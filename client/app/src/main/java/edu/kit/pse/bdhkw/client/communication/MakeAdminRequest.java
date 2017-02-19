package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;
@JsonTypeName("MakeAdminRequest_class")
public class MakeAdminRequest extends GroupRequest {
	private int targetUserId;

	public MakeAdminRequest() {
		// TODO Auto-generated constructor stub
	}

	public MakeAdminRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
		targetUserId = in.readInt();
	}

	public MakeAdminRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}

	public static final Creator<MakeAdminRequest> CREATOR = new Creator<MakeAdminRequest>() {
		@Override
		public MakeAdminRequest createFromParcel(Parcel source) {
			return new MakeAdminRequest(source);
		}

		@Override
		public MakeAdminRequest[] newArray(int size) {
			return new MakeAdminRequest[0];
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
		parcel.writeInt(targetUserId);
	}
}
