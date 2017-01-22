package edu.kit.pse.bdhkw.common.communication;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.ResourceManager;

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
	public Response execute() {
		// Get the user who sent this request
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Check if user exists
		if (user == null) {
			return new Response(false);
		}
		// Check if the name is already in use
		if (ResourceManager.getGroup(newGroupName) != null) {
			return new Response(false);
		}
		
		// Create the new group
		GroupServer group = new GroupServer(newGroupName);
		
		// Set the creator as administrator
		group.addAdmin(user);
		
		// NEVER FORGET
		ResourceManager.returnGroup(group);
		
		return new Response(true);
	}

}
