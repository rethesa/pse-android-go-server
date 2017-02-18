package edu.kit.pse.bdhkw.common.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.kit.pse.bdhkw.server.communication.SerializableInteger;
import edu.kit.pse.bdhkw.server.communication.SerializableMember;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=SimpleUser.class, name="SimpleUser_class"),
	@JsonSubTypes.Type(value=SerializableInteger.class, name="SerializableInteger_class"),
	@JsonSubTypes.Type(value=Appointment.class, name="Appointment_class"),
	@JsonSubTypes.Type(value=SerializableMember.class, name="SerializableMember_class"),
	@JsonSubTypes.Type(value=SerializableMember.class, name="SerializableString_class")
	})
public interface Serializable {
	// This is used by Jackson API's objectMapper...
	// (Intentionally left blank)
}
