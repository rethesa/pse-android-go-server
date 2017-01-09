package com.goapp.server.model;

import com.goapp.common.model.SimpleUser;

public class GroupMemberServer extends UserDecoratorServer {

	public GroupMemberServer(SimpleUser user, GroupServer group) {
		super(user, group);
	}

	
}
