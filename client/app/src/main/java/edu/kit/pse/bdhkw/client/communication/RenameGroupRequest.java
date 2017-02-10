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
		parcel.writeString(newName);
	}
}
