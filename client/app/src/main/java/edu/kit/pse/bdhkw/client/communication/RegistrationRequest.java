package edu.kit.pse.bdhkw.client.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

public @JsonTypeName("RegistrationRequest_class") class RegistrationRequest extends Request {
	private String userName;

	public RegistrationRequest() {
		// TODO Auto-generated constructor stub
	}

	public RegistrationRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * This is for serialization within android
	 */
	protected RegistrationRequest(Parcel in) {
		userName = in.readString();
		senderDeviceId = in.readString();
	}

	public static final Creator<RegistrationRequest> CREATOR = new Creator<RegistrationRequest>() {
        @Override                       
        public RegistrationRequest createFromParcel(Parcel source) {               
        	return new RegistrationRequest(source);
        }                                                   
                                                                    
        @Override                                                   
        public RegistrationRequest[] newArray(int size) {
             return new RegistrationRequest[0];                               }                                          
   };                                              
                                                                 
   @Override                                     
   public int describeContents() {                        
        return 0;                                                   
   }                                                                
                                                                    
   @Override                                                        
   public void writeToParcel(Parcel dest, int flags) {              
        dest.writeString(userName);                                 
        dest.writeString(senderDeviceId);                           
   } 
}
