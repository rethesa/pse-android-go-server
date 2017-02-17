package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

@JsonTypeName("DeleteGroupRequest_class")
public class DeleteGroupRequest extends GroupRequest {

	public DeleteGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public DeleteGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the user who sent the request
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get the target group
		GroupServer group = man.getGroup(getTargetGroupName());
		
		// Prepare response
		Response response;
		
		// Check if user is administrator of the group
		if (group.getMember(user).isAdmin()) {
			// Delete the group from the database
			man.deleteGroup(group);
			
			// Send confirmation
			response = new Response(true);
		} else {
			// User sent illegal request
			response = new Response(false);
		}
		return response;
	}

}
