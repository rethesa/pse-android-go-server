package edu.kit.pse.bdhkw.server.communication;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Serializable;

/**
 * Response that, besides the status, contains a HashMap containing arbitrary
 * objects referenced by strings. The receiver has to know the key-set accordingly.
 * Key-dictionary:
 * user_object : when expecting a user in response. (registration)
 * gps_object{1 to n} : GPS-data of all group-members.
 * member_list : a LinkedList<String> containing the names of all group-members.
 * appointment_object : appointment object (update request).
 * group_name : name of returned group (join request)
 * invite_link : Link object for a group invite
 * 
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("ObjectResponse_class")
public class ObjectResponse extends Response {
	private HashMap<String, Serializable> objects = new HashMap<String, Serializable>();
	
	public ObjectResponse() {
		super();
	}
	public ObjectResponse(boolean success) {
		super(success);
	}
	
	public HashMap<String, Serializable> getObjects() {
		return objects;
	}
	public void setObjects(HashMap<String, Serializable> objects) {
		this.objects = objects;
	}
	public void addObject(String key, Serializable value) {
		objects.put(key, value);
	}

	public Object getObject(String key) {
		return objects.get(key);
	}
}
