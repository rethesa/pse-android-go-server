package edu.kit.pse.bdhkw.server.communication;


import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Serializable;

/**
 * @author Tarek Wilkening
 * Container for group membership serialization.
 *
 */
//@JsonAppend(prepend=true)
//@Target(value=ElementType.ANNOTATION_TYPE)

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonTypeName("SerializableMember_class")
//@JsonIdentityInfo(property="@id")
public class SerializableMember implements Serializable {
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
