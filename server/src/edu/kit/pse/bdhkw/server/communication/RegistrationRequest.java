package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;

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
	public Response execute(ResourceManager man) {
		// Get the user from the database
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		ObjectResponse response = new ObjectResponse(true);
				
		// Check if already registered (meaning he was in the database)
		if (user == null) {
			// Register user if unregistered
			user = new SimpleUser(getSenderDeviceId(), userName);
			
			// TODO: let SQL generate the ID!
			user.setID((int) Math.round(Math.random()*1000000000));
			
			// Never forget
			man.psersistObject(user);
		} else {
			response.setSuccess(false);
		}
		// Send the newly generated ID to the user
		response.addObject("user_id", new SerializableInteger(user.getID()));

		return response;
	}

}
