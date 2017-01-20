package com.goapp.server.model;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;
import com.goapp.common.model.UserComponent;

public abstract class UserDecoratorServer implements UserComponent {
	private boolean pressedGo;
	private SimpleUser simpleUser;
	protected GroupServer group;


	public UserDecoratorServer(SimpleUser user) {
		this.simpleUser = user;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDeviceId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void leaveGroup() {
		group.removeMember(this);
	}

	public void setGoStatus(boolean status) {
		pressedGo = status;
	}
	
	public boolean getGoStatus() {
		return pressedGo;
	}
	/**
	 * TODO: this returns the user in the state when he joined the group
	 * @return
	 *
	public SimpleUser getSimpleUser() {
		return simpleUser;
	}*/
}
