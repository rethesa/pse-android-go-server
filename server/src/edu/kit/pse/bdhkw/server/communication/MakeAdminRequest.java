package edu.kit.pse.bdhkw.server.communication;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.ResourceManager;

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
	public Response execute() {
		// Get the sender-user from DB
		SimpleUser sender = ResourceManager.getUser(getSenderDeviceId());
		
		// Get the target group
		GroupServer group = ResourceManager.getGroup(getTargetGroupName());
		
		// Get the user we want to promote
		SimpleUser newAdmin = ResourceManager.getUser(targetUserId);
		
		if (group.getMember(newAdmin) == null) {
			return new Response(false);
		}
		
		// Perform operation
		group.getMember(newAdmin).setAdmin(true);
		
		// As always
		ResourceManager.returnGroup(group);
		
		return new Response(true);
	}

}
