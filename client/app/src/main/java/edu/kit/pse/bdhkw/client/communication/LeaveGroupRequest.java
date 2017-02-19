package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;


import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("LeaveGroupRequest_class")
public class LeaveGroupRequest extends GroupRequest {

	public LeaveGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public LeaveGroupRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
	}

	public LeaveGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public static final Creator<LeaveGroupRequest> CREATOR = new Creator<LeaveGroupRequest>() {
		@Override
		public LeaveGroupRequest createFromParcel(Parcel source) {
			return new LeaveGroupRequest(source);
		}

		@Override
		public LeaveGroupRequest[] newArray(int size) {
			return new LeaveGroupRequest[0];
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
