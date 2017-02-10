package edu.kit.pse.bdhkw.client.communication;
import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;

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
	
	/**
	 * This is for serialization within android
	 */
	protected BroadcastGpsRequest(Parcel in) {
		coordinates = in.readParcelable(GpsObject.class.getClassLoader());
		senderDeviceId = in.readString();
		targetGroupName = in.readString();
		statusGo = in.readByte() != 0;
	}

	public static final Creator<BroadcastGpsRequest> CREATOR = new Creator<BroadcastGpsRequest>() {
        @Override                       
        public BroadcastGpsRequest createFromParcel(Parcel source) {
        	return new BroadcastGpsRequest(source);
        }                                                   
                                                                    
        @Override                                                   
        public BroadcastGpsRequest[] newArray(int size) {
             return new BroadcastGpsRequest[0];
		}
   };                                              
                                                                 
   @Override                                     
   public int describeContents() {                        
        return 0;                                                   
   }                                                                
                                                                    
   @Override                                                        
   public void writeToParcel(Parcel dest, int flags) {        
	   	dest.writeValue(coordinates);
	  	dest.writeString(senderDeviceId);
	   	dest.writeString(targetGroupName);
	   	dest.writeByte((byte) (statusGo ? 1 : 0));
   } 
}
