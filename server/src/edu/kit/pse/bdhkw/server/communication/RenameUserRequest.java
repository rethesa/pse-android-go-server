package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;

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
	public Response execute(ResourceManager man) {
		// As always, get the user first
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		if (user == null) {
			return new Response(false);
		}
		
		// Perform the operation on the user
		user.setName(newName);
		
		// NEVER FORGET
		man.persistObject(user);
		
		// Send response
		return new Response(true);
	}
}
