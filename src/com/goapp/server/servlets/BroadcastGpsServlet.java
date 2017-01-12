package com.goapp.server.servlets;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.goapp.communication.BroadcastGpsRequest;
import com.goapp.communication.BroadcastGpsResponse;
import com.goapp.communication.GroupAdminRequest;
import com.goapp.communication.Response;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.GroupServer;
import com.goapp.server.model.RequestHandler;
import com.goapp.server.model.UserDecoratorServer;

@WebServlet("/BroadcastGps")
public class BroadcastGpsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private GroupManager groupManager;
	//private ObjectMapper objectMapper;
	
	public BroadcastGpsServlet() {
		super();
		groupManager = new GroupManager();
		//objectMapper = new ObjectMapper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println("BroadcastGPSServlet up and running!");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // Get json data from request
			byte[] inputJSONData = getJsonData(request);
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Convert JSON String to object
	        BroadcastGpsRequest message = objectMapper.readValue(inputJSONData, BroadcastGpsRequest.class);
	        
	        // TASK
	        String task = request.getQueryString();
	        switch(task) {
	        case "setgo":
	        	break;
	        case "transmit":
	        	break;
	        	default:
	        		break;
	        }
	        /* 
	         * Process the received message...
	         */
	        GroupServer group = groupManager.getGroup(message.getTargetGroupName());
	        UserDecoratorServer user = group.getMember(message.getSender().getID());

	       	BroadcastGpsResponse r = new BroadcastGpsResponse(true);
	       	
	        // Same request to stop broadcast
	        if (message.isStatusGo()){
	        	user.pressGo();
	        	r.setGpsData(group.getGPSData());
	        	
	        } else {
	        	user.unpressGo();
	        }
	       	
	       	// In case the user also provides some GPS data.
	       	if (message.getCoordinates() != null) {
	       		user.setGPSObject(message.getCoordinates());
	       	}
	       	respond(response, r);
	        
		} catch (Exception e) {
			// TODO
			 
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
		}
	        
	}

	private BroadcastGpsResponse processSetGo(BroadcastGpsRequest request) {
		return null;
	}
	private BroadcastGpsResponse processTransmit(BroadcastGpsRequest request) {
		// This depends on whether the user pressed go or not.
		// In case he did, we will set his coordinates in the database.
		// In case he didn't, we just send him the data of other members
		// TODO
		return null;
	}
}
