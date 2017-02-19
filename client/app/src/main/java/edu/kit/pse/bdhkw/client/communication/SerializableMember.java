package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.Serializable;


/**
 * @author Tarek Wilkening
 * Container for group membership serialization.
 */
//@JsonTypeName("SerializableMember_class")
public class SerializableMember {
	private String name;
	private int id;
	private boolean admin;

	public SerializableMember() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
