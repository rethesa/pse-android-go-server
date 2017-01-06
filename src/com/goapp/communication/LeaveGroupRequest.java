package com.goapp.communication;
import com.goapp.client.GroupClient;
import com.goapp.model.SimpleUser;

public class LeaveGroupRequest extends Request {
	private String targetGroupName;
	
	public String getTargetGroup() {
		return targetGroupName;
	}

	public void setTargetGroup(GroupClient targetGroup) {
		this.targetGroupName = targetGroup.getName();
	}

	public LeaveGroupRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}
	

}
