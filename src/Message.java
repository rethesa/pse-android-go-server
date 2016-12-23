
public class Message {
	private User sender;
	
	public Message(User sender) {
		this.sender = sender;
	}
	
	public User getSender() {
		return this.sender;
	}
	
	public String toJSON() {
		return "{userid:"+this.sender.getID()+",deviceid:"+this.sender.getDeviceId()+"}";
	}
}
