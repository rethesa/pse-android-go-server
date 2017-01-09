package com.goapp.common.model;

import java.util.LinkedList;

public interface Group {

	public String getName();

	public Link createInviteLink();

	public void addMember(UserComponent member);

	public void delete();

	public void removeMember(UserComponent member);
	
	public Appointment getAppointment();

	public LinkedList<UserComponent> getMemberList();

	public UserComponent getMember(int ID);
}