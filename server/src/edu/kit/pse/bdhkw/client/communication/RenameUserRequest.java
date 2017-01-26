package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RenameUserRequest_class")
public class RenameUserRequest extends Request {
	private String newName;
	
	public RenameUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
}
