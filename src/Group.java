import java.util.LinkedList;

public class Group {
	// TODO: what do we need the ID for?
	//private int groupId;
	private String groupName;
	
	private LinkedList<User> members;
	private LinkedList<Invite> invites;
	
	public Group(String name) {
		// Generate unique group ID;
		this.groupName = name;
	}
	
	public String getName() {
		return this.groupName;
	}

	/**
	 * Creates an invite object for this group
	 * @return new Invite for the group
	 */
	public Invite createInvite() {
		// TODO
		return null;
	}
	
	/**
	 * Adds a user to a group with a valid invite.
	 * The invite is invalidated afterwards.
	 * @param user to add to the group
	 * @param invite object the user was invited with.
	 */
	public void join(User user, Invite invite) {
		// TODO
	}
	
	public void delete() {
		// TODO
	}
	
	public void removeMember(User member) {
		// TODO
	}
	
}
