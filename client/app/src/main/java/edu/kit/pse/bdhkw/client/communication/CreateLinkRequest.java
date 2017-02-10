package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("CreateLinkRequest_class")
public class CreateLinkRequest extends GroupRequest {
	
	public CreateLinkRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateLinkRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
	}

	public CreateLinkRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public static final Creator<CreateLinkRequest> CREATOR = new Creator<CreateLinkRequest>() {
		@Override
		public CreateLinkRequest createFromParcel(Parcel in) {
			return new CreateLinkRequest(in);
		}

		@Override
		public CreateLinkRequest[] newArray(int size) {
			return new CreateLinkRequest[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(senderDeviceId);
		parcel.writeString(targetGroupName);
	}
}
