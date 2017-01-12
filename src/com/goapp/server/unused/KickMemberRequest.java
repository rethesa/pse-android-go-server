package com.goapp.server.unused;
import com.goapp.client.GroupClient;
import com.goapp.common.model.SimpleUser;
import com.goapp.communication.Request;

public class KickMemberRequest extends Request {
	private String targetGroupName;
	private SimpleUser targetUser;

	public String getTargetGroup() {
		return targetGroupName;
	}

	public void setTargetGroup(GroupClient targetGroup) {
		this.targetGroupName = targetGroup.getName();
	}

	public SimpleUser getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(SimpleUser targetUser) {
		this.targetUser = targetUser;
	}

	public KickMemberRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

}
