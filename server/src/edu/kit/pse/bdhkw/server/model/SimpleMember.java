package edu.kit.pse.bdhkw.server.model;

public class SimpleMember {
	private boolean statusGo;
	private boolean isAdmin;
	
	public SimpleMember() {
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public SimpleMember setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}

	public boolean isStatusGo() {
		return statusGo;
	}
	public void setStatusGo(boolean status) {
		statusGo = status;
	}
}
