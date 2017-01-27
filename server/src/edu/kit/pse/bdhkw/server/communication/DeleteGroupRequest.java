package edu.kit.pse.bdhkw.server.communication;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

public class DeleteGroupRequest extends GroupRequest {

	public DeleteGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public DeleteGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		// Get the user who sent the request
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Get the target group
		GroupServer group = ResourceManager.getGroup(getTargetGroupName());
		
		// Prepare response
		Response response;
		
		// Check if user is administrator of the group
		if (group.getMember(user).isAdmin()) {
			// Delete the group from the database
			ResourceManager.deleteGroup(group);
			
			// Send confirmation
			response = new Response(true);
		} else {
			// User sent illegal request
			response = new Response(false);
		}
		return response;
	}

}
