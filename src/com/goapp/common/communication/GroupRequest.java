package com.goapp.common.communication;

import com.goapp.client.GroupClient;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;

public class GroupRequest extends Request {
	private String targetGroup;
	private Link inviteLink;
	
	public GroupRequest(SimpleUser sender) {
		super(sender);
	}

	public String getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(GroupClient targetGroup) {
		this.targetGroup = targetGroup.getName();
	}

	public Link getInviteLink() {
		return inviteLink;
	}

	public void setInviteLink(Link inviteLink) {
		this.inviteLink = inviteLink;
	}

}
