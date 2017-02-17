package edu.kit.pse.bdhkw.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name="GpsObject_table",
		uniqueConstraints={@UniqueConstraint(columnNames={"GPS_ID"})}
)
public class GpsObject implements Serializable {

	private int id;

	private double longitude;

	private double latitude;

	private long timestamp;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="GPS_ID",unique=true,nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

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
	@Column(name="LON", nullable=true,unique=false,length=32)
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double d) {
		this.longitude = d;
	}
	@Column(name="LAT", nullable=true,unique=false,length=32)
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Column(name="TIM",nullable=true,unique=false,length=16)
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
