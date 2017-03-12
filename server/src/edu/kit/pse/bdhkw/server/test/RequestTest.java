package edu.kit.pse.bdhkw.server.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SerializableLinkedList;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.communication.*;
import edu.kit.pse.bdhkw.server.controller.MainServlet;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.MemberAssociation;

public class RequestTest {
	private ObjectMapper objectMapper;
	private static HttpClient client;
	private SimpleUser sender;
	private SimpleUser user1;
	private static Tomcat tomcat;
	private static DB db;
	private static final String dbname = "PSEWS1617GoGruppe3";
	private static final String username = "PSEWS1617User3";
	private static final String password = "GJvGaY81thHd4nFc";
	private static Context context;
	private static SessionFactory sessionFactory;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void startServer() throws SQLException {
		// Set up embedded MySQL Database
		File docBase = new File("/home/hagbard/server/apache-tomcat-8.0.20");

		try {
			db = DB.newEmbeddedDB(3306);
			db.start();
			db.createDB(dbname);
			db.source("database.sql", username, password, dbname);

		} catch (ManagedProcessException e1) {
			System.err.println("DB setup failed!");
			e1.printStackTrace();
		}
		
		tomcat = new Tomcat();
		tomcat.setPort(8080);

		context = tomcat.addContext("", docBase.getAbsolutePath());
		context.setSwallowOutput(true);
		context.addApplicationListener("edu.kit.pse.bdhkw.server.controller.HibernateSessionFactoryListener");
		client = HttpClients.createDefault();
		// Add the Servlet
		Tomcat.addServlet(context, "testServlet", new MainServlet());
		context.addServletMapping("/*", "testServlet");

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			fail("Server threw an exception!");
			e.printStackTrace();
		}
		sessionFactory = (SessionFactory) context.getServletContext().getAttribute("SessionFactory");
		sessionFactory.openSession();
	}

	@AfterClass
	public static void shutdown() {
		tomcat.getConnector().pause();
		//SessionFactory sessionFactory = (SessionFactory) context.getServletContext().getAttribute("SessionFactory");
		sessionFactory.close();
		try {
			Thread.sleep(3000);
			db.stop();
			tomcat.stop();
			tomcat.destroy();
			context.destroy();
			
		} catch (LifecycleException | ManagedProcessException | InterruptedException e) {
			System.err.println("ERROR: Shutdown failed! " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		tomcat = null;
		client = null;
	}

	@Before 
	public void setUp() throws Exception {
		// Clear database
		db.source("database.sql", username, password, dbname);

		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		sender = new SimpleUser("sender-dev-id-xy", "sender-user-name");
		user1 = new SimpleUser("device-id-user", "user1-name");
	}

	@After
	public void tearDown() throws Exception {
		objectMapper = null;
		sender = null;
		user1 = null;
	} 
	@Test
	public void testSetup() {
		try {
			setUp();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(objectMapper);
		assertNotNull(sender);
		assertNotNull(user1);
	}
	@Test 
	public void testTearDown() {
		try {
			tearDown();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNull(user1);
		assertNull(sender);
		assertNull(objectMapper);
	}

	private Response sendRequest(Request request) {
		try {
			ByteArrayEntity e = new ByteArrayEntity(objectMapper.writeValueAsBytes(request));
			HttpPost post = new HttpPost("http://localhost:8080/server/testServlet/");
			post.setEntity(e);
			HttpResponse r = client.execute(post);
			HttpEntity ent = r.getEntity();
			InputStream in = ent.getContent();

			byte[] inputJSONData = new byte[8192];
			int c, count = 0;
			while ((c = in.read(inputJSONData, count, inputJSONData.length - count)) != -1) {
				count += c;
			}
			in.close();
			System.out.println(new String(inputJSONData));
			return objectMapper.readValue(inputJSONData, Response.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@Test
	public void testGETRequest() {
		HttpGet get = new HttpGet("http://localhost:8080/server/testServlet/");
		try {
			HttpResponse response = client.execute(get);
			assertEquals(200, response.getStatusLine().getStatusCode());
			InputStream in = response.getEntity().getContent();

			byte[] inputJSONData = new byte[512];
			int c, count = 0;
			while ((c = in.read(inputJSONData, count, inputJSONData.length - count)) != -1) {
				count += c;
			}
			in.close();
			assertTrue(new String(inputJSONData).contains("Please download and install GoApp"));
		} catch (IOException e) {
			fail("GET request could not be executed!");
		}
	}
	private Response registerUser(SimpleUser user) {
		// Create the request
		RegistrationRequest request = new RegistrationRequest(user.getDeviceId());
		request.setUserName(user.getName());

		// Get the response
		return sendRequest(request);
	}
	private Response createGroup(SimpleUser owner, String name) {
		CreateGroupRequest request = new CreateGroupRequest();
		request.setNewGroupName(name);
		request.setSenderDeviceId(owner.getDeviceId());
		return sendRequest(request);
	}
	private Response createLink(SimpleUser owner, String groupName) {
		CreateLinkRequest request = new CreateLinkRequest();
		request.setSenderDeviceId(owner.getDeviceId());
		request.setTargetGroupName(groupName);

		return sendRequest(request);
	}
	private SimpleUser getUserFromDB(String deviceId) {
		//SessionFactory sessionFactory = (SessionFactory) context.getServletContext().getAttribute("SessionFactory");
		Session session = sessionFactory.openSession();
		ResourceManager man = new ResourceManager(session);
		SimpleUser user = man.getUser(deviceId);
		if (user == null) return null;
		Transaction t = session.beginTransaction();
		Hibernate.initialize(user.getMemberAssociations());
		t.commit();
		session.flush();
		session.close(); 
		return user;
	}
	private GroupServer getGroupFromDB(String groupName) {
		//SessionFactory sessionFactory = (SessionFactory) context.getServletContext().getAttribute("SessionFactory");
		Session session = sessionFactory.openSession();
		ResourceManager man = new ResourceManager(session);
		GroupServer group = man.getGroup(groupName);
		if (group == null) return null;
		Transaction t = session.beginTransaction();
		Hibernate.initialize(group.getMemberAssociations());
		Hibernate.initialize(group.getSecrets());
		t.commit();
		session.flush();
		session.close(); 
		return group;
	}
	@Test
	public void testRegistration() {
		ObjectResponse response = (ObjectResponse) registerUser(sender);

		assertNotNull(response);
		assertTrue(response.getSuccess());
		SerializableInteger userid = (SerializableInteger) response.getObject("user_id");

		// Check if user is in the database
		SimpleUser user = getUserFromDB(sender.getDeviceId());
		assertNotNull(user);
		assertEquals(user.getName(), sender.getName());
		assertEquals(user.getDeviceId(), sender.getDeviceId());
		assertEquals(user.getID(), userid.value);
	}
	@Test
	public void testRecoverAccount() {
		ObjectResponse response = (ObjectResponse) registerUser(sender);
		assertNotNull(response);
		assertTrue(response.getSuccess());
		SerializableInteger userid = (SerializableInteger) response.getObject("user_id");
		
		ObjectResponse response2 = (ObjectResponse) registerUser(sender);
		assertNotNull(response2);
		assertFalse(response2.getSuccess());
		SerializableInteger userid2 = (SerializableInteger) response.getObject("user_id");
		assertEquals(userid.value, userid2.value);
		assertNotNull(getUserFromDB(sender.getDeviceId()));
	}
	@Test
	public void testDeleteUser() {
		ObjectResponse response = (ObjectResponse) registerUser(sender);
		SerializableInteger userid = (SerializableInteger) response.getObject("user_id");
		
		DeleteUserRequest req = new DeleteUserRequest();
		req.setSenderDeviceId(sender.getDeviceId());

		Response res = sendRequest(req);
		assertNotNull(res);
		assertTrue(res.getSuccess());
		
		// Check if the user was removed from the database
		assertNull(getUserFromDB(sender.getDeviceId()));
		
		// Create a new user and compare user IDs
		ObjectResponse response2 = (ObjectResponse) registerUser(sender);
		SerializableInteger userid2 = (SerializableInteger) response2.getObject("user_id");
		assertNotEquals(userid.value, userid2.value);
	}
	@Test
	public void testRenameUser() {
		// Create the request
		registerUser(sender);

		RenameUserRequest request = new RenameUserRequest();
		request.setNewName("renamed-to-name");
		request.setSenderDeviceId(sender.getDeviceId());

		Response response = sendRequest(request);
		assertNotNull(response);
		assertTrue(response.getSuccess());
		
		// Check if the name has changed within the database
		SimpleUser dbUser = getUserFromDB(sender.getDeviceId());
		assertEquals(dbUser.getName(), "renamed-to-name");
	}
	@Test
	public void testRenameUserFail() {
		RenameUserRequest request = new RenameUserRequest();
		request.setNewName("renamed-to-name");
		request.setSenderDeviceId(sender.getDeviceId());

		Response response = sendRequest(request);
		assertFalse(response.getSuccess());
	}
	@Test 
	public void testCreateGroup() {
		ObjectResponse res = (ObjectResponse) registerUser(sender);
		sender.setID(((SerializableInteger) res.getObject("user_id")).value);

		Response response = createGroup(sender, "new-group-name");
		assertNotNull(response);
		assertTrue(response.getSuccess());
		
		// Check out the group from remote server DB
		GroupServer dbGroup = getGroupFromDB("new-group-name");
		assertNotNull(dbGroup);
		assertTrue(dbGroup.getMemberAssociations().size() == 1);
		MemberAssociation membership = dbGroup.getMemberAssociations().iterator().next();
		assertNotNull(membership);
		assertTrue(membership.isAdmin());
	}
	@Test
	public void testCreateGroupFail() {
		registerUser(sender);
		Response response1 = createGroup(sender, "new-group-name");
		assertNotNull(response1);
		assertTrue(response1.getSuccess());
		
		Response response2 = createGroup(sender, "new-group-name");
		assertNotNull(response2);
		assertFalse(response2.getSuccess());
	}
	private Response deleteGroup(SimpleUser owner, String groupName) {
		DeleteGroupRequest req = new DeleteGroupRequest();
		req.setSenderDeviceId(owner.getDeviceId());
		req.setTargetGroupName(groupName);
		return sendRequest(req);
	}
	@Test
	public void testDeleteGroup() {
		registerUser(sender);
		createGroup(sender, "new-group-name");
		assertNotNull(getGroupFromDB("new-group-name"));
		
		Response res = deleteGroup(sender, "new-group-name");
		assertNotNull(res);
		assertTrue(res.getSuccess());
		assertNull(getGroupFromDB("new-group-name"));
	}
	@Test 
	public void testDeleteGroupFail() {
		registerUser(sender);
		Response res = deleteGroup(sender, "new-group-name");
		assertNotNull(res);
		assertFalse(res.getSuccess());
	}
	@Test
	public void testGroupNameAvailable() {
		registerUser(sender);
		Response res1 = createGroup(sender, "new-group-name");
		assertTrue(res1.getSuccess());
		
		Response res2 = deleteGroup(sender, "new-group-name");
		assertTrue(res2.getSuccess());
		
		// Group name should be available
		Response res3 = createGroup(sender, "new-group-name");
		assertTrue(res3.getSuccess());
	}
	@Test
	public void testCreateLink() {
		registerUser(sender);
		createGroup(sender, "new-group-name");

		ObjectResponse response = (ObjectResponse) createLink(sender, "new-group-name");
		assertNotNull(response);
		assertTrue(response.getSuccess());
		Link link = (Link) response.getObject("invite_link");
		assertNotNull(link);
		assertEquals(link.getGroupName(), "new-group-name");
		assertFalse(getGroupFromDB("new-group-name").getSecrets().isEmpty());
	}
	private Response joinGroup(SimpleUser user, Link link, String groupName) {
		JoinGroupRequest req = new JoinGroupRequest();
		req.setLink(link);
		req.setTargetGroupName(groupName);
		req.setSenderDeviceId(user.getDeviceId());
		
		return sendRequest(req);
	}
	@Test
	public void testJoinGroup() {
		registerUser(sender);
		//ObjectResponse res1 = (ObjectResponse) registerUser(user1);
		registerUser(user1);
		createGroup(sender, "new-group-name");

		ObjectResponse res2 = (ObjectResponse) createLink(sender, "new-group-name");
		Link link = (Link) res2.getObject("invite_link");

		// Obtain user ID for 
		//user1.setID(((SerializableInteger) res1.getObject("user_id")).value);
		
		ObjectResponse re = (ObjectResponse) joinGroup(user1, link, "new-group-name");
		assertNotNull(re);
		assertTrue(re.getSuccess());
		GroupServer dbGroup = getGroupFromDB("new-group-name");
		assertTrue(dbGroup.getSecrets().isEmpty());
		assertTrue(dbGroup.getMemberAssociations().size() == 2);
	}

	@Test
	public void testLeaveGroup() {
		registerUser(sender);
		ObjectResponse res1 = (ObjectResponse) registerUser(user1);
		createGroup(sender, "new-group-name");
		//int id =((SerializableInteger) res1.getObject("user_id")).value;
		
		ObjectResponse res2 = (ObjectResponse) createLink(sender, "new-group-name");
		Link link = (Link) res2.getObject("invite_link");
		
		joinGroup(user1, link, "new-group-name");
		
		LeaveGroupRequest req = new LeaveGroupRequest();
		req.setSenderDeviceId(user1.getDeviceId());
		req.setTargetGroupName("new-group-name");
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());

		SimpleUser dbUser = getUserFromDB(user1.getDeviceId());
		// User1 joined one single group, so memberAssociations should be empty by now.
		assertTrue(dbUser.getMemberAssociations().isEmpty());
	}

	@Test
	public void testKickMember() {
		registerUser(sender);
		ObjectResponse res1 = (ObjectResponse) registerUser(user1);
		user1.setID(((SerializableInteger) res1.getObject("user_id")).value);
		createGroup(sender, "new-group-name");
		Link link = (Link) ((ObjectResponse) createLink(sender, "new-group-name")).getObject("invite_link");
		joinGroup(user1, link, "new-group-name");
		
		KickMemberRequest req = new KickMemberRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setTargetMemberId(user1.getID());
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
		
		GroupServer dbGroup = getGroupFromDB("new-group-name"); 
		assertNotNull(dbGroup);
		// Only the admin remains
		assertTrue(dbGroup.getMemberAssociations().size() == 1);
	}

	@Test
	public void testMakeAdmin() {
		registerUser(sender);
		ObjectResponse res1 = (ObjectResponse) registerUser(user1);
		user1.setID(((SerializableInteger) res1.getObject("user_id")).value);
		createGroup(sender, "new-group-name");
		
		Link link = (Link) ((ObjectResponse) createLink(sender, "new-group-name")).getObject("invite_link");
		joinGroup(user1, link, "new-group-name");
		
		MakeAdminRequest req = new MakeAdminRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setTargetUserId(user1.getID());

		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}
	// @Test
	// Group name equals it's primary key,
	// so we skip this.
	/*
	public void testRenameGroup() {
		testCreateGroup();
		RenameGroupRequest req = new RenameGroupRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setNewName("better-group-name");

		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}*/
	public Response setAppointment(SimpleUser owner, String groupName, Appointment appointment) {
		SetAppointmentRequest req = new SetAppointmentRequest();
		req.setSenderDeviceId(owner.getDeviceId());
		req.setTargetGroupName(groupName);
		req.setAppointment(appointment);
		return sendRequest(req);
	}
	@Test
	public void testSetAppointment() {
		registerUser(sender);
		createGroup(sender, "new-group-name");
		
		Appointment a = new Appointment();
		a.setDate(1423545);
		GpsObject gps = new GpsObject();
		gps.setLatitude(2.33343);
		gps.setLongitude(1.111111);
		gps.setTimestamp(123456789);
		a.setDestination(gps);
		a.setName("mensa");
		Response re = setAppointment(sender, "new-group-name", a);
		assertNotNull(re);
		assertTrue(re.getSuccess());
		GroupServer dbGroup = getGroupFromDB("new-group-name");
		GpsObject destination = dbGroup.getAppointment().getDestination();
		assertTrue(gps.getLatitude() - destination.getLatitude() < 0.000001);
		assertTrue(gps.getLongitude() - destination.getLongitude() < 0.000001);
		assertEquals(destination.getTimestamp(), gps.getTimestamp());
		assertEquals(dbGroup.getAppointment().getName(), "mensa");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		ObjectResponse res1 = (ObjectResponse) registerUser(sender);
		sender.setID(((SerializableInteger) res1.getObject("user_id")).value);
		createGroup(sender, "new-group-name");
		
		UpdateRequest req = new UpdateRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");

		ObjectResponse res = (ObjectResponse) sendRequest(req);
		assertTrue(res.getSuccess());
		@SuppressWarnings("rawtypes")
		SerializableLinkedList<HashMap> members = (SerializableLinkedList<HashMap>) res.getObject("member_list");
		ObjectMapper o = new ObjectMapper();
		SerializableMember member1 = null;
		try {
			String json = o.writeValueAsString(members.pop());
			member1 = o.readValue(json.getBytes(), SerializableMember.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(member1.getName(), sender.getName());	
		assertEquals(member1.getId(), sender.getID());
		assertTrue(member1.isAdmin());
		// Get group from the database
		GroupServer dbGroup = getGroupFromDB("new-group-name");
		Appointment app = (Appointment) res.getObject("appointment");
		assertNotNull(app);
		assertNull(app.getName());
		assertTrue(((SerializableLinkedList<GpsObject>) res.getObject("gps_data")).isEmpty());
		assertEquals(app.getDestination().getTimestamp(), 0);
	}
	@Test
	public void testBroadcastGps() {
		registerUser(sender);
		createGroup(sender, "new-group-name");
		GpsObject gps = new GpsObject();
		gps.setLatitude(1.111111);
		gps.setLongitude(2.222222);
		gps.setTimestamp(1234567);
		
		BroadcastGpsRequest req = new BroadcastGpsRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setCoordinates(gps);
		req.setStatusGo(true); 
		
		ObjectResponse res = (ObjectResponse) sendRequest(req);
		assertNotNull(res);
		assertTrue(res.getSuccess());
		SerializableLinkedList<HashMap<String,String>> list = (SerializableLinkedList<HashMap<String,String>>) res.getObject("gps_list");
		assertFalse(list.isEmpty());
		ObjectMapper o = new ObjectMapper();
		GpsObject gps2 = null;
		try {
			String json = o.writeValueAsString(list.pop());
			gps2 = o.readValue(json.getBytes(), GpsObject.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(gps2); 
		assertTrue(gps.getLatitude() - gps2.getLatitude() < 0.000001); 
		assertTrue(gps.getLongitude() - gps2.getLongitude() < 0.000001);
		assertEquals(gps.getTimestamp(), gps2.getTimestamp());
	}
	@Test
	public void testPersistUser() {
		Session session = sessionFactory.openSession();
		ResourceManager man = new ResourceManager(session);
		GpsObject gps = new GpsObject();
		sender.setID(12345);
		sender.setGpsObject(gps);
		man.persistObject(sender);
		
		SimpleUser dbUser = man.getUser(sender.getDeviceId());
		assertEquals(dbUser.getID(), sender.getID());
		assertEquals(dbUser.getName(), sender.getName());
		assertEquals(dbUser.getGpsObject(), gps);
	}
	@Test
	public void testPersistGroup() {
		Session session = sessionFactory.openSession();
		ResourceManager man = new ResourceManager(session);
		GroupServer group = new GroupServer();
		group.setGroupId("group");
		man.persistObject(group);
		
		GroupServer dbGroup = man.getGroup("group");
		assertEquals(group, dbGroup);
	}
}
