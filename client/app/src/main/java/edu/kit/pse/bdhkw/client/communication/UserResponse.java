package edu.kit.pse.bdhkw.client.communication;


import edu.kit.pse.bdhkw.common.model.SimpleUser;

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
