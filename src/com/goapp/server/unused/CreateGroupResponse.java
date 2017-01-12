package com.goapp.server.unused;
import com.goapp.common.model.Group;
import com.goapp.communication.Response;

public class CreateGroupResponse extends Response {
	private Group newGroup;
	
	public CreateGroupResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public Group getNewGroup() {
		return newGroup;
	}

	public void setNewGroup(Group newGroup) {
		this.newGroup = newGroup;
	}

}
