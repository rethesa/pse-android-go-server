package com.goapp.server.servlets;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.goapp.common.model.Group;
import com.goapp.communication.GroupAdminRequest;
import com.goapp.communication.GroupRequest;
import com.goapp.communication.GroupResponse;
import com.goapp.communication.Response;
import com.goapp.server.model.GroupManager;
import com.goapp.server.model.GroupServer;
import com.goapp.server.model.RequestHandler;
import com.goapp.server.unused.CreateGroupRequest;
import com.goapp.server.unused.CreateGroupResponse;

public abstract class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected ObjectMapper objectMapper;
	
	public BaseServlet() {
		objectMapper = new ObjectMapper();
	}
	
	protected byte[] getJsonData(HttpServletRequest request) throws IOException {
		
	    int length = request.getContentLength();
	    
	    // Read JSON data from request
	    byte[] inputJSONData = new byte[length];
	    ServletInputStream sin = request.getInputStream();
	  
	    int c, count = 0 ;
	    while ((c = sin.read(inputJSONData, count, inputJSONData.length-count)) != -1) {
	        count +=c;
	    }
	    sin.close();
	    
	    return inputJSONData;
	}
	
	protected void respond(HttpServletResponse response, Response messageResponse) throws JsonGenerationException, JsonMappingException, IOException {
		// Convert object to JSON string
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
       
        // JSON Stringwriter
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, messageResponse);
        
        // HTTP response writer
        OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

        writer.write(stringWriter.toString());
        writer.flush();
        writer.close();
	}
}
