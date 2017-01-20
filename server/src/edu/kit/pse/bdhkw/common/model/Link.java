package edu.kit.pse.bdhkw.common.model;
/**
 * 
 * @author tarek
 *
 */
public class Link {
	private String url;
	private String groupName;
	private String secret;
	
	public Link(String url, String groupName, String secret) {
		this.url = url;
		this.groupName = groupName;
		this.secret = secret;
	}
	
	public String getGroupName() {
		return this.groupName;
	}
	
	public String getSecret() {
		return this.secret;
	}
	
	public boolean equals(Link link) {
		return this.groupName.equals(link.getGroupName()) &&
			   this.secret.equals(link.getSecret());
	}
	
	public String toString() {
		return this.url + "/"+ this.groupName + "/" + this.secret;
	}
}
