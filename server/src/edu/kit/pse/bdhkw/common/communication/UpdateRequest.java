package edu.kit.pse.bdhkw.common.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.ResourceManager;

@JsonTypeName("UpdateRequest_class")
public class UpdateRequest extends Request {
	private String targetGroupName;

	public UpdateRequest() {
		// TODO Auto-generated constructor stub
	}

	public UpdateRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		// Get the sender user object
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Get the target group
		GroupServer group = ResourceManager.getGroup(targetGroupName);
		
		// Prepare response
		GenericResponse response = new GenericResponse(true);
		
		response.addObject(group);
		
		return response;
	}

}
