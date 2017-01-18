package com.goapp.common.model;

public interface UserComponent {

	public String getName();
	
	public int getID();
	
	public String getDeviceId();
	
	public GpsObject getGpsObject();
	
	public void setGpsObject(GpsObject gpsObject);
}
