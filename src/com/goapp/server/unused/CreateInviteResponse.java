package com.goapp.server.unused;

import com.goapp.common.model.Link;
import com.goapp.communication.Response;

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
