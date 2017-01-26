package edu.kit.pse.bdhkw.client.communication;

public class CreateGroupRequest extends Request {
	private String newGroupName;

	public CreateGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public String getNewGroupName() {
		return newGroupName;
	}

	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}
}
