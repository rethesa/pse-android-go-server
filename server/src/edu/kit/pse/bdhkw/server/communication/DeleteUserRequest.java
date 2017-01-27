package edu.kit.pse.bdhkw.server.communication;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;

public class DeleteUserRequest extends Request {

	public DeleteUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public DeleteUserRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		// Get the user from the database
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
				
		if (user == null) {
			// User is not in the database, so nothing to do.
			return new Response(false);
		} else {
			// Delete the user
			ResourceManager.deleteUser(user);
			
			return new Response(true);
		}
	}
}
