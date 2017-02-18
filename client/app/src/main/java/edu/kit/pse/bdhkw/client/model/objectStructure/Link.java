package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Created by Theresa on 13.01.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * This class represents a link to invite new users to a group.
 */
@JsonTypeName("Link_class")
public class Link implements Parcelable, Serializable {

    private String url;
    private String groupName;
    private String secret;

    public Link() {
        //default
    }

    /**
     * Instantiates a new Link obejct.
     *
     * @param url       the base url
     * @param groupName the group name
     * @param secret    the secret; should be generated randomly
     */
    public Link(String url, String groupName, String secret) {
        this.url = url;
        this.groupName = groupName;
        this.secret = secret;
    }

    public Link(Parcel in) {
        url = in.readString();
        groupName = in.readString();
        secret = in.readString();
    }

   	public static final Creator<Link> CREATOR = new Creator<Link>() {
		@Override
		public Link createFromParcel(Parcel source) {
			return new Link(source);
		}

		@Override
		public Link[] newArray(int size) {
			return new Link[0];
		}
	};
    @Override
    public String toString() {
        return this.url + "/" + this.groupName + "/" + this.secret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(groupName);
        parcel.writeString(secret);
    }
}
