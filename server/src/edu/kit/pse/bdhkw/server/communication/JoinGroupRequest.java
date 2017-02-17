package edu.kit.pse.bdhkw.server.communication;

import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.kit.pse.bdhkw.common.model.Link;
import edu.kit.pse.bdhkw.common.model.LinkedListWrapper;
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
		
		if (group.join(user, link)){
			// Return the group object TODO: just return member list..
			ObjectResponse response = new ObjectResponse(true);
			
			// List of group-member names
			LinkedListWrapper<String> memberNames = new LinkedListWrapper<String>();
			
			// Get the names of all group-members
			for (String devId : group.getMemberIdSet()) {
				memberNames.add(man.getUser(devId).getName());
			}
			
			// Add the names to the response
			response.addObject("member_list", memberNames);
			
			// Add the appointment to the response
			response.addObject("appointment_object", group.getAppointment());
			
			// Add the group's name to the response
			//response.addObject("group_name", group.getName());
			
			// Add if the user is admin ...
			// ...
			// TODO send GroupClient object for simplicity.
			return response;
		} else {
			return new Response(false);
		}
	}

}
