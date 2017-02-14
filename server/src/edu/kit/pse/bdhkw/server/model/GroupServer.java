package edu.kit.pse.bdhkw.server.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.LinkedListWrapper;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;

/**
 * Server-side group class
 * Stores further information about users, such as individual go-status.
 * Whereas ClientGroup sends requests to the server-side group, the server-side group
 * directly creates invite-links or removes users from the group.
 * NOTE:
 * After any operation manipulating the group, the group needs to be returned to the database.
 * This is done via ResourceManager.
 * 
 * @author Tarek Wilkening
 *
 */
public class GroupServer {
	/**
	 * Stores all generated invite-link-secrets.
	 * A secret is added, every time a Link was generated,
	 * it is removed, once the link was used by a user to join the group.
	 */
	private final LinkedList<String> secrets = new LinkedList<String>();
	/**
	 * Maps user-device-ID's to SimpleMember objects.
	 * Go-status and administrator properties are bound to a group
	 * and can't be stored in the SimpleUser object directly.
	 */
	private final HashMap<String, SimpleMember> memberMap = new HashMap<String, SimpleMember>();
	
	private String groupname;
	private Appointment appointment;
	
	public GroupServer() {
		this.appointment = new Appointment();
	}
	public GroupServer(String name) {
		this.groupname = name;
		this.appointment = new Appointment();
	}
	
	/**
	 * Returns the member object that represents the given user
	 * in respect to this group.
	 * @param user to be mapped to a group member.
	 * @return member that represents  the given user in this group.
	 */
	public SimpleMember getMember(SimpleUser user) {
		return memberMap.get(user.getDeviceId());
	}
	/**
	 * Creates a new, unique invite-link for this group.
	 * @return link to invite a user to this group.
	 */
	public Link createInviteLink() {
		LinkGenerator g = new LinkGenerator();
		
		Link link = g.generateLink(this);
		secrets.add(link.getSecret());
		return link;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	/**
	 * Use link to join group, link is invalidated afterwards.
	 * Compares the invite link's secret with the ones stored in secrets.
	 * If a matching secret was found, it will be removed from the secrets list
	 * and the given user will get membership of this group.
	 * @param user to join the group.
	 * @param inviteLink the user was invited with.
	 * @return true if successful, false otherwise.
	 */
	public boolean join(SimpleUser user, Link inviteLink) {
		if (this.secrets.contains(inviteLink.getSecret())) {
			// Invalidate invite link
			this.secrets.remove(inviteLink.getSecret());
			
			addMember(user);
			return true;
		}
		return false;
	}
	/**
	 * Adds a new administrator to this group.
	 * Returns false if the user already was an administrator in this group.
	 * @param user to be made administrator.
	 */
	public void addAdmin(SimpleUser user) {
		SimpleMember member = memberMap.get(user);
		
		if (member == null) {
			memberMap.put(user.getDeviceId(), new SimpleMember().setAdmin(true));
		} else {
			member.setAdmin(true);
		}
	}
	/**
	 * TODO: already calculate clusters here?
	 * Returns a list of all GPS-data of members who pressed go.
	 * @return list containing the GPS-data of all group-members.
	 */
	public LinkedListWrapper<GpsObject> getGPSData(ResourceManager man) {
		LinkedListWrapper<GpsObject> data = new LinkedListWrapper<GpsObject>();
		
		// Get GPS-Data of all groupMembers
		for (String key : memberMap.keySet()) {
			if (memberMap.get(key).isStatusGo()) {
				data.push(man.getUser(key).getGpsObject());
			}
		}
		return data;
	}
	
	public String getName() {
		return this.groupname;
	}
	/**
	 * Set the groups name.
	 * NOTE: name must be unique! It will not be checked here!
	 * @param name - new name for the group
	 */
	public void setName(String name) {
		groupname = name;
	}
	private void addMember(SimpleUser user) {
		memberMap.put(user.getDeviceId(), new SimpleMember().setAdmin(false));
	}

	/**
	 * Remove a member from this group.
	 * It doesn't matter if the member has administrator status.
	 * @param member to remove from the group.
	 */
	public void removeMember(SimpleUser member) {
		memberMap.remove(member.getDeviceId());
	}
	/**
	 * Returns a set of all member device-ID's.
	 * @return set of member ID's.
	 */
	public Set<String> getMemberIdSet() {
		return memberMap.keySet();
	}

	public Appointment getAppointment() {
		return this.appointment;
	}
}
