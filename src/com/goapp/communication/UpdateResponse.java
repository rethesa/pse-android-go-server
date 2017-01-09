package com.goapp.communication;

import java.util.LinkedList;

import com.goapp.common.model.Group; 

/**
 * Response with a list of all groups that the user requested updates for.
 * @author tarek
 *
 */
public class UpdateResponse extends Response {
	private LinkedList<Group> updatedGroups;

	public UpdateResponse(boolean success) {
		super(success);
		// TODO Auto-generated constructor stub
	}

}
