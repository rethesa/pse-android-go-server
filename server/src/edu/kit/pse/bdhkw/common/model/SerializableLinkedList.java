package edu.kit.pse.bdhkw.common.model;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Wrapper class for LinkedList<GpsObject>.
 * @author Tarek Wilkening
 *
 * @param <GpsObject>
 */
@JsonTypeName("SerializableLinkedList_class")
public class SerializableLinkedList<T> extends java.util.LinkedList<T> implements Serializable {

	private static final long serialVersionUID = -6129277573659503021L;
}
