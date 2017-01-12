package com.goapp.server.model;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goapp.communication.GroupRequest;

public class RequestHandler {
	private ObjectMapper objectMapper;
	
	public HttpServletResponse processGroupRequest(HttpServletRequest request, HttpServletResponse response) {
		try {
			byte[] input = getJsonData(request);
			
			response.setStatus(HttpServletResponse.SC_OK);
			
			GroupRequest message = objectMapper.readValue(input, GroupRequest.class);
			
			String task = request.getQueryString();
			
			switch(task) {
			case "join":
				// DO shit
				break;
			case "leave":
				break;
			case "create":
				break;
			case "kick":
				break;
			case "invite":
				break;
			case "broadcast":
				break;
			case "update":
				break;
			case "delete":
				break;
			case "appointment":
				
			}
			
			
		} catch (IOException e) {
			// TODO write into response
			e.printStackTrace();
		}

	}	   

	private byte[] getJsonData(HttpServletRequest request) throws IOException {
		
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
}
