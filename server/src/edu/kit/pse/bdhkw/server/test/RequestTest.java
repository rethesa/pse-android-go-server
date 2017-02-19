package edu.kit.pse.bdhkw.server.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mysql.management.MysqldResource;

import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.communication.*;
import edu.kit.pse.bdhkw.server.controller.MainServlet;

public class RequestTest {
	private ObjectMapper objectMapper;
	private HttpClient client;
	private SimpleUser sender;
	private SimpleUser user1;
	private Link link;
	private static Tomcat tomcat;
	private static EmbeddedMysqlDataSource dataSource;
	private static MysqldResource MysqldResourceI;
	private static Connection con;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void startServer() throws SQLException {
		File docBase = new File(System.getProperty("java.io.tmpdir"));
		// Set up embedded MySQL Database
		MysqldResourceI = new MysqldResource(docBase);

        Map<String,String> database_options = new HashMap<String,String>();
		database_options.put(com.mysql.management.MysqldResourceI.PORT, Integer.toString(3306));
        database_options.put(com.mysql.management.MysqldResourceI.INITIALIZE_USER, "true");
        database_options.put(com.mysql.management.MysqldResourceI.INITIALIZE_USER_NAME, "PSEWS1617User3");
        database_options.put(com.mysql.management.MysqldResourceI.INITIALIZE_PASSWORD, "GJvGaY81thHd4nFc");
       MysqldResourceI.start("thread", database_options);
        if (!MysqldResourceI.isRunning()) {
            throw new RuntimeException("MySQL did not start.");
        }
        System.out.println("MySQL is running.");
		try {
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PSEWS1617GoGruppe3" + "?" + "createDatabaseIfNotExist=true", "PSEWS1617User3", "GJvGaY81thHd4nFc");
            
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	/*	dataSource = EmbeddedMysqlDataSource.getInstance();
		dataSource.setPassword("GJvGaY81thHd4nFc");
		dataSource.setUser("PSEWS1617User3");
		dataSource.setDatabaseName("PSEWS1617GoGruppe3");
		dataSource.setURL("jdbc:mysql://localhost/PSEWS1617GoGruppe3");
		dataSource.setPort(3306);*/

		tomcat = new Tomcat();
		tomcat.setPort(8080);


		Context ctxt = tomcat.addContext("", docBase.getAbsolutePath());
		ctxt.addApplicationListener("edu.kit.pse.bdhkw.server.controller.HibernateSessionFactoryListener");
		
		System.out.println("hallo welt");
		// Add the Servlet
		Tomcat.addServlet(ctxt, "testServlet", new MainServlet());
		ctxt.addServletMapping("/*", "testServlet");
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			fail("Server threw an exception!");
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void shutdown() {
		// Shut down local DB
		EmbeddedMysqlDataSource.shutdown(dataSource);
		MysqldResourceI.shutdown();
		try {
			tomcat.stop();
			tomcat.destroy();
		} catch (LifecycleException e) {
			fail("Server shutdown threw an exception!");
			e.printStackTrace();
		}
		tomcat = null;
	}

	@Before
	public void setUp() throws Exception {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		client = HttpClients.createDefault();
		sender = new SimpleUser("sender-dev-id-xy", "sender-user-name");
		user1 = new SimpleUser("device-id-user", "user1-name");
	}

	@After
	public void tearDown() throws Exception {
		objectMapper = null;
		client = null;
	}

	private Response sendRequest(Request request) {
		try {
			ByteArrayEntity e = new ByteArrayEntity(objectMapper.writeValueAsBytes(request));
			HttpPost post = new HttpPost("http://localhost:8080/server/testServlet/");
			post.setEntity(e);
			HttpResponse r = client.execute(post);
			HttpEntity ent = r.getEntity();
			InputStream in = ent.getContent();

			byte[] inputJSONData = new byte[512];
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

	@Test
	public void testRegistration() {
		// Create the request
		RegistrationRequest request = new RegistrationRequest();
		request.setSenderDeviceId(sender.getDeviceId());
		request.setUserName(sender.getName());

		// Get the response
		ObjectResponse response = (ObjectResponse) sendRequest(request);

		assertNotNull(response);
		assertTrue(response.getSuccess());
		SerializableInteger userid = (SerializableInteger) response.getObject("user_id");
		
		assertNotEquals(userid, 0);
	}
	
	@Test
	public void testDeleteUser() {
		testRegistration();
		DeleteUserRequest req = new DeleteUserRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		
		Response res = sendRequest(req);
		assertNotNull(res);
		assertTrue(res.getSuccess());
		//TODO test if user is not on server
	}

	@Test
	public void testRenameUser() {
		// Create the request
		RegistrationRequest registration = new RegistrationRequest();
		registration.setSenderDeviceId(sender.getDeviceId());
		registration.setUserName(sender.getName());

		// Get the response
		assertTrue(sendRequest(registration).getSuccess());

		RenameUserRequest request = new RenameUserRequest();
		request.setNewName("renamed-to-name");
		request.setSenderDeviceId(sender.getDeviceId());

		Response response = sendRequest(request);
		assertNotNull(response);
		assertTrue(response.getSuccess());
	}
	
	@Test
	public void testCreateGroup() {
		testRegistration();
		CreateGroupRequest request = new CreateGroupRequest();
		request.setNewGroupName("new-group-name");
		request.setSenderDeviceId(sender.getDeviceId());
		
		Response response = sendRequest(request);
		assertNotNull(response);
		assertTrue(response.getSuccess());
		// TODO check group stuff
	}
	
	@Test 
	public void testDeleteGroup() {
		testCreateGroup();
		DeleteGroupRequest req = new DeleteGroupRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		
		Response res = sendRequest(req);
		assertNotNull(res);
		assertTrue(res.getSuccess());
	}
	
	@Test
	public void testCreateLink() {
		testCreateGroup();
		CreateLinkRequest request = new CreateLinkRequest();
		request.setSenderDeviceId(sender.getDeviceId());
		request.setTargetGroupName("new-group-name");
		
		ObjectResponse response = (ObjectResponse) sendRequest(request);
		assertNotNull(response);
		assertTrue(response.getSuccess());
		link = (Link) response.getObject("link_object");
		assertEquals(link.getGroupName(), "new-group-name");
	}
	
	@Test
	public void testJoinGroup() {
		testCreateLink();
		JoinGroupRequest req = new JoinGroupRequest();
		req.setLink(link);
		req.setTargetGroupName("new-group-name");
		req.setSenderDeviceId(user1.getDeviceId());
		
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}
	
	@Test
	public void testKickMember() {
		testJoinGroup();
		KickMemberRequest req = new KickMemberRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setTargetMemberId(user1.getID());
		
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}
	
	@Test
	public void testLeaveGroup() {
		testJoinGroup();
		LeaveGroupRequest req = new LeaveGroupRequest();
		req.setSenderDeviceId(user1.getDeviceId());
		req.setTargetGroupName("new-group-name");
		
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
		
		// Test if the user can still access the group
		testUpdate();
	}
	
	@Test
	public void testMakeAdmin() {
		testJoinGroup();
		MakeAdminRequest req = new MakeAdminRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		req.setTargetUserId(user1.getID());
		
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}
	
	@Test
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
	
	@Test
	public void testSetAppointment() {
		testCreateGroup();
		SetAppointmentRequest req = new SetAppointmentRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		req.setTargetGroupName("new-group-name");
		
		Appointment a = new Appointment();
		a.setDate(new Date(1423545));
		a.setDestination(new GpsObject());
		a.setName("mensa");
		req.setAppointment(a);
		Response re = sendRequest(req);
		assertNotNull(re);
		assertTrue(re.getSuccess());
	}
	
	@Test
	public void testUpdate() {
		testJoinGroup();
		// TBD...
	}
}
