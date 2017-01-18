package com.goapp.server.trash;

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
import com.goapp.common.communication.Response;
import com.goapp.common.model.SimpleUser;

@WebServlet("/UserManager")
public class UserManagerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Create single UserManager object
	 */
	private UserManager userManager;

	public UserManagerServlet() {
		super();
		userManager = new UserManager();
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
			byte[] inputJSONData = getJsonData(request);

			response.setStatus(HttpServletResponse.SC_OK);

			// Convert JSON String to object
			UserRequest message = objectMapper.readValue(inputJSONData, UserRequest.class);
			
			Response messageResponse = null;
			
			// TODO
			if (message.getName().isEmpty()) {
				// User does not want to create a new account, neither does he want to change his user-name.
				messageResponse = processDeleteUser(message);
			} else if (userManager.getUserByDevId(message.getSender().getDeviceId()) == null) {
				// The user is not registered yet
				messageResponse = processCreateUser(message);
			} else {
				// Rename user
				messageResponse = processRenameUser(message);
			}
			
			respond(response, messageResponse);

		} catch (IOException e) {

			try {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(e.getMessage());
				response.getWriter().close();
			} catch (IOException ioe) {
			}
		}
	}
	
	private UserResponse processRenameUser(UserRequest message) {
		SimpleUser user = userManager.getUserByDevId(message.getSender().getDeviceId());
		
		user.renameUser(message.getName());
		
		UserResponse response = new UserResponse(true);
		response.setFinalUser(user);
		return response;
	}

	private UserResponse processCreateUser(UserRequest request) {
		if (request.getName().length() > 16) {
			return new UserResponse(false);
		}
		
		SimpleUser newUser = userManager.createUser(request.getSender().getDeviceId(), request.getName());
		UserResponse response = new UserResponse(true);
		response.setFinalUser(newUser);
		return response;
	}
	
	private Response processDeleteUser(UserRequest request) {
		userManager.deleteUser(request.getSender());
		
		return new Response(true);
	}
}
