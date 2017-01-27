package edu.kit.pse.bdhkw.common.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
	@JsonSubTypes.Type(value=SimpleUser.class, name="SimpleUser_class")
	})
public interface Serializable {
	// This is used by Jackson API's objectMapper...
	// (Intentionally left blank)
}
