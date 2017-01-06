package com.goapp.communication;

import java.util.LinkedList;

import com.goapp.model.SimpleUser;

public class UpdateRequest extends Request {
	public UpdateRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}


	private LinkedList<String> targetGroupsNames;
	

	public LinkedList<String> getGroups() {
		return this.targetGroupsNames;
	}
}
