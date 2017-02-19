package edu.kit.pse.bdhkw.server.test;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;

/**
 * Manages object flow from the RequestHandler to the database.
 * Note that methods are only defined for known types.
 * @author Tarek Wilkening
 *
 */
public class ResourceManagerTest {
	private Session session;
	
	public ResourceManagerTest(Session session) {
		this.session = session;
	}

	/**
	 * Get a SimpleUser object from the database.
	 * Database-key is the users device ID which is unique.
	 * @param deviceId - device ID of the target user.
	 * @return SimpleUser with matching ID or null if none was found.
	 */
	public SimpleUser getUser(String deviceId) {
		SimpleUser user = new SimpleUser();
		user.setDeviceId(deviceId);
		user.setID(1234);
		user.setName("user1");
		return user;
	}

	/**
	 * Get a GroupServer object from the database.
	 * Database-key is the groups name as it is unique.
	 * @param groupName - name of the target group
	 * @return GroupServer with matching ID or null if none was found.
	 */
	public GroupServer getGroup(String groupName) {
		Transaction t = session.beginTransaction();
		GroupServer group = (GroupServer) session.get(GroupServer.class, groupName);
		t.commit();
		return group;
	}
	/**
	 * Stores a SimpleUser object inside the database.
	 * The key that this user is stored by, is the device ID.
	 * @param SimpleUser to store in the database.
	 */
	public void psersistObject(SimpleUser user) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
	}
	/**
	 * Stores a GroupServer object inside the database.
	 * The key that this group is stored by, is groupName.
	 * @param GroupServer to store in the database.
	 */
	public void persistObject(GroupServer group) {
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(group);
		t.commit();
	}
	/**
	 * Deletes a user from the database.
	 * @param user to delete from the database.
	 */
	public void deleteObject(SimpleUser user) {
		Transaction t = session.beginTransaction();
		session.delete(user);
		t.commit();
	}
	/**
	 * Deletes a GroupServer instance from the database.
	 * @param group to be deleted.
	 */
	public void deleteObject(GroupServer group) {
		Transaction t = session.beginTransaction();
		session.delete(group);
		t.commit();
	}
}