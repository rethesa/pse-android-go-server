package com.goapp.server.model;

import com.goapp.common.model.SimpleUser;
import com.goapp.common.model.UserComponent;

/**
 * This is the main interface between the user-database and the servlet
 * @author tarek
 *
 */
public class UserManager {
	public SimpleUser createUser(String deviceId, String name) {
		// Check if already registered too many times
		if (hasTooManyAccounts(deviceId)) {
			return null;
		}
		return new SimpleUser(deviceId, name, createUserId());	
	}

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
	
	public void addUser(UserComponent user) {
		// TODO
	}
	
	public void deleteUser(UserComponent user) {
		// TODO
	}
	
	public SimpleUser getUserByUserId(int userid) {
		return null;
	}
	
	public SimpleUser getUserByDevId(String deviceId) {
		// TODO
		return null;
	}
}
