package com.goapp.server.model;

import com.goapp.common.model.SimpleUser;

public class UserFactory {
	/**
	 * Create a new simple user.
	 * @param deviceId
	 * @param name
	 * @return
	 */
	public static SimpleUser createUser(String deviceId, String name) {
		SimpleUser user = new SimpleUser(deviceId, name, (int) Math.round(Math.random() * 1000000000));
		
		// Don't forget to store the user in the database
		ResourceManager.returnUser(user);
		
		return user;
	}
}
