package com.goapp.communication;
import com.goapp.common.model.SimpleUser;

public class CreateGroupRequest extends Request {
	private String groupName;

	public CreateGroupRequest() {
		
	}
	public CreateGroupRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
