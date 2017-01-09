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
import com.goapp.communication.RegistrationRequest;
import com.goapp.communication.RegistrationResponse;

@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Create single UserManager object
	 */
	private UserManager userManager;
	private ObjectMapper objectMapper;

	public RegistrationServlet() {
		super();
		userManager = new UserManager();
		objectMapper = new ObjectMapper();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getOutputStream().println("RegistrationServlet up and running!");
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
			RegistrationRequest message = objectMapper.readValue(inputJSONData, RegistrationRequest.class);

			/*
			 * Process the received message...
			 */
			boolean success = true;
			if (message.getName().length() > 16) {
				success = false;
			}

			// Create response message
			RegistrationResponse registrationResponse = new RegistrationResponse(success);

			// Create the new user (null on error)
			SimpleUser user = userManager.createUser(message.getDeviceId(), message.getName());

			if (user != null) {
				// Return the newly generated ID to the user
				registrationResponse.setUserId(user.getID());
			} else {
				success = false;
			}

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
