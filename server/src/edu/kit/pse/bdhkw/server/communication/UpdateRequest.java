package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

@JsonTypeName("UpdateRequest_class")
public class UpdateRequest extends GroupRequest {
	public UpdateRequest() {
		// TODO Auto-generated constructor stub
	}

	public UpdateRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the sender user object
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		if (user == null) {
			return new Response(false);
		}
		
		// Get the target group
		GroupServer group = man.getGroup(getTargetGroupName());
		
		if (group.getMember(user) == null) {
			return new Response(false);
		}
		// Prepare response
		ObjectResponse response = new ObjectResponse(true);
		
		// TODO make to GroupClient (don't give away more info than necessary)
	//	response.addObject("group_object", group);
		
		return response;
	}

}
