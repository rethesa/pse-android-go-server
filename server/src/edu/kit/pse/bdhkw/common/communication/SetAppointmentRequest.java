package edu.kit.pse.bdhkw.common.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.ResourceManager;

@JsonTypeName("SetAppointmentRequest_class")
public class SetAppointmentRequest extends Request {
	private String targetGroupName;
	private Appointment appointment;

	public SetAppointmentRequest() {
		// TODO Auto-generated constructor stub
	}

	public SetAppointmentRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Response execute() {
		// Get user object
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Get the group object
		GroupServer group = ResourceManager.getGroup(targetGroupName);
		
		// Prepare response object
		Response response;
		
		if (group.getMember(user).isAdmin()) {
			// Perform the requested operation
			group.setAppointment(appointment);
			
			// As always..
			ResourceManager.returnGroup(group);
			
			// Send confirmation
			response = new Response(true);
		} else {
			response = new Response(false);
		}
		return response;
	}

}
