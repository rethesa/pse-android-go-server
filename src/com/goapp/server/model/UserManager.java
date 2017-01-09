package com.goapp.server.model;

import com.goapp.common.model.SimpleUser;
import com.goapp.common.model.UserComponent;

/**
 * This is the main interface between the user-database and the servlet
 * @author tarek
 *
 */
public class UserManager {
	/**
	 * Create a new user object and returns it.
	 * @param deviceId
	 * @param name
	 * @return
	 */
	public SimpleUser createUser(String deviceId, String name) {
		// Check if already registered too many times
		if (hasTooManyAccounts(deviceId)) {
			return null;
		}
		return new SimpleUser(deviceId, name, createUserId());	
	}
	/**
	 * Checks if a user has too many accounts mapped to his deviceID.
	 * @param deviceId
	 * @return
	 */
	private boolean hasTooManyAccounts(String deviceId) {
		// TODO Check the database for deviceId's
		return false;
	}
	
	private int createUserId() {
		int userId = (int) Math.random()* 10000000;
		
		// TODO: Check the database for userIDs. 
		// If already in use, create a new one.
		return userId;
	}
	/**
	 * TODO remove user from the database.
	 * @param user
	 */
	public void deleteUser(UserComponent user) {
		// TODO
	}
	/**
	 * NOTE: the changed user must be returned to the userManager
	 * so it can be updated in the database.
	 * @param deviceId
	 * @return
	 */
	public SimpleUser getUserByUserId(int userid) {
		return null;
	}
	/**
	 * NOTE: the changed user must be returned to the userManager
	 * so it can be updated in the database.
	 * @param deviceId
	 * @return
	 */
	public SimpleUser getUserByDevId(String deviceId) {
		// TODO
		return null;
	}
	/**
	 * Updates a modified user object inside the database.
	 * @param user
	 */
	public void returnUser(SimpleUser user) {
		// TODO
	}
}
