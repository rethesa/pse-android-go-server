package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

public@JsonTypeName("RegistrationRequest_class")
 class RegistrationRequest extends Request {
	private String userName;

	public RegistrationRequest() {
		// TODO Auto-generated constructor stub
	}

	public RegistrationRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
