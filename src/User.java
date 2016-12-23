
public class User {
	private String name;
	private int id;
	private String deviceId;
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
}
