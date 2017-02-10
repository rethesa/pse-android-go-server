package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("CreateGroupRequest_class")
public class CreateGroupRequest extends Request {
	private String newGroupName;

	public CreateGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public CreateGroupRequest(Parcel in) {
		senderDeviceId = in.readString();
		newGroupName = in.readString();
	}

	public String getNewGroupName() {
		return newGroupName;
	}

	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}

    public static final Creator<CreateGroupRequest> CREATOR = new Creator<CreateGroupRequest>() {
        @Override
        public CreateGroupRequest createFromParcel(Parcel source) {
            return new CreateGroupRequest(source);
        }

        @Override
        public CreateGroupRequest[] newArray(int size) {
            return new CreateGroupRequest[0];
        }
    };

    @Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(senderDeviceId);
		parcel.writeString(newGroupName);
	}
}
