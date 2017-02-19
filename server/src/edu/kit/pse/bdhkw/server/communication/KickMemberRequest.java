package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.MemberAssociation;

@JsonTypeName("KickMemberRequest_class")
public class KickMemberRequest extends GroupRequest {
	private int targetMemberId;

	public int getTargetMemberId() {
		return targetMemberId;
	}

	public void setTargetMemberId(int targetMemberId) {
		this.targetMemberId = targetMemberId;
	}

	public KickMemberRequest() {
		// TODO Auto-generated constructor stub
	}

	public KickMemberRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the user who sent this request
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get the target group
		GroupServer group = man.getGroup(getTargetGroupName());
		
		if (user == null || group == null) {
			return new Response(false);
		}
		
		// Get the user we want to kick from the group
		MemberAssociation mem = group.getMembership(targetMemberId);
		
		if (mem == null) {
			// User is not a member of this group...
			return new Response(false);
		}
		
		// Prepare response 
		Response response;
		
		// Check if we are administrator of the group
		if (group.getMembership(user).isAdmin() && !mem.isAdmin()) {
			response = new Response(true);
			
			group.removeMember(mem.getUser());
			
			// NEVER FORGET
			man.persistObject(group);
			man.psersistObject(mem.getUser());
		} else {
			response = new Response(false);
		}
		return response;
	}

}
