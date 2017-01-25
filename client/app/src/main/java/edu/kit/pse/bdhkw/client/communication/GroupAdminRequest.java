package edu.kit.pse.bdhkw.client.communication;


import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

public class GroupAdminRequest extends GroupRequest {
	private Appointment appointmentToSet;
	private int userIdToKick;
	private String groupNameToSet;
	private boolean deleteRequest;

	public GroupAdminRequest(SimpleUser sender) {
		super(sender);
		userIdToKick = 0;
	}

	public Appointment getAppointmentToSet() {
		return appointmentToSet;
	}

	public void setAppointmentToSet(Appointment appointmentToSet) {
		this.appointmentToSet = appointmentToSet;
	}

	public int getUserIdToKick() {
		return userIdToKick;
	}

	public void setUserIdToKick(int userIdToKick) {
		this.userIdToKick = userIdToKick;
	}

	public String getGroupNameToSet() {
		return groupNameToSet;
	}

	public void setGroupNameToSet(String groupNameToSet) {
		this.groupNameToSet = groupNameToSet;
	}

	public boolean isDeleteRequest() {
		return deleteRequest;
	}

	public void setDeleteRequest(boolean deleteThisGroup) {
		this.deleteRequest = deleteThisGroup;
	}
}
