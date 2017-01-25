package edu.kit.pse.bdhkw.client.communication;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.Link;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

public class GroupRequest extends Request {
	private String targetGroup;
	private Link inviteLink;
	
	public GroupRequest(SimpleUser sender) {
		super(sender);
	}

	public String getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(GroupClient targetGroup) {
		this.targetGroup = targetGroup.getGroupName();
	}

	public Link getInviteLink() {
		return inviteLink;
	}

	public void setInviteLink(Link inviteLink) {
		this.inviteLink = inviteLink;
	}

}
