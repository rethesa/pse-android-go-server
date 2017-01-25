package edu.kit.pse.bdhkw.client.communication;

import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

import java.util.LinkedList;



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
