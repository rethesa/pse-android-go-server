package edu.kit.pse.bdhkw.common.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SimpleUser_class")
public class SimpleUser implements UserComponent, Serializable {
	private String name;
	private int id;
	private String deviceId;
	private GpsObject gpsObject;
	
	public SimpleUser() {
		
	}
	public SimpleUser(String deviceId, String name, int id) {
		this.deviceId = deviceId;
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public void renameUser(String newName) {
		this.name = newName;
	}

	@Override
	public GpsObject getGpsObject() {
		return gpsObject;
	}

	@Override
	public void setGpsObject(GpsObject gpsObject) {
		this.gpsObject = gpsObject;
	}
}
