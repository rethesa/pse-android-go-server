package com.goapp.server.servlets;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goapp.common.communication.GroupAdminRequest;
import com.goapp.common.communication.Response;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.GroupServer;

@WebServlet("/GroupAdmin")
public class GroupAdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private GroupManager groupManager;
	
	public GroupAdminServlet() {
		super();
		groupManager = new GroupManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println("GroupAdmin up and running!");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // Get json data from request
			byte[] inputJSONData = getJsonData(request);
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Convert JSON String to object
	        GroupAdminRequest message = objectMapper.readValue(inputJSONData, GroupAdminRequest.class);
	     			
	     	Response messageResponse = null;
	        
	     	// Task from request
	     	String task = request.getQueryString();
	     	
	     	switch(task) {
	     	case "kick":
	     		break;
	     	case "delete":
	     		break;
	     	case "invite":
	     		break;
	     	case "rename":
	     		break;
	     	case "appointment":
	     		break;
	     	case "admin":
	     		break;
	     	}
	     	
	     	if (message.isDeleteRequest()) {
	     		messageResponse = processDelete(message);
	     		respond(response, messageResponse);
	     		return;
	     	} 
	     		GroupServer targetGroup = groupManager.getGroup(message.getTargetGroup());
	     		
	     		targetGroup.getMember(message.getSender().getID());
	     		if (message.getUserIdToKick() != 0) {
	     			messageResponse = processKickMember(message);
	     			respond(response, messageResponse);
	     			return;
	     		}
	     		
	        
		} catch (Exception e) {
			// TODO exception handling
			 
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(request.getQueryString() + e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
		}
	        
	}

	private Response processKickMember(GroupAdminRequest request) {
		return null;
	}
	private Response processDelete(GroupAdminRequest request) {
		return null;
	}
	private Response processCreateLink(GroupAdminRequest request) {
		return null;
	}
	private Response processRename(GroupAdminRequest request) {
		return null;
	}
	private Response processSetAppointment(GroupAdminRequest request) {
		return null;
	}
	private Response processSetAdmin(GroupAdminRequest request) {
		return null;
	}
}
