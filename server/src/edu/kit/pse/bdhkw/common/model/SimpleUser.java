package edu.kit.pse.bdhkw.common.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.server.model.MemberAssociation;

@JsonTypeName("SimpleUser_class")
@Entity
@Table(name="users",
		uniqueConstraints={
				@UniqueConstraint(columnNames={"device_id"})
		}
)
/**
 * 
 * @author Tarek Wilkening
 *
 */
public class SimpleUser implements Serializable {

	private String deviceId;
	
	private String name;

	private int userId;
	
	private Set<MemberAssociation> memberAssociations;

	private GpsObject gpsObject = new GpsObject();
	
	public SimpleUser() {
	}
	public SimpleUser(String deviceId, String name) {
		memberAssociations = new HashSet<MemberAssociation>();
		this.deviceId = deviceId;
		this.name = name;

	}

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="device_id")
	public Set<MemberAssociation> getMemberAssociations() {
		return memberAssociations;
	}
	public void setMemberAssociations(Set<MemberAssociation> memberAssociations) {
		this.memberAssociations = memberAssociations;
	}
	
	@Column(name="name",nullable=true,unique=false,length=16)
	public String getName() {
		return this.name;
	}

	@Column(name="user_id", nullable=false, unique=true, length=11)
	public int getID() {
		return userId;
	}
	
	public void setID(int userId) {
		this.userId = userId;
	}
	@Id
	@Column(name="device_id", nullable=false, unique=true,length=64,columnDefinition="VARCHAR(64)")
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="gpsobject_gps_id1", nullable=false)
	public GpsObject getGpsObject() { 
		return gpsObject;
	}

	public void setGpsObject(GpsObject gpsObject) {
		this.gpsObject = gpsObject;
	}
}