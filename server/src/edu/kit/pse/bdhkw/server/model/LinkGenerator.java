package edu.kit.pse.bdhkw.server.model;
import java.util.UUID;

import edu.kit.pse.bdhkw.common.model.Link;

/**
 * Generate a link for group invitations.
 * The prefix links to our tomcat server.
 * The infix is simply the groups name (which is unique)
 * The suffix is a random generated string with 16 places.
 * @author tarek
 *
 */
public class LinkGenerator {
	private final String baseURL = "http://localhost:8080/server/Group";
	
	public LinkGenerator() {
		
	}
	public Link generateLink(GroupServer group) {
		// Random string
		String secret = UUID.randomUUID().toString();
		
		return new Link(this.baseURL, group.getName(), secret);
	}

}
