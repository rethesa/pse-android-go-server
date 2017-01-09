package com.goapp.communication;
/**
 * In response to the registration request,
 * we send a generated user-ID in return.
 * @author tarek
 *
 */
public class RegistrationResponse extends Response {
	private int userId;
	
	public RegistrationResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	

}
