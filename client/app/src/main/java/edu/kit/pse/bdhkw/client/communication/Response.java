package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.kit.pse.bdhkw.server.communication.ObjectResponse;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=ObjectResponse.class, name="ObjectResponse_class")
	})
public class Response {
	private boolean success;
	
	public Response(boolean success) {
		this.success = success;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
}
