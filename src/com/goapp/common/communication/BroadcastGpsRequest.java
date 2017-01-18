package com.goapp.common.communication;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.goapp.common.model.GpsObject;
import com.goapp.common.model.SimpleUser;
import com.goapp.server.model.GroupServer;
import com.goapp.server.model.ResourceManager;
import com.goapp.server.model.UserDecoratorServer;
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

	public void setStatusGo(boolean goStatus) {
		this.statusGo = goStatus;
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
		
		// Get the group member relative to this group
		UserDecoratorServer member = group.getMember(user);
		
		// Set the status 
		member.setGoStatus(statusGo);
		
		// Create Response
		BroadcastGpsResponse response = new BroadcastGpsResponse(true);
		
		// Insert GPS-Data of the group
		response.setGpsData(group.getGPSData());
		
		// Return the result
		return response;
	}
}
