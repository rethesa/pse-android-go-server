package edu.kit.pse.bdhkw.server.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.communication.*;
import edu.kit.pse.bdhkw.server.controller.Servlet;

public class RequestTest {
	private ObjectMapper objectMapper;
	private HttpClient client;
	private SimpleUser sender;
	private SimpleUser user1;
	private Link link;
	private static Tomcat tomcat;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void startServer() {
		tomcat = new Tomcat();
		tomcat.setPort(8080);

		File docBase = new File(System.getProperty("java.io.tmpdir"));
		Context ctxt = tomcat.addContext("", docBase.getAbsolutePath());

		// Add the servlet
		Tomcat.addServlet(ctxt, "testServlet", new Servlet());
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
		sender = new SimpleUser("sender-dev-id-xy", "sender-user-name", 123456);
		user1 = new SimpleUser("device-id-user", "user1-name", 1337);
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

		assertTrue(response.getSuccess());
		SimpleUser user = (SimpleUser) response.getObject("user_object");

		assertNotNull(user);
		assertEquals(sender.getDeviceId(), user.getDeviceId());
		assertEquals(sender.getName(), user.getName());
	}
	
	@Test
	public void testDeleteUser() {
		testRegistration();
		DeleteUserRequest req = new DeleteUserRequest();
		req.setSenderDeviceId(sender.getDeviceId());
		
		Response res = sendRequest(req);
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
		assertTrue(response.getSuccess());
	}
	
	@Test
	public void testCreateGroup() {
		testRegistration();
		CreateGroupRequest request = new CreateGroupRequest();
		request.setNewGroupName("new-group-name");
		request.setSenderDeviceId(sender.getDeviceId());
		
		Response response = sendRequest(request);
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
		assertTrue(res.getSuccess());
	}
	
	@Test
	public void testCreateLink() {
		testCreateGroup();
		CreateLinkRequest request = new CreateLinkRequest();
		request.setSenderDeviceId(sender.getDeviceId());
		request.setTargetGroupName("new-group-name");
		
		ObjectResponse response = (ObjectResponse) sendRequest(request);
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
		assertTrue(re.getSuccess());
	}
}
