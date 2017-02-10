package edu.kit.pse.bdhkw.client.model.objectStructure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.kit.pse.bdhkw.client.communication.SerializableInteger;

/**
 * Created by Tarek on 10.02.2017.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=SimpleUser.class, name="SimpleUser_class"),
        @JsonSubTypes.Type(value=SerializableInteger.class, name="SerializableInteger_class")
})
public interface Serializable {
}
