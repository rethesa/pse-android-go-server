package com.goapp.communication;
import com.goapp.client.GroupClient;
import com.goapp.common.model.SimpleUser;

public class ChangeGroupnameRequest extends Request {
	public ChangeGroupnameRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}
	private String newName;
	private String targetGroup;
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getTargetGroup() {
		return targetGroup;
	}
	public void setTargetGroup(GroupClient targetGroup) {
		this.targetGroup = targetGroup.getName();
	}
	
	

}
