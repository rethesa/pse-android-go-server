package edu.kit.pse.bdhkw.client.communication;

import edu.kit.pse.bdhkw.client.model.objectStructure.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SerializableInteger_class")

public class SerializableInteger implements Serializable {
	public int value;

	public SerializableInteger() {
		// for object mapper
	}
	public SerializableInteger(int integer) {
		value = integer;
	}
}
