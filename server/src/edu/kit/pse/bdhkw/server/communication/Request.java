package edu.kit.pse.bdhkw.server.communication;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.kit.pse.bdhkw.server.controller.ResourceManager;

/**
 * Base type for any request received by the client.
 * @author Tarek Wilkening
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=GroupRequest.class, name="GroupRequest_class"),
	@JsonSubTypes.Type(value=RegistrationRequest.class, name="RegistrationRequest_class"),
	@JsonSubTypes.Type(value=RenameUserRequest.class, name="RenameUserRequest_class"),
	@JsonSubTypes.Type(value=DeleteUserRequest.class, name="DeleteUserRequest_class")
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
	 * @param manager  interface to database
	 * @return response to this request.
	 */
	public abstract Response execute(ResourceManager manager);
}
