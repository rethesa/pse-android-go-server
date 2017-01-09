package com.goapp.server.model;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public final class RequestHandler {

	public static byte[] getJsonData(HttpServletRequest request) throws IOException {
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
