package edu.kit.pse.bdhkw.common.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.ResourceManager;

@JsonTypeName("JoinGroupRequest_class")
public class JoinGroupRequest extends Request {
	private Link link;
	
	public JoinGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public JoinGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		// Get the user object
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Get the group object
		GroupServer group = ResourceManager.getGroup(link.getGroupName());
		
		// Prepare response
		Response response;
		
		if (group.join(user, link)){
			// Return the group object TODO: just return member list..
			response = new GenericResponse(true);
			
			((GenericResponse) response).addObject(group);
		} else {
			response = new Response(false);
		}
		return response;
	}

}
