package edu.kit.pse.bdhkw.server.communication;
import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.SerializableLinkedList;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.MemberAssociation;
/**
 * Request to share GPS-Coordinates with a target group.
 * Sharing in this case means to store the coordinates in the database,
 * that way, other group members can request them.
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("BroadcastGpsRequest_class")
public class BroadcastGpsRequest extends GroupRequest {
	private GpsObject coordinates;
	private boolean statusGo;
	
	public boolean isStatusGo() {
		return statusGo;
	}

	public void setStatusGo(boolean statusGo) {
		this.statusGo = statusGo;
	}

	public GpsObject getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(GpsObject coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Response execute(ResourceManager man) {
		// Get the SimpleUser who sent this request
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get the group object next
		GroupServer group = man.getGroup(getTargetGroupName());
		
		if (user == null || group == null) {
			return new Response(false);
		}
		// Set the last known position to the currently provided one
		user.getGpsObject().copy(coordinates);
				
		MemberAssociation mem = group.getMembership(user);
		
		// Check if user is a member of the group
		if (mem == null) {
			return new Response(false);
		}
		
		// Set the status 
		mem.setStatusGo(true);
		
		SerializableLinkedList<GpsObject> list = group.getGPSData(man);
		ObjectResponse response = new ObjectResponse(true);
		response.addObject("gps_list", list);
		
		// NEVER..
		man.persistObject(user);
		man.persistObject(group);
		
		return response;
	}
}
