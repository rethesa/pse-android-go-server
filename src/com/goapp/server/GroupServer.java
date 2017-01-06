package com.goapp.server;

import java.util.LinkedList;

import com.goapp.model.Group;
import com.goapp.model.Link;
/**
 * Server-side group class
 * Stores further information about users, such as individual go-status.
 * Whereas ClientGroup sends requests to the server-side group, the server-side group
 * directly creates invite-links or removes users from the group.
 * @author tarek
 *
 */
public class GroupServer extends Group {
	private LinkedList<Link> inviteLinks;
	
	public Link createLink() {
		LinkGenerator g = new LinkGenerator();
		
		// TODO check if admin?
		
		Link link = g.generateLink(this);
		inviteLinks.add(link);
		return link;
	}

}
