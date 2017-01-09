package com.goapp.server.model;
import java.util.LinkedList;

import com.goapp.client.GroupClient;
import com.goapp.common.model.SimpleUser;
/**
 * Server-side class for managing groups and interacting with the database.
 * @author tarek
 *
 */
public class GroupManager {
	public GroupManager() {
		// TODO Auto-generated method stub
	}
	/**
	 * Create a new group and store it in the database.
	 * The name has to be unique! Will return null on error.
	 * @param admin - user to be administrator of this group.
	 * @param name - the name this group should be called.
	 * @return the newly generated group or null if name in use.
	 */
	public GroupClient createGroup(SimpleUser admin, String name) {
		// Check if name is already in use TODO
		if(getGroupNames().contains(name)) {
			return null;
		}
		GroupClient group = new GroupClient(name);
		
		return group;
	}
	/**
	 * Collects all group-names from the database and returns them.
	 * Used to check if a name is already in use.
	 * @return names of all groups
	 */
	private LinkedList<String> getGroupNames() {
		// TODO
		return null;
	}
	/**
	 * Get the group objects from the database and return.
	 * @return List of groups from the database.
	 */
	public LinkedList<GroupClient> getGroups() {
		return null;
	}
	public GroupServer getGroup(String groupName) {
		// TODO : get the group from the database
		return null;
	}
}
