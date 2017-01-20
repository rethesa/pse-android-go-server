package edu.kit.pse.bdhkw.common.communication;
import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.model.GroupServer;
import edu.kit.pse.bdhkw.server.model.ResourceManager;
/**
 * Request to share GPS-Coordinates with a target group.
 * Sharing in this case means to store the coordinates in the database,
 * that way, other group members can request them.
 * @author Tarek Wilkening
 *
 */
@JsonTypeName("BroadcastGpsRequest_class")
public class BroadcastGpsRequest extends Request {
	private GpsObject coordinates;
	private String targetGroupName;
	private boolean statusGo;
	
	public boolean isStatusGo() {
		return statusGo;
	}

	public void setStatusGo(boolean statusGo) {
		this.statusGo = statusGo;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}
	
	public String getTargetGroupName() {
		return this.targetGroupName;
	}

	public GpsObject getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(GpsObject coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public Response execute() {
		// Get the SimpleUser who sent this request
		SimpleUser user = ResourceManager.getUser(getSenderDeviceId());
		
		// Set the last known position to the currently provided one
		user.setGpsObject(coordinates);
		
		// Get the group object next
		GroupServer group = ResourceManager.getGroup(targetGroupName);
		
		// Set the status 
		group.getMember(user).setStatusGo(statusGo);
		
		// Create Response
		BroadcastGpsResponse response = new BroadcastGpsResponse(true);
		
		// Insert GPS-Data of the group
		response.setGpsData(group.getGPSData());
		
		// Return the result
		return response;
	}
}
