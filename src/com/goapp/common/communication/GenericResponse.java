package com.goapp.common.communication;

public class GenericResponse extends Response {
	private Object[] objects;
	
	public GenericResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}
	
}
