package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("DeleteUserRequest_class")
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

	public static final Creator<DeleteUserRequest> CREATOR = new Creator<DeleteUserRequest>() {
		@Override
		public DeleteUserRequest createFromParcel(Parcel source) {
			return new DeleteUserRequest(source);
		}

		@Override
		public DeleteUserRequest[] newArray(int size) {
			return new DeleteUserRequest[0];
		}
	};
}
