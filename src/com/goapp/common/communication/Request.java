package com.goapp.common.communication;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.goapp.common.model.SimpleUser;
import com.goapp.server.model.ResourceManager;

/**
 * @author Tarek Wilkening
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=BroadcastGpsRequest.class, name="BroadcastGpsRequest_class"),
	@JsonSubTypes.Type(value=BroadcastGpsRequest.class, name="CreateUserRequest_class")
	
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
