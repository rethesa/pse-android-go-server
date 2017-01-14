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
import com.goapp.common.communication.GroupRequest;
import com.goapp.common.communication.GroupResponse;
import com.goapp.common.communication.Response;
import com.goapp.common.model.Group;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.RequestHandler;
import com.goapp.server.unused.CreateGroupRequest;
import com.goapp.server.unused.CreateGroupResponse;

@WebServlet("/Group/*")
public class GroupServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private GroupManager groupManager;
	
	public GroupServlet() {
		super();
		groupManager = new GroupManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println("GroupManagerServlet up and running!");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // Get json data from request
			byte[] inputJSONData = getJsonData(request);
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Convert JSON String to object
	        GroupRequest message = objectMapper.readValue(inputJSONData, GroupRequest.class);
	        
	        // Get the task from the request
	        String task = request.getQueryString();
	     			
	     	Response messageResponse = null;
	        
	     	switch(task) {
	     	case "join":
	     		break;
	     	case "leave:":
	     		break;
	     	case "create":
	     		break;
	     	case "update":
	     		break;
	     		default:
	     			break;
	     	}
	        
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
			// TODO exception handling
			 
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(request.getQueryString() + e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
		}
	        
	}
	private GroupResponse processCreateGroup(GroupRequest request) {
		return null;
	}
	private GroupResponse processJoinGroup(GroupRequest request) {
		return null;
	}
	private GroupResponse processLeaveGroup(GroupRequest request) {
		return null;
	}
	private GroupResponse processUpdateGroup(GroupRequest request) {
		return null;
	}
}
