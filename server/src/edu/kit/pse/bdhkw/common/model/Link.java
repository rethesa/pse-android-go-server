package edu.kit.pse.bdhkw.common.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * 
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("Link_class")
public class Link implements Serializable {
	private String url;
	private String groupName;
	private String secret;
	
	public Link() {
		// Default constructor
	}
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
