package com.goapp.communication;
import com.goapp.model.SimpleUser;

/**
 * @author tarek
 *
 */
public abstract class Request {
	private SimpleUser sender;
	
	public Request(SimpleUser sender) {
		this.sender = sender;
	}
	
	public SimpleUser getSender() {
		return this.sender;
	}
}
