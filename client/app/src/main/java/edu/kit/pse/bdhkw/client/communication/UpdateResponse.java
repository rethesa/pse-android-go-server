package edu.kit.pse.bdhkw.client.communication;

import java.security.acl.Group;
import java.util.LinkedList;

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
