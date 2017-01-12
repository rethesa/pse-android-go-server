package com.goapp.server.unused;
import com.goapp.common.model.SimpleUser;
import com.goapp.communication.Request;

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
