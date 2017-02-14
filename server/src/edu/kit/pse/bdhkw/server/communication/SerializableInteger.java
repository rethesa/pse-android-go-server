package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Serializable;

@JsonTypeName("SerializableInteger_class")

public class SerializableInteger  implements Serializable {
	public int value;
	
	public SerializableInteger() {
		
	}
	public SerializableInteger(int integer) {
		value = integer;
	}
}
