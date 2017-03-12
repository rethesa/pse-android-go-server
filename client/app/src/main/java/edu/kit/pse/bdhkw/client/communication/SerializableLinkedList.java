package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.Serializable;

/**
 * Wrapper class for LinkedList<GpsObject>.
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("SerializableLinkedList_class")
public class SerializableLinkedList<T> extends java.util.LinkedList<T> implements Serializable {

	private static final long serialVersionUID = -6129277573659503021L;
}
