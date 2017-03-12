package edu.kit.pse.bdhkw.server.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;

import edu.kit.pse.bdhkw.common.model.Appointment;
import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SerializableLinkedList;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.communication.SerializableMember;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;

/**
 * Server-side group class Stores further information about users, such as
 * individual go-status. Whereas ClientGroup sends requests to the server-side
 * group, the server-side group directly creates invite-links or removes users
 * from the group. NOTE: After any operation manipulating the group, the group
 * needs to be returned to the database. This is done via ResourceManager.
 * 
 * @author Tarek Wilkening
 *
 */
@Entity
@Table(name = "groups", uniqueConstraints = { @UniqueConstraint(columnNames = { "group_name" }) })
public class GroupServer {
	/**
	 * Stores all generated invite-link-secrets. A secret is added, every time a
	 * Link was generated, it is removed, once the link was used by a user to
	 * join the group.
	 */
	private Set<String> secrets = new HashSet<String>();

	private Set<MemberAssociation> memberAssociations = new HashSet<MemberAssociation>();
	private String groupId;
	private Appointment appointment = new Appointment();

	public GroupServer() {
		// Intentionally left blank
	}

	public GroupServer(String name) {
		this.groupId = name;
	}

	@Id
	@Column(name = "group_name", nullable = false, unique = true, length = 64, columnDefinition = "VARCHAR(64)")
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * Set the groups name. NOTE: name must be unique! It will not be checked
	 * here!
	 * @param name - new name for the group
	 */
	public void setGroupId(String name) {
		this.groupId = name;
	}

	@ElementCollection
	@CollectionTable(name = "secrets", joinColumns = @JoinColumn(name = "groups_group_name"))
	@Column(name = "secret")
	public Set<String> getSecrets() {
		return this.secrets;
	}

	public void setSecrets(Set<String> secrets) {
		this.secrets = secrets;
	}

	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "group_name")
	public Set<MemberAssociation> getMemberAssociations() {
		return memberAssociations;
	}

	public void setMemberAssociations(Set<MemberAssociation> memberAssociations) {
		this.memberAssociations = memberAssociations;
	}

	@Transient
	public MemberAssociation getMembership(SimpleUser user) {
		for (MemberAssociation current : memberAssociations) {
			if (current.getUser().getID() == user.getID()) {
				return current;
			}
		}
		return null;
	}

	@Transient
	public MemberAssociation getMembership(int userId) {
		for (MemberAssociation current : memberAssociations) {
			if (current.getUser().getID() == userId) {
				return current;
			}
		}
		return null;
	}

	@Transient
	public SimpleUser getMember(SimpleUser user) {
		MemberAssociation mem = getMembership(user);
		return mem == null ? null : mem.getUser();
	}

	/**
	 * Creates a new, unique invite-link for this group.
	 * 
	 * @return link to invite a user to this group.
	 */
	@Transient
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
	 * Use link to join group, link is invalidated afterwards. Compares the
	 * invite link's secret with the ones stored in secrets. If a matching
	 * secret was found, it will be removed from the secrets Set and the given
	 * user will get membership of this group.
	 * 
	 * @param user
	 *            to join the group.
	 * @param inviteLink
	 *            the user was invited with.
	 * @return true if successful, false otherwise.
	 */
	@Transient
	public boolean join(SimpleUser user, Link inviteLink) {
		if (this.secrets.contains(inviteLink.getSecret())) {
			// Invalidate invite link
			this.secrets.remove(inviteLink.getSecret());

			addMember(user);
			return true;
		}
		return false;
	}

	@Transient
	public SerializableLinkedList<SerializableMember> getSerializableMemberList() {
		// List of group-members
		SerializableLinkedList<SerializableMember> members = new SerializableLinkedList<SerializableMember>();

		// Get the names of all group-members
		for (MemberAssociation ass : getMemberAssociations()) {
			SerializableMember mem = new SerializableMember();
			mem.setName(ass.getUser().getName());
			mem.setId(ass.getUser().getID());
			mem.setAdmin(ass.isAdmin());
			members.add(mem);
		}
		return members;
	}

	/**
	 * Adds a new administrator to this group. Returns false if the user already
	 * was an administrator in this group.
	 * 
	 * @param user
	 *            to be made administrator.
	 */
	public void addAdmin(SimpleUser user) {
		MemberAssociation adminAss = null;
		
		this.addMember(user);

		for (MemberAssociation memAss : this.memberAssociations) {
			if (memAss.getUser().getID() == user.getID()) {
				adminAss = memAss;
			}
		}
		adminAss.setAdmin(true);
	}

	/**
	 * TODO Clustering is currently done on client side, needs to be here.
	 * 
	 * @return Set containing the GPS-data of all group-members.
	 */
	@Transient
	public SerializableLinkedList<GpsObject> getGPSData(ResourceManager man) {
		SerializableLinkedList<GpsObject> data = new SerializableLinkedList<GpsObject>();
		for (MemberAssociation current : memberAssociations) {
			if (current.isStatusGo()) {
				data.add(current.getUser().getGpsObject());
			}
		}
		return data;
	}

	private void addMember(SimpleUser user) {
		for (MemberAssociation current : this.memberAssociations) {
			if (current.getUser().getID() == user.getID()) {
				// User is already a member of this group.
				return;
			}
		}

		MemberAssociation ass = new MemberAssociation();
		ass.setUser(user);
		ass.setGroup(this);
		ass.setAdmin(false);
		memberAssociations.add(ass);

		// Reverse link
		user.getMemberAssociations().add(ass);
	}

	/**
	 * Remove a member from this group. It doesn't matter if the member has
	 * administrator status.
	 * 
	 * @param member
	 *            to remove from the group.
	 */
	public void removeMember(SimpleUser member) { 
		Iterator<MemberAssociation> iterator = memberAssociations.iterator();
		MemberAssociation current;
		while (iterator.hasNext()) { 
			current = iterator.next();
			if (current.getUser().getID() == member.getID()) {
				iterator.remove();
				member.getMemberAssociations().remove(current);
				return;
			}
		}
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointment_ap_id")
	public Appointment getAppointment() {
		return this.appointment;
	}
	/**
	 * Makes this a deep copy of group.
	 * @param group to be copied
	 */
	public void copy(GroupServer group) {
		appointment.copy(group.getAppointment());
		for (MemberAssociation cursor : group.getMemberAssociations()) {
			memberAssociations.add(cursor);
		}
		for (String cursor : group.getSecrets()) {
			secrets.add(cursor);
		}
	}
}
