package com.goapp.communication;
import com.goapp.client.GroupClient;

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
