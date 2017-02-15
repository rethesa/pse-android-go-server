package edu.kit.pse.bdhkw.common.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.server.model.MemberAssociation;

@JsonTypeName("SimpleUser_class")
@Entity
@Table(name="SimpleUser_table",
		uniqueConstraints={
				@UniqueConstraint(columnNames={"DEVICE_ID"}),
				@UniqueConstraint(columnNames={"USER_ID"})
		}
)
public class SimpleUser implements UserComponent, Serializable {

	private String deviceId;
	
	private String name;

	private int userId;
	
	private Set<MemberAssociation> memberAssociations;

	private GpsObject gpsObject;
	
	public SimpleUser() {
		this.gpsObject = new GpsObject();
	}
	public SimpleUser(String deviceId, String name, int id) {
		memberAssociations = new HashSet<MemberAssociation>();
		this.deviceId = deviceId;
		this.name = name;
		this.userId = id;
		this.gpsObject = new GpsObject();
	}

	@OneToMany(fetch=FetchType.LAZY)
	public Set<MemberAssociation> getMemberAssociations() {
		return memberAssociations;
	}
	public void setMemberAssociations(Set<MemberAssociation> memberAssociations) {
		this.memberAssociations = memberAssociations;
	}
	
	@Column(name="NAME",nullable=false,unique=false,length=16)
	@Override
	public String getName() {
		return this.name;
	}
	
	@Column(name="USER_ID", nullable=false, unique=true, length=11)
	@Override
	public int getID() {
		//return this.id;
		return userId;
	}
	
	public void setID(int userId) {
		this.userId = userId;
	}
	
	@Id
	@Column(name="DEVICE_ID", nullable=false, unique=true,length=64,columnDefinition="VARCHAR(64)")
	@Override
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	@Override
	public GpsObject getGpsObject() {
		return gpsObject;
	}

	@Override
	public void setGpsObject(GpsObject gpsObject) {
		this.gpsObject = gpsObject;
	}
}