package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Created by Theresa on 13.01.2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class represents a link to invite new users to a group.
 */
public class Link implements Parcelable {

    private String url;
    private String groupName;
    private String secret;

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

    }
}
