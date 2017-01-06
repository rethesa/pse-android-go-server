package com.goapp.communication;
import com.goapp.model.SimpleUser;

public class RegistrationRequest extends Request {
	private String userName;
	private String deviceId;
	
	public RegistrationRequest(SimpleUser sender) {
		super(sender);
	}

	public String getName() {
		return userName;
	}

	public void setName(String name) {
		this.userName = name;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
