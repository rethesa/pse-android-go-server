package com.goapp.server.unused;
import com.goapp.client.GroupClient;
import com.goapp.common.model.SimpleUser;
import com.goapp.communication.Request;

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
