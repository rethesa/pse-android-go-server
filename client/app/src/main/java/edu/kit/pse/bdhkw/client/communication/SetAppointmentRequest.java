package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleAppointment;


@JsonTypeName("SetAppointmentRequest_class")
public class SetAppointmentRequest extends GroupRequest {
	private SimpleAppointment appointment;

	public SetAppointmentRequest() {
		// TODO Auto-generated constructor stub
	}

	public SetAppointmentRequest(Parcel in) {
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
		appointment = in.readParcelable(SimpleAppointment.class.getClassLoader());
	}

	public SetAppointmentRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public SimpleAppointment getAppointment() {
		return appointment;
	}

	public void setAppointment(SimpleAppointment appointment) {
		this.appointment = appointment;
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
		parcel.writeParcelable(appointment, i);
	}
}
