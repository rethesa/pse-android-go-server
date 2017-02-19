package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("RenameGroupRequest_class")
public class RenameGroupRequest extends GroupRequest {
	private String newName;

	public RenameGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public RenameGroupRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
		newName = in.readString();
	}

	public RenameGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}
	
	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public static final Creator<RenameGroupRequest> CREATOR = new Creator<RenameGroupRequest>() {
		@Override
		public RenameGroupRequest createFromParcel(Parcel source) {
			return new RenameGroupRequest(source);
		}

		@Override
		public RenameGroupRequest[] newArray(int size) {
			return new RenameGroupRequest[0];
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
		parcel.writeString(newName);
	}
}
