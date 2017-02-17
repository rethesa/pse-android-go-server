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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("Appointment_class")
@Entity
@Table(name="Appointment_table",
uniqueConstraints={@UniqueConstraint(columnNames={"AP_ID"})}
)
public class Appointment implements Serializable {

	private int id;
	private Date date;
    private GpsObject destination;
    private String name;
    
    public Appointment() {
    	destination = new GpsObject();
    }
    
    @Column(name="DATE",nullable=true,unique=false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public GpsObject getDestination() {
		return destination;
	}
	public void setDestination(GpsObject destination) {
		this.destination = destination;
	}
	@Column(name="NAME",nullable=true,unique=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AP_ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
