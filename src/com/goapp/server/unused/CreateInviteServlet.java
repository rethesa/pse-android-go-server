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
import com.goapp.common.model.Link;
import com.goapp.communication.BroadcastGpsRequest;
import com.goapp.communication.BroadcastGpsResponse;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.GroupServer;
import com.goapp.server.model.RequestHandler;
import com.goapp.server.model.UserDecoratorServer;

@WebServlet("/CreateInvite")
public class CreateInviteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupManager groupManager;
	private ObjectMapper objectMapper;
	
	public CreateInviteServlet() {
		groupManager = new GroupManager();
		objectMapper = new ObjectMapper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println("CreateInvite up and running!");
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        // Get json data from request
			byte[] inputJSONData = RequestHandler.getJsonData(request);
			
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        // Convert JSON String to object
	        CreateInviteRequest message = objectMapper.readValue(inputJSONData, CreateInviteRequest.class);
	        
	        /* 
	         * Process the received message...
	         */
	        GroupServer group = groupManager.getGroup(message.getTargetGroup());
	        UserDecoratorServer user = group.getMember(message.getSender().getID());
	        
	        // null if user is not an administrator
	        Link inviteLink = user.getInviteLink();

	        CreateInviteResponse r = new CreateInviteResponse(inviteLink != null);
	        r.setInviteLink(inviteLink);
	     
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
