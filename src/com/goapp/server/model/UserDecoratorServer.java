package com.goapp.server.model;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.GpsObject;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;
import com.goapp.common.model.UserComponent;

public abstract class UserDecoratorServer implements UserComponent {
	private boolean pressedGo;
	private GpsObject gpsObject;
	private String deviceId;
	private int id;
	private String name;
	//private SimpleUser simpleUser;
	protected GroupServer group;


	public UserDecoratorServer(SimpleUser user, GroupServer group) {
		this.deviceId = user.getDeviceId();
		this.name = user.getName();
		this.id = user.getID();
		this.group = group;
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
	
	public SimpleUser toSimpleUser() {
		// TODO get simple user from database?
		return new SimpleUser(this.deviceId, this.name, this.id);
	}
	
	public boolean hasPressedGo() {
		return pressedGo;
	}
	
	public void pressGo() {
		pressedGo = true;
	}
	public void unpressGo() {
		pressedGo = false;
	}
	
	public GpsObject getGPSObject() {
		return gpsObject;
	}
	
	public void setGPSObject(GpsObject object) {
		this.gpsObject = object;
	}
	public void kickMember(UserDecoratorServer member) {
		// TODO: report illegal operation
	}
	
	public Link getInviteLink() {	
		// TODO: report illegal operation
		return null;
	}
	
	public void makeUserAdmin(UserDecoratorServer user) {
		// TODO: report illegal operation
	}
	
	public void setAppointment(Appointment appointment) {
		// TODO: report illegal operation
	}
	
	public void deleteGroup() {
		// TODO: report illegal operation
	}
}
