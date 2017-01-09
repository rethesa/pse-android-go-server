package com.goapp.server.model;

import com.goapp.common.model.Appointment;
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
	public void makeUserAdmin(UserDecoratorServer user) {
		group.removeMember(user);
		group.addAdmin(user.getSimpleUser());
	}
	
	@Override
	public void setAppointment(Appointment appointment) {
		group.setAppointment(appointment);
	}
	
	@Override
	public void deleteGroup() {
		group.delete();
	}
}
