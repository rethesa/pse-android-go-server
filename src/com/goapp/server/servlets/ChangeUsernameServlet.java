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
import com.goapp.server.model.RequestHandler;
import com.goapp.server.model.UserManager;
import com.goapp.common.model.SimpleUser;
import com.goapp.communication.ChangeUsernameRequest;
import com.goapp.communication.ChangeUsernameResponse;
import com.goapp.communication.RegistrationRequest;
import com.goapp.communication.RegistrationResponse;

@WebServlet("/ChangeUsername")
public class ChangeUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Create single UserManager object
	 */
	private UserManager userManager;
	private ObjectMapper objectMapper;

	public ChangeUsernameServlet() {
		super();
		userManager = new UserManager();
		objectMapper = new ObjectMapper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getOutputStream().println("ChangeUsernameServlet up and running!");
	}

	/**
	 * Servlets main method called when a post-request was received
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Get the data from the POST request
			byte[] inputJSONData = RequestHandler.getJsonData(request);

			response.setStatus(HttpServletResponse.SC_OK);

			// Convert JSON String to object
			ChangeUsernameRequest message = objectMapper.readValue(inputJSONData, ChangeUsernameRequest.class);

			/*
			 * Process the received message...
			 */
			SimpleUser user = userManager.getUserByUserId(message.getSender().getID());
			
			// TODO check if the new name is invalid for whatever reason
			// TODO change the username and store it in the database
			

			// Create response message
			ChangeUsernameResponse registrationResponse = new ChangeUsernameResponse(true);

			// Convert object to JSON string
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

			// JSON Stringwriter = string??
			StringWriter stringWriter = new StringWriter();
			objectMapper.writeValue(stringWriter, registrationResponse);

			// http response writer
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());

			writer.write(stringWriter.toString());
			writer.flush();
			writer.close();

		} catch (IOException e) {

			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException ioe) {
			}
		}
	}
}
