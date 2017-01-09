package com.goapp.client;
import java.util.LinkedList;

import com.goapp.common.model.Appointment;
import com.goapp.common.model.Group;
import com.goapp.common.model.Invite;
import com.goapp.common.model.Link;
import com.goapp.common.model.SimpleUser;
import com.goapp.common.model.UserComponent;
import com.goapp.common.model.UserDecorator;
/**
 * Client-side group class
 * Group class that performs server requests on any operation call.
 * Basically the "remote-control" for the server-side group
 * @author tarek
 *
 */
public class GroupClient implements Group {
	// TODO: what do we need the ID for?
	//private int groupId;
	private String groupName;
	
	private Appointment appointment;
	
	private LinkedList<UserComponent> members;
	private GoStatus goStatus;
	
	public GroupClient(String name) {
		this.groupName = name;
		this.goStatus = new GoStatus(this);
		this.appointment = new Appointment();
	}
	
	public String getName() {
		return this.groupName;
	}

	/**
	 * First sends a request to the server to delete the group.
	 * On success, we delete all group-data from the database so we 
	 * don't leave orphans.
	 */
	public void delete() {
		// TODO
	}

	/**
	 * Own go-status relative to this group
	 * @return
	 */
	public GoStatus getGoStatus() {
		return this.goStatus;
	}
	/**
	 * Returns the user object we are relative to this group.
	 * We enter the ID and it returns us either admin or groupMember.
	 * @param ID - our UserID. TODO: SimpleUser object as parameter?
	 * @return Own user relative to this group.
	 */
	public UserDecorator getUser(int ID) {
		return null;
	}
	/**
	 * Sends an update request to the server to check if names of users or the group
	 * have changed. Also if new members joined the group.
	 */
	public void update() {
		
	}
	/**
	 * Sends a create invite link request to the server.
	 * @return new Invite for the group
	 */
	@Override
	public Link createInviteLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMember(UserComponent member) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Sends a kickMemberRequest to the server.
	 * On success the member will also be removed locally.
	 * @param member
	 */
	@Override
	public void removeMember(UserComponent member) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Get the user list of this group from the database and return it.
	 */
	@Override
	public LinkedList<UserComponent> getMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment getAppointment() {
		return this.appointment;
	}

	@Override
	public UserComponent getMember(int ID) {
		// TODO Auto-generated method stub
		return null;
	}
}
