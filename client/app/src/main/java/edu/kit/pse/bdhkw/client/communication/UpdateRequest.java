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

	public static final Creator<DeleteGroupRequest> CREATOR = new Creator<DeleteGroupRequest>() {
		@Override
		public DeleteGroupRequest createFromParcel(Parcel source) {
			return new DeleteGroupRequest(source);
		}

		@Override
		public DeleteGroupRequest[] newArray(int size) {
			return new DeleteGroupRequest[0];
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
