package edu.kit.pse.bdhkw.common.model;

import java.util.Date;

public class GpsObject {
	private double longitude;
	private double latitude;
	private Date timestamp;
	
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
