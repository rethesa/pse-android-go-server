package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

public class DeleteUserRequest extends Request {

	public DeleteUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public DeleteUserRequest(Parcel in) {
		senderDeviceId = in.readString();
	}

	public DeleteUserRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(senderDeviceId);
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
}
