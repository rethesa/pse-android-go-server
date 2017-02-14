package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;
@JsonTypeName("LeaveGroupRequest_class")
public class LeaveGroupRequest extends GroupRequest {

	public LeaveGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public LeaveGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the user from the database
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get the group object from the database
		GroupServer group = man.getGroup(getTargetGroupName());
		
		// Remove the user from the group
		group.removeMember(user);
		
		// Never forget..!
		man.returnGroup(group);
		
		return new Response(true);
	}

}
