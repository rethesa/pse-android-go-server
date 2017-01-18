package com.goapp.server.servlets;

import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.goapp.common.communication.Request;
import com.goapp.common.communication.Response;
import com.goapp.server.model.RequestHandler;

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper;
	private RequestHandler requestHandler;

	public Servlet() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		requestHandler = new RequestHandler();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		int length = request.getContentLength();

		// Read JSON data from request
		byte[] inputJSONData = new byte[length];

		try {
			ServletInputStream sin = request.getInputStream();

			int c, count = 0;
			while ((c = sin.read(inputJSONData, count, inputJSONData.length - count)) != -1) {
				count += c;
			}
			sin.close();
			response.setStatus(HttpServletResponse.SC_OK);

			// Convert JSON String to object
			Request requestMessage = objectMapper.readValue(inputJSONData, Request.class);
			
			// Process the received message and create a response
			Response responseMessage = requestHandler.handleRequest(requestMessage);

			// Serialize response
			StringWriter stringWriter = new StringWriter();
			objectMapper.writeValue(stringWriter, responseMessage);
			
			 // HTTP response writer
	        OutputStreamWriter outputWriter = new OutputStreamWriter(response.getOutputStream());

	        outputWriter.write(stringWriter.toString());
	        outputWriter.flush();
	        outputWriter.close();
			
		} catch (Exception e) {
			// TODO report error message
		}

	}
}
