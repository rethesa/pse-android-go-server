package com.goapp.server.model;

import com.goapp.common.model.SimpleUser;

/**
 * Manages object flow from the RequestHandler to the database.
 * TODO: maybe this should inherit from some Hibernate-interface?
 * @author Tarek Wilkening
 *
 */
public class ResourceManager {
	/**
	 * Get a SimpleUser object from the database.
	 * Database-key is the users device ID which is unique.
	 * @param deviceId - device ID of the target user.
	 * @return SimpleUser with matching ID or null if none was found.
	 */
	public static SimpleUser getUser(String deviceId) {
		return null;
	}
	/**
	 * Get a GroupServer object from the database.
	 * Database-key is the groups name as it is unique.
	 * @param groupName - name of the target group
	 * @return GroupServer with matching ID or null if none was found.
	 */
	public static GroupServer getGroup(String groupName) {
		return null;
	}
	/**
	 * Stores a SimpleUser object inside the database.
	 * The key that this user is stored by, is the device ID.
	 * @param SimpleUser to store in the database.
	 */
	public static void returnUser(SimpleUser user) {
		
	}
	/**
	 * Stores a GroupServer object inside the database.
	 * The key that this group is stored by, is groupName.
	 * @param GroupServer to store in the database.
	 */
	public static void returnGroup(GroupServer group) {
		
	}
}
