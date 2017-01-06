package com.goapp.model;
/**
 * This is the main interface between the user-database and the servlet
 * @author tarek
 *
 */
public class UserManager {
	public UserComponent createUser(String deviceid, String name) {
		// Check if already registered
		
		// TODO
		return null;
	}
	
	public void addUser(UserComponent user) {
		// TODO
	}
	
	public void deleteUser(UserComponent user) {
		// TODO
	}
	
	public UserComponent getUserByUserId(int userid) {
		return null;
	}
	
	public UserComponent getUserByDevId(String deviceId) {
		// TODO
		return null;
	}
}
