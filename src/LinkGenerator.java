import java.util.UUID;

/**
 * Generate a link for group invitations.
 * The prefix links to our tomcat server.
 * The infix is simply the groups name (which is unique)
 * The suffix is a random generated string with 16 places.
 * @author tarek
 *
 */
public class LinkGenerator {
	private final String baseURL = "http://our-tomcat-server:8080";
	
	public LinkGenerator() {
		
	}
	public Link generateLink(Group group) {
		// Random string
		String secret = UUID.randomUUID().toString();
		
		return new Link(this.baseURL, group.getName(), secret);
	}

}
