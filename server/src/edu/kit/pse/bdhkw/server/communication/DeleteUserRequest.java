package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;

@JsonTypeName("DeleteUserRequest_class")
public class DeleteUserRequest extends Request {

	public DeleteUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public DeleteUserRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the user from the database
		SimpleUser user = man.getUser(getSenderDeviceId());
				
		if (user == null) {
			// User is not in the database, so nothing to do.
			return new Response(false);
		} else {

			man.deleteObject(user);

			return new Response(true);
		}
	}
}
