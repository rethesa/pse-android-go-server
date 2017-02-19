package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("KickMemberRequest_class")
public class KickMemberRequest extends GroupRequest {
	private int targetMemberId;

	public int getTargetMemberId() {
		return targetMemberId;
	}

	public void setTargetMemberId(int targetMemberId) {
		this.targetMemberId = targetMemberId;
	}

	public KickMemberRequest() {
		// TODO Auto-generated constructor stub
	}

	public KickMemberRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
		targetMemberId = in.readInt();
	}

	public KickMemberRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public static final Creator<KickMemberRequest> CREATOR = new Creator<KickMemberRequest>() {
		@Override
		public KickMemberRequest createFromParcel(Parcel source) {
			return new KickMemberRequest(source);
		}

		@Override
		public KickMemberRequest[] newArray(int size) {
			return new KickMemberRequest[0];
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
		parcel.writeInt(targetMemberId);
	}
}
