package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;

import edu.kit.pse.bdhkw.client.model.objectStructure.Serializable;

/**
 * Response that, besides the status, contains a HashMap containing arbitrary
 * objects referenced by strings. The receiver has to know the key-set accordingly.
 * Key-dictionary:
 * user_id : SerializableInteger object when expecting a user ID in response. (registration)
 * gps_object_list : a List<GpsObject> containing GPS-data of all group-members.
 * member_list : a LinkedList<String> containing the names of all group-members.
 * appointment_object : appointment object (update request).
 * group_name : name of returned group (join request)
 * 
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("ObjectResponse_class")
public class ObjectResponse extends Response {
	private HashMap<String, Serializable> objects = new HashMap<String, Serializable>();
	
	public ObjectResponse(boolean success) {
		super(success);
	}

    public ObjectResponse() {
        super();
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

	protected ObjectResponse(Parcel in) {
		super(in.readByte() != 0);
        objects = in.readHashMap(Serializable.class.getClassLoader());

	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (getSuccess() ? 1 : 0));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Response> CREATOR = new Creator<Response>() {
		@Override
		public Response createFromParcel(Parcel in) {
			return new Response(in);
		}

		@Override
		public Response[] newArray(int size) {
			return new Response[size];
		}
	};
}
