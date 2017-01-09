package com.goapp.communication;

import com.goapp.common.model.Link;

public class CreateInviteResponse extends Response {
	private Link inviteLink;
	
	public CreateInviteResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public Link getInviteLink() {
		return inviteLink;
	}

	public void setInviteLink(Link inviteLink) {
		this.inviteLink = inviteLink;
	}
}
