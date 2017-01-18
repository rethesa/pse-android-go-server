package com.goapp.server.trash;

import com.goapp.common.communication.Request;
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

	public void setTargetGroup(String targetGroup) {
		this.targetGroup = targetGroup;
	}

	public Link getInviteLink() {
		return inviteLink;
	}

	public void setInviteLink(Link inviteLink) {
		this.inviteLink = inviteLink;
	}

}
