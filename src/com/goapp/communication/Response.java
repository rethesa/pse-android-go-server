package com.goapp.communication;

public abstract class Response {
	private boolean success;
	
	public Response(boolean success) {
		this.success = success;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
}
