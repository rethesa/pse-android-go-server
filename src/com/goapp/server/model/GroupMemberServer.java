package com.goapp.server.model;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.GpsObject;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;

public class GroupMemberServer extends UserDecoratorServer {

	public GroupMemberServer(SimpleUser user) {
		super(user);
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
	
}
