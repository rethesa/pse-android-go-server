package com.goapp.common.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.goapp.common.model.SimpleUser;
import com.goapp.server.model.ResourceManager;

@JsonTypeName("RenameUserRequest_class")
public class RenameUserRequest extends Request {
	private String newName;
	
	public RenameUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	@Override
	public Response execute() {
		// As always, get the user first
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Perform the operation on the user
		user.renameUser(newName);
		
		// NEVER FORGET
		ResourceManager.returnUser(user);
		
		// Send response
		return new Response(true);
	}
}
