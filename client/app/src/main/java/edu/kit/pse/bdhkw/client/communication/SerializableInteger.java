package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.Serializable;


@JsonTypeName("SerializableInteger_class")

public class SerializableInteger  implements Serializable {
	public int value;

	public SerializableInteger() {

	}
	public SerializableInteger(int integer) {
		value = integer;
	}
}
