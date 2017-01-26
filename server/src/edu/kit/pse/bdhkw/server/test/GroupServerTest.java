package edu.kit.pse.bdhkw.server.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;

public class GroupServerTest {
	
	private GroupServer testGroup;
	private SimpleUser testUser;
	private SimpleUser testAdmin;

	private void fillGroup() {
		testGroup.addAdmin(testAdmin);
		testGroup.join(testUser, testGroup.createInviteLink());
	}
	@Before
	public void setUp() {
		testGroup = new GroupServer();
		testUser = new SimpleUser("device-id-user", "test-user1", 1234);
		testAdmin = new SimpleUser("device-id-admin", "test-user2", 5678);
	}
	@After
	public void tearDown() {
		testGroup = null;
		testUser = null;
		testAdmin = null;
	}
	@Test
	public void testAddAdmin() {
		testGroup.addAdmin(testAdmin);
		
		// Check if the user is administrator of the group.
		assertTrue(testGroup.getMember(testAdmin).isAdmin());
	}
	@Test
	public void testCreateLink() {
		testGroup.setName("testgroup");
		Link link = testGroup.createInviteLink();
		
		// Link should have the following format
		assertEquals(link.toString(), "http://localhost:8080/server/Group/testgroup/" + link.getSecret());
	}
	@Test
	public void testJoinGroup() {
		// Create an invite link
		Link link = testGroup.createInviteLink();
		
		assertNull(testGroup.getMember(testUser));
		
		// Use the link for testUser1 to join the group
		assertTrue(testGroup.join(testUser, link));
		
		assertNotNull(testGroup.getMember(testUser));
		assertFalse(testGroup.getMember(testUser).isAdmin());
		
		SimpleUser testUser2 = new SimpleUser("dev-id-3", "testUser2", 77541);
		assertNull(testGroup.getMember(testUser2));
		
		// Try using the same link again
		assertFalse(testGroup.join(testUser2, link));
		assertNull(testGroup.getMember(testUser2));
	}
	@Test
	public void testRemoveMember() {
		fillGroup();
		assertNotNull(testGroup.getMember(testUser));
		
		testGroup.removeMember(testUser);
		
		assertNull(testGroup.getMember(testUser));
	}
	@Test
	public void testGetGpsData() {
		fillGroup();
		
		// Get some random GPS coordinates
		GpsObject object = new GpsObject();
		object.setLatitude(8.12351);
		object.setLongitude(48.3456);
		testUser.setGpsObject(object);
		
		// TODO this is testing the database
		assertTrue(testGroup.getGPSData().isEmpty());
		
		// Set the users status to "GO"
		testGroup.getMember(testUser).setStatusGo(true);
		
		assertEquals(testGroup.getGPSData().getFirst(), object);
	}
	@Test
	public void testGetMemberIdSet() {
		assertTrue(testGroup.getMemberIdSet().isEmpty());
		
		fillGroup();
		
		assertFalse(testGroup.getMemberIdSet().isEmpty());
		assertTrue(testGroup.getMemberIdSet().contains(testAdmin.getDeviceId()));
		assertTrue(testGroup.getMemberIdSet().contains(testUser.getDeviceId()));
	}
}
