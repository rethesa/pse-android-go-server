package com.goapp.communication;
import com.goapp.common.model.Group;

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
