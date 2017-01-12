package com.goapp.server.unused;
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
import com.goapp.communication.GroupResponse;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.GroupServer;
import com.goapp.server.model.RequestHandler;

/**
 * NOTE: The suffix of this URL is ignored! Parameters have to be passed 
 * within the request.
 * @author tarek
 *
 */
@WebServlet("/JoinGroup/*")
public class JoinGroupServlet extends HttpServlet {
	private GroupManager groupManager;
	private ObjectMapper objectMapper;
	
	private static final long serialVersionUID = 1L;
	
	public JoinGroupServlet() {
		super();
		groupManager = new GroupManager();
		objectMapper = new ObjectMapper();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getOutputStream().println("Please download and install GoApp!");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // Get json data from request
			byte[] inputJSONData = RequestHandler.getJsonData(request);
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Convert JSON String to object
	        JoinGroupRequest message = objectMapper.readValue(inputJSONData, JoinGroupRequest.class);
	        
	        // Get the targetted group
	        GroupServer targetGroup = groupManager.getGroup(message.getTargetGroup());
	   	        
	        boolean success = targetGroup.join(message.getSender(), message.getInviteLink());
	        
	        GroupResponse r = new GroupResponse(success);
	        
	        // Only send groupData, if operation was legal.
	        if(success) {
	        	r.setGroup(targetGroup);
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
