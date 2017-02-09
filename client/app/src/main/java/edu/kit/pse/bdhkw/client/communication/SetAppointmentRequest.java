package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Appointment;

@JsonTypeName("SetAppointmentRequest_class")
public class SetAppointmentRequest extends GroupRequest {
	private Appointment appointment;

	public SetAppointmentRequest() {
		// TODO Auto-generated constructor stub
	}

	public SetAppointmentRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
}
