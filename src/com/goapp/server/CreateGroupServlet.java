package com.goapp.server;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goapp.model.GroupManager;

public class CreateGroupServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private void constructor() {
		// TODO Auto-generated method stub

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        int length = request.getContentLength();
	        
	        // Read JSON data from request
	        byte[] inputJSONData = new byte[length];
	        ServletInputStream sin = request.getInputStream();
	        
	        int c, count = 0 ;
	        while ((c = sin.read(inputJSONData, count, inputJSONData.length-count)) != -1) {
	            count +=c;
	        }
	        sin.close();
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Create objectMapper instance
	        ObjectMapper objectMapper = new ObjectMapper();
	        
	        // Convert JSON String to object
	        CreateGroupMessage message = objectMapper.readValue(inputJSONData, CreateGroupMessage.class);
	        
	        /* 
	         * Process the received message...
	         */
	        GroupManager man = new GroupManager();
	        
	        // Create the new group
	        try {
	        	man.createGroup(message.getSender(), message.getGroupName());
	        } catch (Exception e) {
	        	// TODO what if user is not allowed to create more groups?
	        	// Send Error response
	        	return;
	        }
	        
	        // Response of success
	        
		} catch (Exception e) {
			// TODO
		}
	        
	}

}
