package edu.kit.pse.bdhkw.server.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;

/**
 * Manages object flow from the RequestHandler to the database.
 * TODO: maybe this should inherit from some Hibernate-interface?
 * @author Tarek Wilkening
 *
 */
public class ResourceManager {
	private Session session;
	
	public ResourceManager(Session session) {
		this.session = session;
	}

	/**
	 * Get a SimpleUser object from the database.
	 * Database-key is the users device ID which is unique.
	 * @param deviceId - device ID of the target user.
	 * @return SimpleUser with matching ID or null if none was found.
	 */
	public SimpleUser getUser(String deviceId) {
		Transaction tx = session.beginTransaction();
		SimpleUser user = (SimpleUser) session.get(SimpleUser.class, deviceId);
		tx.commit();
		return user;
	}
	/**
	 * Same thing but with user ID
	 * TODO does not work! remove
	 * @param userId
	 * @return
	 */
	public SimpleUser getUser(int userId) {
		// !FOR TESTING PURPOSE ONLY!
		SimpleUser user = new SimpleUser("tareks-ultradevice", "tarek", userId);
		GpsObject o = new GpsObject();
		o.setLatitude(49.21315f);
		o.setLongitude(8.342334f);
		user.setGpsObject(o);
		return user;
	}
	/**
	 * Get a GroupServer object from the database.
	 * Database-key is the groups name as it is unique.
	 * @param groupName - name of the target group
	 * @return GroupServer with matching ID or null if none was found.
	 */
	public GroupServer getGroup(String groupName) {
		GroupServer group = new GroupServer();
		group.addAdmin(getUser("test-devID"));
		group.getAppointment().setName("Mensaaa!!");
		return group;
	}
	/**
	 * Stores a SimpleUser object inside the database.
	 * The key that this user is stored by, is the device ID.
	 * @param SimpleUser to store in the database.
	 */
	public void returnUser(SimpleUser user) {
		Transaction tx = session.beginTransaction();
		session.persist(user);
		tx.commit();
	}
	/**
	 * Stores a GroupServer object inside the database.
	 * The key that this group is stored by, is groupName.
	 * @param GroupServer to store in the database.
	 */
	public void returnGroup(GroupServer group) {
		
	}
	/**
	 * Deletes a user from the database.
	 * @param user to delete from the database.
	 */
	public void deleteUser(SimpleUser user) {
		// TODO
	}
	/**
	 * Deletes a group from the database.
	 * @param group to delete from the database.
	 */
	public void deleteGroup(GroupServer group) {
		// TODO
	}
}
