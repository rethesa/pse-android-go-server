package edu.kit.pse.bdhkw.common.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.ResourceManager;

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

	@Override
	public Response execute() {
		// Get the user from the database
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		GenericResponse response = new GenericResponse(true);
		
		Object[] objects = new Object[1];
		
		// Check if already registered (meaning he was in the database)
		if (user == null) {
			// Register user if unregistered
			user = new SimpleUser(getSenderDeviceId(), userName, (int) Math.round(Math.random()*1000000000));
		}
		
		objects[0] = user;
		
		response.setObjects(objects);

		return response;
	}

}
