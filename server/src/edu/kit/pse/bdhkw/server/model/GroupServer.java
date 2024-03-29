package edu.kit.pse.bdhkw.server.model;

import java.util.HashMap;
import java.util.LinkedList;

import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.common.model.UserComponent;
/**
 * Server-side group class
 * Stores further information about users, such as individual go-status.
 * Whereas ClientGroup sends requests to the server-side group, the server-side group
 * directly creates invite-links or removes users from the group.
 * @author tarek
 *
 */
public class GroupServer {
	private LinkedList<Link> inviteLinks;
	/**
	 * Maps users to members/administrators.
	 * ("name", true) means user with name "name" is administrator in this group.
	 */
	private HashMap<String, SimpleMember> isAdminMap;
	
	private String groupname;
	private Appointment appointment;
	
	public GroupServer() {
		isAdminMap = new HashMap<String, SimpleMember>();
		this.appointment = new Appointment();
	}
	public GroupServer(String name) {
		this.groupname = name;
		this.appointment = new Appointment();
		isAdminMap = new HashMap<String, SimpleMember>();
	}
	
	/**
	 * Checks whether or not a given user is administrator of this group.
	 * @param user to be checked for administrator status.
	 * @return true if user is administrator, false otherwise.
	 */
	public SimpleMember getMember(SimpleUser user) {
		return isAdminMap.get(user.getDeviceId());
	}
	
	public Link createInviteLink() {
		LinkGenerator g = new LinkGenerator();
		
		Link link = g.generateLink(this);
		inviteLinks.add(link);
		return link;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	/**
	 * Use link to join group, link is invalidated afterwards.
	 * A joined user is automatically a simple groupMember.
	 * @param user to join the group.
	 * @param inviteLink the user was invited with.
	 * @return true if successful, false otherwise.
	 */
	public boolean join(SimpleUser user, Link inviteLink) {
		if (this.inviteLinks.contains(inviteLink)) {
			// Invalidate invite link
			this.inviteLinks.remove(inviteLink);
			addMember(user);
			return true;
		}
		return false;
	}
	/**
	 * Adds a new administrator to this group.
	 * If the user was a regular groupMember before, he is moved to the admin-list.
	 * Returns false if the user already was an administrator in this group.
	 * @param user to be made administrator.
	 * @return false if user was administrator already, true otherwise.
	 */
	public void addAdmin(SimpleUser user) {
		if (isAdminMap.get(user.getDeviceId()) == null) {
			isAdminMap.put(user.getDeviceId(), new SimpleMember().setAdmin(true));
		}
	}
	/**
	 * Returns a list of all GPS-data of members who pressed go.
	 * @return
	 */
	public LinkedList<GpsObject> getGPSData() {
		LinkedList<GpsObject> data = new LinkedList<GpsObject>();
		
		// Get GPS-Data of all groupMembers
		for (String key : isAdminMap.keySet()) {
			data.push(ResourceManager.getUser(key).getGpsObject());
		}
		return data;
	}
	
	public String getName() {
		return this.groupname;
	}
	public void setName(String name) {
		groupname = name;
	}
	public void addMember(SimpleUser user) {
		isAdminMap.put(user.getDeviceId(), new SimpleMember().setAdmin(false));
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}

	public void removeMember(SimpleUser member) {
		isAdminMap.remove(member.getDeviceId());
		if (isAdminMap.isEmpty()) {
			this.delete();
		}
	}

	public LinkedList<UserComponent> getMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}


}
