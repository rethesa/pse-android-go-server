package com.goapp.server.trash;
import java.util.LinkedList;

import com.goapp.common.model.SimpleUser;
import com.goapp.server.model.GroupServer;
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
	public GroupServer createGroup(SimpleUser admin, String name) {
		// Check if name is already in use TODO
		if(getGroupNames().contains(name)) {
			return null;
		}
		GroupServer group = new GroupServer(name);
		
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
	 * Stores a group in the database.
	 * @param group to be stored.
	 */
	public void returnGroup(GroupServer group) {
		//TODO
	}
	/**
	 * Get the group objects from the database and return.
	 * @return List of groups from the database.
	 */
	/*public LinkedList<GroupServer> getGroups() {
		return null;
	}*/
	/**
	 * Get a certain group by it's unique name.
	 * @param groupName of the requested group.
	 * @return group if a group with matching name was found, null otherwise.
	 */
	public GroupServer getGroup(String groupName) {
		// TODO : get the group from the database
		return null;
	}
}
