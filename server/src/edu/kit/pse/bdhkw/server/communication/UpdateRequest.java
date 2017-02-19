package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.MemberAssociation;

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
		
		// Get the target group
		GroupServer group = man.getGroup(getTargetGroupName());
		
		if (user == null || group == null) {
			return new Response(false);
		}
		
		MemberAssociation mem = group.getMembership(user);
		
		if (mem == null) {
			return new Response(false);
		}
		// Prepare response
		ObjectResponse response = new ObjectResponse(true);
		
		response.addObject("group_name", new SerializableString(group.getGroupId()));
		response.addObject("appointment", group.getAppointment());
		response.addObject("member_list", group.getSerializableMemberList());
		response.addObject("gps_data", group.getGPSData(man));
		
		return response;
	}

}
