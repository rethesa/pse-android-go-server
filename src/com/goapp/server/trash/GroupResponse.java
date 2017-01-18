package com.goapp.server.trash;

import com.goapp.common.communication.Response;
import com.goapp.server.model.GroupServer;

public class GroupResponse extends Response {
	private GroupServer group;
	
	public GroupResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public GroupServer getGroup() {
		return group;
	}

	public void setGroup(GroupServer group) {
		this.group = group;
	}

}