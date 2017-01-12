package com.goapp.server.unused;
import com.goapp.common.model.SimpleUser;
import com.goapp.communication.Request;

public class RegistrationRequest extends Request {
	private String userName;
	private String deviceId;
	
	public RegistrationRequest() {
		super(null);
	}
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
