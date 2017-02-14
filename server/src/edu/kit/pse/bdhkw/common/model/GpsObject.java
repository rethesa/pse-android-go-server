package edu.kit.pse.bdhkw.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name="GpsObject",
		uniqueConstraints={@UniqueConstraint(columnNames={"ID"})}
)
public class GpsObject {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true,length=11)
	private int id;
	
	@Column(name="LON", nullable=true,unique=false,length=32)
	private double longitude;
	
	@Column(name="LAT", nullable=true,unique=false,length=32)
	private double latitude;
	
	@Column(name="TIM",nullable=true,unique=false,length=16)
	private String timestamp;

	public GpsObject() {
		
	}
	/**
	 * Returns the euclidean distance between two GPS-objects.
	 * @param object
	 * @return
	 */
	public int distanceTo(GpsObject object) {
		return (int) Math.round(Math.sqrt(Math.pow((longitude - object.getLongitude()), 2) + Math.pow((latitude - object.getLatitude()), 2)));
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
