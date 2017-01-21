package edu.kit.pse.bdhkw.common.communication;

import java.util.ArrayList;

public class GenericResponse extends Response {
	//private Object[] objects;
	private ArrayList<Object> objects;
	
	public GenericResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Object> getObjects() {
		return objects;
	}
	
	public void addObject(Object object) {
		objects.add(object);
	}
	
}
