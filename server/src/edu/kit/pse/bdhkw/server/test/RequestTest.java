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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		//File docBase = new File(System.getProperty("java.io.tmpdir"));
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
		/*
	        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");
	        logger.setLevel(Level.ALL);
	        Handler[] handlers = logger.getHandlers();
	        Handler handler;
	        if (handlers.length == 1 && handlers[0] instanceof ConsoleHandler) {
	            handler = handlers[0];
	        } else {
	            handler = new ConsoleHandler();
	        }
	        handler.setFormatter(new SimpleFormatter());
	        handler.setLevel(Level.ALL);
	        try {
				handler.setEncoding("UTF-8");
			} catch (SecurityException | UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
	        logger.addHandler(handler);
*/

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
			fail("Server shutdown threw an exception!");
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
	public void getRequestTest() {
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
			e.printStackTrace();
		}
	}
	private Response registerUser(SimpleUser user) {
		// Create the request
		RegistrationRequest request = new RegistrationRequest();
		request.setSenderDeviceId(user.getDeviceId());
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
		session.flush();
		session.close(); 
		return user;
	}
	private GroupServer getGroupFromDB(String groupName) {
		//SessionFactory sessionFactory = (SessionFactory) context.getServletContext().getAttribute("SessionFactory");
		Session session = sessionFactory.openSession();
		ResourceManager man = new ResourceManager(session);
		GroupServer group = man.getGroup(groupName);
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
	public void testCreateGroup() {
		ObjectResponse res = (ObjectResponse) registerUser(sender);
		sender.setID(((SerializableInteger) res.getObject("user_id")).value);

		Response response = createGroup(sender, "new-group-name");
		assertNotNull(response);
		assertTrue(response.getSuccess());
		
		// Check out the group from remote server DB
		//GroupServer dbGroup = getGroupFromDB("new-group-name");
		//assertNotNull(dbGroup);
		//MemberAssociation membership = dbGroup.getMembership(sender.getID());
		//assertNotNull(membership);
		//assertTrue(membership.isAdmin());
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
		
		Response res = deleteGroup(sender, "new-group-name");
		assertNotNull(res);
		assertTrue(res.getSuccess());
	}
	@Test
	public void testGroupNameAvailable() {
		registerUser(sender);
		Response res1 = createGroup(sender, "new-group-name");
		assertTrue(res1.getSuccess());
		
		Response res2 = deleteGroup(sender, "new-group-name");
		assertTrue(res2.getSuccess());
		
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
	}

	@Test
	public void testLeaveGroup() {
		registerUser(sender);
		ObjectResponse res1 = (ObjectResponse) registerUser(user1);
		createGroup(sender, "new-group-name");
		int id =((SerializableInteger) res1.getObject("user_id")).value;
		
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
		MemberAssociation membership = dbUser.getMemberAssociations().iterator().next();
		assertNull(membership.getGroup());
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
		MemberAssociation ass = dbGroup.getMembership(user1.getID());
		assertNull(ass.getGroup());
		assertNull(ass.getUser());
	}

	//@Test
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
	public void testRenameGroup() {
		testCreateGroup();
		RenameGroupRequest req = new RenameGroupRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setNewName("better-group-name");

		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}
	//@Test
	public void testNegativeRenameGroup() {
		testCreateGroup();
		RenameGroupRequest req = new RenameGroupRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setNewName("new-group-name");

		Response re = sendRequest(req);
		assertFalse(re.getSuccess());
	}
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
	}
}
