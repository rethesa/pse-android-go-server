package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

@JsonTypeName("SetAppointmentRequest_class")
public class SetAppointmentRequest extends GroupRequest {
	private Appointment appointment;

	public SetAppointmentRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public SetAppointmentRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get user object
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get the group object
		GroupServer group = man.getGroup(getTargetGroupName());
		
		// Prepare response object
		Response response;
		
		if (group.getMember(user).isAdmin()) {
			// Perform the requested operation
			group.setAppointment(appointment);
			
			// As always..
			man.returnGroup(group);
			
			// Send confirmation
			response = new Response(true);
		} else {
			response = new Response(false);
		}
		return response;
	}

}
