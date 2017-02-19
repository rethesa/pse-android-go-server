package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.server.controller.ResourceManager;
import edu.kit.pse.bdhkw.server.model.GroupServer;

@JsonTypeName("JoinGroupRequest_class")
public class JoinGroupRequest extends GroupRequest {
	private Link link;
	
	public JoinGroupRequest() {
		// TODO Auto-generated constructor stub
	}

	public JoinGroupRequest(String senderDeviceId) {
		super(senderDeviceId);
		// TODO Auto-generated constructor stub
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	// TODO return GroupClient object ?
	@Override
	public Response execute(ResourceManager man) {
		// Get the user object
		SimpleUser user = man.getUser(getSenderDeviceId());
		
		// Get the group object
		GroupServer group = man.getGroup(link.getGroupName());
		
		if (user != null && group != null && group.join(user, link)){
			// NEVER EVER FORGET !!
			man.persistObject(group);
			man.psersistObject(user);
			
			ObjectResponse response = new ObjectResponse(true);
			
			// Add the groups name
			response.addObject("group_name", new SerializableString(group.getGroupId()));
			
			// Add the names to the response
			response.addObject("member_list", group.getSerializableMemberList());
			
			// Add the appointment to the response
			// TODO: SimpleAppointment?
			response.addObject("appointment", group.getAppointment());
			
			return response;
		} else {
			return new Response(false);
		}
	}

}
