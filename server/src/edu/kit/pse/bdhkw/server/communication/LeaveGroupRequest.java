package edu.kit.pse.bdhkw.server.communication;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

public class LeaveGroupRequest extends GroupRequest {

	public LeaveGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public LeaveGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		// Get the user from the database
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Get the group object from the database
		GroupServer group = ResourceManager.getGroup(getTargetGroupName());
		
		// Remove the user from the group
		group.removeMember(user);
		
		// Never forget..!
		ResourceManager.returnGroup(group);
		
		return new Response(true);
	}

}
