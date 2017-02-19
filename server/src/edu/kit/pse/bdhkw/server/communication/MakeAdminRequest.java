package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.MemberAssociation;

@JsonTypeName("MakeAdminRequest_class")
public class MakeAdminRequest extends GroupRequest {
	private int targetUserId;

	public MakeAdminRequest() {
		// TODO Auto-generated constructor stub
	}

	public MakeAdminRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the sender-user from DB
		SimpleUser user = man.getUser(getSenderDeviceId());

		// Get the target group
		GroupServer group = man.getGroup(getTargetGroupName());
		
		if (user == null || group == null) {
			return new Response(false);
		}
		// Get the target member association object
		MemberAssociation mem = group.getMembership(user.getID());
		
		if (mem == null || !group.getMembership(user).isAdmin()) {
			return new Response(false);
		}
		mem = group.getMembership(targetUserId);
		
		if (mem == null || mem.isAdmin()) {
			return new Response(false);
		}
		
		// Perform operation
		mem.setAdmin(true);
		
		// As always
		man.persistObject(group);
		man.psersistObject(mem.getUser());
		
		return new Response(true);
	}

}
