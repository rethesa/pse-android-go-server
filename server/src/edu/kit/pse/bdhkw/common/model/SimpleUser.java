package edu.kit.pse.bdhkw.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SimpleUser_class")
@Entity
@Table(name="SimpleUser",
		uniqueConstraints={@UniqueConstraint(columnNames={"DEVICE_ID"})}
)
public class SimpleUser implements UserComponent, Serializable {
	@Id
	
	@Column(name="DEVICE_ID", nullable=false, unique=true,length=64)
	private String deviceId;
	
	@Column(name="NAME",nullable=false,unique=false,length=16)
	private String name;

	@Column(name="USER_ID", nullable=false, unique=true, length=11)
	private int userId;
	
	// TEST MAYBE IF WE GIVE LENGTH FOR TWO DOUBLES IT WILL WORK?
	@Column(name="GPS_OBJECT", nullable=true,unique=false,length=11)
	private GpsObject gpsObject;
	
	public SimpleUser() {
		
	}
	public SimpleUser(String deviceId, String name, int id) {
		this.deviceId = deviceId;
		this.name = name;
		this.userId = id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getID() {
		//return this.id;
		return userId;
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
