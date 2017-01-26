package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("KickMemberRequest_class")
public class KickMemberRequest extends GroupRequest {
	private int targetMemberId;

	public int getTargetMemberId() {
		return targetMemberId;
	}

	public void setTargetMemberId(int targetMemberId) {
		this.targetMemberId = targetMemberId;
	}

	public KickMemberRequest() {
		// TODO Auto-generated constructor stub
	}

	public KickMemberRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}
}
