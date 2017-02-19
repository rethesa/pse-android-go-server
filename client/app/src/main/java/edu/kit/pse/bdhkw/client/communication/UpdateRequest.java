package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("UpdateRequest_class")
public class UpdateRequest extends GroupRequest {
	public UpdateRequest() {
		// TODO Auto-generated constructor stub
	}

	public UpdateRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
	}

	public UpdateRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public static final Creator<UpdateRequest> CREATOR = new Creator<UpdateRequest>() {
		@Override
		public UpdateRequest createFromParcel(Parcel source) {
			return new UpdateRequest(source);
		}

		@Override
		public UpdateRequest[] newArray(int size) {
			return new UpdateRequest[0];
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
