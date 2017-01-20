package com.goapp.common.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.goapp.common.model.SimpleUser;
import com.goapp.server.model.GroupServer;
import com.goapp.server.model.ResourceManager;

@JsonTypeName("RenameGroupRequest_class")
public class RenameGroupRequest extends Request {
	private String newName;
	private String targetGroupName;

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

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}

	@Override
	public Response execute() {
		// Get the user from the database
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Get target group
		GroupServer group = ResourceManager.getGroup(getTargetGroupName());
		
		GenericResponse response;
		
		Object[] objects = new Object[1];
		
		// Check if user is administrator of the group
		if (group.getMember(user).isAdmin()) {
			// User is allowed to perform operation
			group.setName(newName);
			
			// NEVER FORGET THIS
			ResourceManager.returnGroup(group);
			
			// Provide the group object as response
			objects[0] = group;
			
			response = new GenericResponse(true);
			response.setObjects(objects);
		} else {
			// In case the user was not allowed to perform operation
			// TODO: ban user from system?
			response = new GenericResponse(false);
		}
		return response;
	}

}
