package edu.kit.pse.bdhkw.server.communication;

import java.util.HashMap;

/**
 * Response that, besides the status, contains a HashMap containing arbitrary
 * objects referenced by strings. The receiver has to know the key-set accordingly.
 * Key-dictionary:
 * user_object : when expecting a user in response. (registration)
 * gps_object_list : a List<GpsObject> containing GPS-data of all group-members.
 * member_list : a LinkedList<String> containing the names of all group-members.
 * appointment_object : appointment object (update request).
 * group_name : name of returned group (join request)
 * 
 * @author Tarek Wilkening
 *
 */
public class ObjectResponse extends Response {
	private final HashMap<String, Object> objects = new HashMap<String, Object>();
	
	public ObjectResponse(boolean success) {
		super(success);
	}
	
	public void addObject(String key, Object value) {
		objects.put(key, value);
	}

	public Object getObject(String key) {
		return objects.get(key);
	}
}
