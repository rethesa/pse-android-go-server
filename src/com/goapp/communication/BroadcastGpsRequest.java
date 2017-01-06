package com.goapp.communication;
import com.goapp.model.SimpleUser;

import com.goapp.model.GpsObject;
/**
 * @author tarek
 *
 */
public class BroadcastGpsRequest extends Request {
	private GpsObject coordinates;
	private String targetGroup;
	
	public BroadcastGpsRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

	public GpsObject getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(GpsObject coordinates) {
		this.coordinates = coordinates;
	}

}
