package com.goapp.server.model;

import java.util.Iterator;
import java.util.LinkedList;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.GpsObject;
import com.goapp.common.model.Group;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;
import com.goapp.common.model.UserComponent;
import com.goapp.common.model.UserDecorator;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
/**
 * Server-side group class
 * Stores further information about users, such as individual go-status.
 * Whereas ClientGroup sends requests to the server-side group, the server-side group
 * directly creates invite-links or removes users from the group.
 * @author tarek
 *
 */
public class GroupServer implements Group {
	private LinkedList<Link> inviteLinks;
	//TODO ...
	//private LinkedList<SimpleUser> admins;
	private LinkedList<UserDecoratorServer> members;
	
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
	 * Iterates over the member list and returns a member with matching ID.
	 */
	@Override
	public UserDecoratorServer getMember(int userId) {
		Iterator<UserDecoratorServer> t = members.iterator();
		UserDecoratorServer current;
		while (t.hasNext()) {
			 current = t.next();
			if (current.getID() == userId) {
				return current;
			}
		}
		// Error: user not a member of this group
		return null;
	}
	/**
	 * Checks whether or not a given user is administrator of this group.
	 * @param user to be checked for administrator status.
	 * @return true if user is administrator, false otherwise.
	 *
	public boolean isAdmin(SimpleUser user) {
		return this.admins.contains(user);
	}*/
	
	public Link createInviteLink() {
		LinkGenerator g = new LinkGenerator();
		
		// TODO check if admin?
		
		Link link = g.generateLink(this);
		inviteLinks.add(link);
		return link;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	/**
	 * Use link to join group, link is invalidated afterwards.
	 * @param user to join the group.
	 * @param inviteLink the user was invited with.
	 * @return true if successful, false otherwise.
	 */
	public boolean join(UserComponent user, Link inviteLink) {
		if (this.inviteLinks.contains(inviteLink)) {
			// Invalidate invite link
			this.inviteLinks.remove(inviteLink);
			this.addMember(user);
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
	public boolean addAdmin(SimpleUser user) {
		
		GroupAdminServer admin = new GroupAdminServer(user, this);
		members.add(admin);
		return true;
	}
	/**
	 * Returns a list of all GPS-data of members who pressed go.
	 * @return
	 */
	public LinkedList<GpsObject> getGPSData() {
		LinkedList<GpsObject> data = new LinkedList<GpsObject>();
		Iterator<UserDecoratorServer> t = members.iterator();
		UserDecoratorServer current;
		// Collect GPS-data of every member that pressed go.
		while (t.hasNext()) {
			current = t.next();
			if (current.hasPressedGo()) {
				data.add(current.getGPSObject());
			}
		}
		return data;
	}
	public boolean hasMember(UserDecoratorServer user) {
		return members.contains(user);
	}
	
	@Override
	public String getName() {
		return this.groupname;
	}

	@Override
	public void addMember(UserComponent user) {
		this.members.add(new GroupMemberServer((SimpleUser) user, this));
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMember(UserComponent member) {
		this.members.remove(member);
		if (members.isEmpty()) {
			this.delete();
		}
	}

	@Override
	public LinkedList<UserComponent> getMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment getAppointment() {
		return this.appointment;
	}


}
