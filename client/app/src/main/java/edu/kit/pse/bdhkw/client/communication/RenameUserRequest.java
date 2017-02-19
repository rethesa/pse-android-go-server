package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RenameUserRequest_class")
public class RenameUserRequest extends Request {
	private String newName;
	
	public RenameUserRequest() {
		// TODO Auto-generated constructor stub
	}

	public RenameUserRequest(Parcel in) {
		senderDeviceId = in.readString();
		newName = in.readString();
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public static final Creator<RenameUserRequest> CREATOR = new Creator<RenameUserRequest>() {
		@Override
		public RenameUserRequest createFromParcel(Parcel source) {
			return new RenameUserRequest(source);
		}

		@Override
		public RenameUserRequest[] newArray(int size) {
			return new RenameUserRequest[0];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(senderDeviceId);
		parcel.writeString(newName);
	}
}
