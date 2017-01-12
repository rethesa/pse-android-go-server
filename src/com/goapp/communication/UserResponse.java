package com.goapp.communication;

import com.goapp.common.model.SimpleUser;

public class UserResponse extends Response {
	private SimpleUser finalUser;
	
	public UserResponse(boolean success) {
		super(success);
	}

	public SimpleUser getFinalUser() {
		return finalUser;
	}

	public void setFinalUser(SimpleUser finalUser) {
		this.finalUser = finalUser;
	}
}
