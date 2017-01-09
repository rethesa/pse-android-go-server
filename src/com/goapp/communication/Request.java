package com.goapp.communication;
import com.goapp.common.model.SimpleUser;

/**
 * @author tarek
 *
 */
public abstract class Request {
	private SimpleUser sender;
	
	/**
	 * Default constructor required by Jackson API
	 */
	public Request() {
		
	}
	public Request(SimpleUser sender) {
		this.sender = sender;
	}
	
	public SimpleUser getSender() {
		return this.sender;
	}
}
