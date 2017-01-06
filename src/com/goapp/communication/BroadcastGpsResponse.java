package com.goapp.communication;

import java.util.LinkedList;

import com.goapp.model.GpsObject;

/**
 * Response to broadcastGPSrequest. Contains the GPS-Data of other group members
 * @author tarek
 *
 */
public class BroadcastGpsResponse extends Response {
	private LinkedList<GpsObject> gpsData;

	public BroadcastGpsResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

}
