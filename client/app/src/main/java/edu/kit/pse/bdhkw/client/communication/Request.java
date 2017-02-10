package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;
import android.os.Parcelable;

import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

/**
 * @author tarek
 *
 */
public abstract class Request implements Parcelable {
	private SimpleUser sender;
	
	/**
	 * Default constructor required by Jackson API
	 */
	public Request() {
		
	}

	public Request(SimpleUser sender) {
		sender = sender;
	}

	protected Request(Parcel user) {
		sender = user.readInt();
	}

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel user) {
            return new Request(user);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, SimpleUser simpleUser) {
        parcel.writeInt(simpleUser);
    }

    public SimpleUser getSender() {
		return this.sender;
	}
}
