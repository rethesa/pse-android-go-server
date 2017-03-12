package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;


@JsonTypeName("CreateGroupRequest_class")
public class CreateGroupRequest extends Request {
	private String newGroupName;

	public CreateGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public String getNewGroupName() {
		return newGroupName;
	}

	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the user who sent this request
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Check if user exists
		if (user == null) {
			return new Response(false);
		}
		// Check if the name is already in use
		if (man.getGroup(newGroupName) != null) {
			return new Response(false);
		}
		
		// Create the new group
		GroupServer group = new GroupServer(newGroupName);
		
		// Set the creator as administrator
		group.addAdmin(user);
		
		// NEVER FORGET
		man.persistObject(group);
		man.persistObject(user);
		
		return new Response(true);
	}

}
