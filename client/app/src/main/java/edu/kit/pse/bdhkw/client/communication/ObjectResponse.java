package edu.kit.pse.bdhkw.client.communication;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.Serializable;


/**
 * Response that, besides the status, contains a HashMap containing arbitrary
 * objects referenced by strings. The receiver has to know the key-set accordingly.
 * Key-dictionary: (key | Class)
 * user_id : when expecting a user in response. (registration) | SerializableInteger
 * gps_data : GPS-data of all group-members. | SerializableLinkedList
 * member_list : a list containing the names of all group-members. | LinkedListWrapper<SerializableString>
 * appointment : appointment object (update request). | Appointment
 * group_name : name of returned group (join request) | SerializableString
 * invite_link : Link object for a group invite | Link
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
