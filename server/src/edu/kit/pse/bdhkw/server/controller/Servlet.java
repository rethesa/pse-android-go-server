package edu.kit.pse.bdhkw.server.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.kit.pse.bdhkw.common.communication.Request;
import edu.kit.pse.bdhkw.common.communication.Response;
import edu.kit.pse.bdhkw.server.model.RequestHandler;

@WebServlet("/GoAppServer/")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ObjectMapper objectMapper;
	private RequestHandler requestHandler;

	public Servlet() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		requestHandler = new RequestHandler();
	} 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //try {
			response.getOutputStream().println("Please download and install GoApp @<url>!");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int length = request.getContentLength();

		// Read JSON data from request
		byte[] inputJSONData = new byte[length];
		
		OutputStreamWriter outputWriter;
		
		//try {
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
	        outputWriter = new OutputStreamWriter(response.getOutputStream());

	        outputWriter.write(stringWriter.toString());
	        outputWriter.flush();
	        outputWriter.close();
			
//		} catch (Exception e) {
//			try {
//				outputWriter = new OutputStreamWriter(response.getOutputStream());
//				outputWriter.write(e.getLocalizedMessage());
//				outputWriter.flush();
//				outputWriter.close();
//			} catch (IOException e1) {
//				// Seems like some connection problem, so
//				// simply do nothing, the client shall repeat the request.
//			}
//		}

	}
}
