package edu.kit.pse.bdhkw.common.model;


import java.util.Date;

public class Appointment implements Serializable {
	private Date date;
    	private GpsObject destination;
    	private String name;
    
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public GpsObject getDestination() {
		return destination;
	}
	public void setDestination(GpsObject destination) {
		this.destination = destination;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
