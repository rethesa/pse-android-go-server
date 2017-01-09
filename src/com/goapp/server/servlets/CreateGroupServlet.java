package com.goapp.server.servlets;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.goapp.common.model.Group;
import com.goapp.communication.CreateGroupRequest;
import com.goapp.communication.CreateGroupResponse;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.RequestHandler;

@WebServlet("/CreateGroup")
public class CreateGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupManager groupManager;
	private ObjectMapper objectMapper;
	
	public CreateGroupServlet() {
		groupManager = new GroupManager();
		objectMapper = new ObjectMapper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println("CreateGroupServlet up and running!");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // Get json data from request
			byte[] inputJSONData = RequestHandler.getJsonData(request);
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Convert JSON String to object
	        CreateGroupRequest message = objectMapper.readValue(inputJSONData, CreateGroupRequest.class);
	        
	        /* 
	         * Process the received message...
	         */
	        Group newGroup = null;
	        
	        // Create the new group
	        try {
	        	newGroup = groupManager.createGroup(message.getSender(), message.getGroupName());
	        } catch (Exception e) {
	        	// TODO what if user is not allowed to create more groups?
	        	// Send Error response
	        	return;
	        }
	        CreateGroupResponse r = new CreateGroupResponse(true);
	        r.setNewGroup(newGroup);
	        
            // Convert object to JSON string
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
           
            // JSON Stringwriter = string??
            StringWriter stringWriter = new StringWriter();
            objectMapper.writeValue(stringWriter, r);
            
            // http response writer
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
 
            writer.write(stringWriter.toString());
            writer.flush();
            writer.close();
	        
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

}
