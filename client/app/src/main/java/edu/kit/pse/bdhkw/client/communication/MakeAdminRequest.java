package edu.kit.pse.bdhkw.client.communication;

public class MakeAdminRequest extends GroupRequest {
	private int targetUserId;

	public MakeAdminRequest() {
		// TODO Auto-generated constructor stub
	}

	public MakeAdminRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}
}
