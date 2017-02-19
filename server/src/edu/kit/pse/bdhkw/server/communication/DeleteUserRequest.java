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
			//HashSet<MemberAssociation> set = (HashSet<MemberAssociation>) user.getMemberAssociations();
			
			// Remove user from all groups
			//for (MemberAssociation cursor : set) {
				//cursor.getGroup().removeMember(user);
				//set.remove(cursor);
				//man.deleteObject(cursor);
				// TODO: not sure if this is necessary
				//man.returnGroup(cursor.getGroup());
			//}
			//man.deleteObject(user.getGpsObject());
			// Save the deleted groups?
			man.deleteObject(user);
			//man.deleteUser(user);
			//man.deleteObject(user.getMemberAssociations().);
			return new Response(true);
		}
	}
}
