package edu.kit.pse.bdhkw.client.communication;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Tarek Wilkening
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
		@JsonSubTypes.Type(value=GroupRequest.class, name="GroupRequest_class"),
		@JsonSubTypes.Type(value=RegistrationRequest.class, name="RegistrationRequest_class"),
		@JsonSubTypes.Type(value=RenameUserRequest.class, name="RenameUserRequest_class"),
		@JsonSubTypes.Type(value=CreateGroupRequest.class, name="CreateGroupRequest_class"),
		@JsonSubTypes.Type(value=JoinGroupRequest.class, name="JoinGroupRequest_class")

})
public abstract class Request implements Parcelable {
	protected String senderDeviceId;
	
	// Required by Jackson API's object mapper.
	public Request() {
		// Intentionally left blank.
	}
	/**
	 * Constructor with initialization of sender.
	 * @param senderDeviceId - SimpleUser who created this request.
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
	 * TODO Remove this on client 
	 * Executes this request on the server.
	 * It will manipulate the servers resources via the ResourceManager.
	 * @return response to this request.
	 */
	//public abstract Response execute();
}
