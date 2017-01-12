package com.goapp.server.unused;
import com.goapp.client.GroupClient;
import com.goapp.communication.Request;

public class CreateInviteRequest extends Request{
	private String targetGroupName;
	
	public CreateInviteRequest() {
		
	}

	public String getTargetGroup() {
		return targetGroupName;
	}

	public void setTargetGroup(GroupClient targetGroup) {
		this.targetGroupName = targetGroup.getName();
	}
	
	
}
