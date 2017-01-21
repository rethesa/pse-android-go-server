package edu.kit.pse.bdhkw.common.communication;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Tarek Wilkening
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=BroadcastGpsRequest.class, name="BroadcastGpsRequest_class"),
	@JsonSubTypes.Type(value=RegistrationRequest.class, name="RegistrationRequest_class"),
	@JsonSubTypes.Type(value=RenameGroupRequest.class, name="RenameGroupRequest_class"),
	@JsonSubTypes.Type(value=KickMemberRequest.class, name="KickMemberRequest_class"),
	@JsonSubTypes.Type(value=RenameUserRequest.class, name="RenameUserRequest_class"),
	@JsonSubTypes.Type(value=JoinGroupRequest.class, name="JoinGroupRequest_class"),
	@JsonSubTypes.Type(value=CreateLinkRequest.class, name="CreateLinkRequest_class"),
	@JsonSubTypes.Type(value=SetAppointmentRequest.class, name="SetAppointmentRequest_class"),
	@JsonSubTypes.Type(value=UpdateRequest.class, name="UpdateRequest_class")


	})
public abstract class Request {
	private String senderDeviceId;
	
	// Required by Jackson API's object mapper.
	public Request() {
		// Intentionally left blank.
	}
	/**
	 * Constructor with initialization of sender.
	 * @param sender - SimpleUser who created this request.
	 */
	public Request(String senderDeviceId) {
		this.senderDeviceId = senderDeviceId;
	}
	
	public String getSenderDeviceId() {
		return senderDeviceId;
	}
	public void setSenderDeviceId(String senderDeviceId) {
		this.senderDeviceId = senderDeviceId;
	}
	/**
	 * Executes this request on the server.
	 * It will manipulate the servers resources via the ResourceManager.
	 * @return response to this request.
	 */
	public abstract Response execute();
}
