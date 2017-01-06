package com.goapp.communication;
import com.goapp.client.GroupClient;
import com.goapp.model.SimpleUser;

public class kickMemberRequest extends Request {
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

	public kickMemberRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

}
