package edu.kit.pse.bdhkw.client.model.objectStructure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.kit.pse.bdhkw.client.communication.SerializableInteger;
import edu.kit.pse.bdhkw.client.communication.SerializableLinkedList;
import edu.kit.pse.bdhkw.client.communication.SerializableMember;
import edu.kit.pse.bdhkw.client.communication.SerializableString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=SimpleUser.class, name="SimpleUser_class"),
	@JsonSubTypes.Type(value=SerializableInteger.class, name="SerializableInteger_class"),
	@JsonSubTypes.Type(value=Appointment.class, name="Appointment_class"),
	//@JsonSubTypes.Type(value=SerializableMember.class, name="SerializableMember_class"),
	@JsonSubTypes.Type(value=SerializableString.class, name="SerializableString_class"),
	@JsonSubTypes.Type(value=Link.class, name="Link_class"),
		@JsonSubTypes.Type(value=GpsObject.class, name="GpsObject_class"),
		@JsonSubTypes.Type(value=SerializableLinkedList.class, name="SerializableLinkedList_class")
	})
public interface Serializable {
	// This is used by Jackson API's objectMapper...
	// (Intentionally left blank)
}
