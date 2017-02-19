package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.Link;

@JsonTypeName("JoinGroupRequest_class")
public class JoinGroupRequest extends GroupRequest {
	private Link link;
	
	public JoinGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public JoinGroupRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
		link = in.readParcelable(Link.class.getClassLoader());
	}

	public JoinGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(senderDeviceId);
		parcel.writeString(targetGroupName);
		parcel.writeParcelable(link, i);
	}

	public static final Creator<JoinGroupRequest> CREATOR = new Creator<JoinGroupRequest>() {
		@Override
		public JoinGroupRequest createFromParcel(Parcel source) {
			return new JoinGroupRequest(source);
		}

		@Override
		public JoinGroupRequest[] newArray(int size) {
			return new JoinGroupRequest[0];
		}
	};
}
