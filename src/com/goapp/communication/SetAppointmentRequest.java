package com.goapp.communication;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.SimpleUser;

public class SetAppointmentRequest extends Request {
	private Appointment targetAppointment;
	private String targetGroupName;

	public SetAppointmentRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

	public Appointment getTargetAppointment() {
		return targetAppointment;
	}

	public void setTargetAppointment(Appointment targetAppointment) {
		this.targetAppointment = targetAppointment;
	}

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}

}
