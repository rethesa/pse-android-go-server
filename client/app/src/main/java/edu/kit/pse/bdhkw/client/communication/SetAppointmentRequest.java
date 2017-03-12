package edu.kit.pse.bdhkw.client.communication;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

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

	public static final Creator<SetAppointmentRequest> CREATOR = new Creator<SetAppointmentRequest>() {
		@Override
		public SetAppointmentRequest createFromParcel(Parcel source) {
			return new SetAppointmentRequest(source);
		}

		@Override
		public SetAppointmentRequest[] newArray(int size) {
			return new SetAppointmentRequest[0];
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
