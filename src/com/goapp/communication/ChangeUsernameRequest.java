package com.goapp.communication;
import com.goapp.common.model.SimpleUser;

public class ChangeUsernameRequest extends Request {
	private String newName;
	
	public ChangeUsernameRequest(SimpleUser sender) {
		super(sender);
		
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
}
