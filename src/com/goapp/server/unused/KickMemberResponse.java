package com.goapp.server.unused;

import com.goapp.communication.Response;
import com.goapp.server.model.GroupServer;

public class KickMemberResponse extends Response {
	// TODO: transform to gorupClient?
	private GroupServer group;
	
	public KickMemberResponse(boolean success) {
		super(success);
	}

	public GroupServer getGroup() {
		return group;
	}

	public void setGroup(GroupServer group) {
		this.group = group;
	}

}
