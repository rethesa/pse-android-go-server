package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Serializable;

/**
 * Wrapper class for serializing String objects.
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("SerializableString_class")
public class SerializableString implements Serializable {
	public String value;
	
	public SerializableString() {
		// TODO Auto-generated constructor stub
	}
	public SerializableString(String value) {
		this.value = value;
	}

}
