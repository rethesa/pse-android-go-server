package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

/*******************************
 * Since the group's name is it's primary key,
 * we should not send this request.
 * -> group needs to be detached from the session
 *******************************/
@JsonTypeName("RenameGroupRequest_class")
public class RenameGroupRequest extends GroupRequest {
	private String newName;

	public RenameGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public RenameGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}
	
	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the user from the database
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get target group
		GroupServer group = man.getGroup(getTargetGroupName());
				
		// Check if user is administrator of the group
		if (group.getMembership(user).isAdmin()) {
			// User is allowed to perform operation
			
			// Check if the name is already in use
			if (man.getGroup(newName) != null) {
				return new Response(false);
			}

			GroupServer renamedGroup = new GroupServer();
			renamedGroup.setGroupId(newName);
			renamedGroup.copy(group);
			
			// NEVER FORGET THIS
			man.deleteObject(group);
			man.persistObject(renamedGroup);
			
			return new Response(true);
		} else {
			// In case the user was not allowed to perform operation
			// TODO: ban user from system?
			return new Response(false);
		}
	}

}
