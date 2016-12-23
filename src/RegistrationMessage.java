
public class RegistrationMessage extends Message {
	public RegistrationMessage(User sender) {
		super(sender);
	}
	
	@Override
	public String toJSON() {
		return "{register:me}";
	}
}
