package edu.kit.pse.bdhkw.server.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.MemberAssociation;

/**
 * Test GroupServer public method functionality
 * @author Tarek Wilkening
 *
 */
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
		testUser = new SimpleUser("device-id-user", "test-user1");
		testUser.setID(1234);
		testAdmin = new SimpleUser("device-id-admin", "test-user2");
		testAdmin.setID(5678);
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
		
		MemberAssociation mem = testGroup.getMembership(testAdmin);
		
		assertNotNull(mem);
		
		// Check if the user is administrator of the group.
		assertTrue(testGroup.getMembership(testAdmin).isAdmin());
	}
	@Test
	public void testCreateLink() {
		testGroup.setGroupId("testgroup");
		Link link = testGroup.createInviteLink();
		
		// Link should have the following format
		assertEquals(testGroup.getGroupId(), link.getGroupName());
		assertTrue(testGroup.getSecrets().contains(link.getSecret()));
	}
	@Test
	public void testJoinGroup() {
		// Create an invite link
		Link link = testGroup.createInviteLink();
		
		assertNull(testGroup.getMember(testUser));
		assertNull(testGroup.getMembership(testUser));
		
		// Use the link for testUser1 to join the group
		assertTrue(testGroup.join(testUser, link));
		
		assertNotNull(testGroup.getMember(testUser));
		assertFalse(testGroup.getMembership(testUser).isAdmin());
		
		SimpleUser testUser2 = new SimpleUser("dev-id-3", "testUser2");
		assertNull(testGroup.getMember(testUser2));
		
		// Try using the same link again
		assertFalse(testGroup.join(testUser2, link));
		assertNull(testGroup.getMember(testUser2));
	}
	@Test
	public void testRemoveMember() {
		assertNull(testGroup.getMembership(testUser));
		
		fillGroup();
		assertNotNull(testGroup.getMember(testUser));
		
		testGroup.removeMember(testUser);
		
		assertNull(testGroup.getMember(testUser));
		assertNull(testGroup.getMembership(testUser.getID()));
	}
	@Test
	public void testGetGpsData() {
		fillGroup();
		
		// Get some random GPS coordinates
		GpsObject object = new GpsObject();
		object.setLatitude(8.12351);
		object.setLongitude(48.3456);
		testUser.setGpsObject(object);
		
		// Set the users status to "NOT-GO"
		testGroup.getMembership(testUser).setStatusGo(false);
		
		// Set the users status to "GO"
		testGroup.getMembership(testUser).setStatusGo(true);
	}
	@Test
	public void testGetMemberAssociations() {
		assertTrue(testGroup.getMemberAssociations().isEmpty());
		
		fillGroup();
		
		assertFalse(testGroup.getMemberAssociations().isEmpty());
		assertTrue(testGroup.getMemberAssociations().contains(testGroup.getMembership(testAdmin)));
		assertTrue(testGroup.getMemberAssociations().contains(testGroup.getMembership(testUser)));
	}
}
