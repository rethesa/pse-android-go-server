package com.goapp.communication;
import com.goapp.common.model.GpsObject;
import com.goapp.common.model.SimpleUser;
/**
 * @author tarek
 *
 */
public class BroadcastGpsRequest extends Request {
	private GpsObject coordinates;
	private String targetGroupName;
	private boolean statusGo;
	
	public BroadcastGpsRequest(SimpleUser sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}
	
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

}
