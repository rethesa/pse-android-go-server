package edu.kit.pse.bdhkw.common.model;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeName("Appointment_class")
@Entity
@Table(name="appointment",
uniqueConstraints={@UniqueConstraint(columnNames={"ap_id"})}
)
public class Appointment implements Serializable {


	@JsonIgnore private String ap_id;
	private Date date;
    private GpsObject destination;
    private String name;
    
    public Appointment() {
    	destination = new GpsObject();
    }

	@Column(name="date",nullable=true,unique=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="gpsobject_gps_id")
	public GpsObject getDestination() {
		return destination;
	}
	public void setDestination(GpsObject destination) {
		this.destination = destination;
	}
	@Column(name="name",nullable=true,unique=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ap_id",unique=true,nullable=false)
	@JsonIgnore public String getId() {
		return ap_id;
	}
	@JsonIgnore public void setId(String id) {
		this.ap_id = id;
	}
}
