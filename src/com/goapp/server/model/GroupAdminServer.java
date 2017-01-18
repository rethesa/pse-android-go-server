package com.goapp.server.model;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.GpsObject;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;

public class GroupAdminServer extends UserDecoratorServer {
	public GroupAdminServer(SimpleUser user, GroupServer group) {
		super(user, group);
	}
	
	@Override
	public void kickMember(UserDecoratorServer member) {
		group.removeMember(member);
	}
	
	@Override
	public Link getInviteLink() {
		return group.createInviteLink();
	}
	
	@Override
	public void setAppointment(Appointment appointment) {
		group.setAppointment(appointment);
	}
	
	@Override
	public void deleteGroup() {
		group.delete();
	}

	@Override
	public GpsObject getGpsObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGpsObject(GpsObject gpsObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeUserAdmin(UserDecoratorServer user) {
		// TODO Auto-generated method stub
		
	}
}
