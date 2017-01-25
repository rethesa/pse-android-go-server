package edu.kit.pse.bdhkw.client.communication;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;


public class GroupResponse extends Response {
	private GroupClient group;
	
	public GroupResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public GroupClient getGroup() {
		return group;
	}

	public void setGroup(GroupClient group) {
		this.group = group;
	}

}