package edu.kit.pse.bdhkw.client.communication;

public class Response {
	private boolean success;
	
	public Response(boolean success) {
		this.success = success;
	}
	
	public boolean getSuccess() {
		return this.success;
	}
}
