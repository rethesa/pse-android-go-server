package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("GroupRequest_class")
@JsonSubTypes({
	@JsonSubTypes.Type(value=BroadcastGpsRequest.class, name="BroadcastGpsRequest_class"),
	@JsonSubTypes.Type(value=RenameGroupRequest.class, name="RenameGroupRequest_class"),
	//@JsonSubTypes.Type(value=JoinGroupRequest.class, name="JoinGroupRequest_class"),
	//@JsonSubTypes.Type(value=SetAppointmentRequest.class, name="SetAppointmentRequest_class"),
	@JsonSubTypes.Type(value=UpdateRequest.class, name="UpdateRequest_class"),
	@JsonSubTypes.Type(value=CreateLinkRequest.class, name="CreateLinkRequest_class"),
	@JsonSubTypes.Type(value=KickMemberRequest.class, name="KickMemberRequest_class")
})
public abstract class GroupRequest extends Request {
	protected String targetGroupName;

	public GroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public GroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}


}
