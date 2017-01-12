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
import com.goapp.communication.Response;
import com.goapp.communication.UserRequest;
import com.goapp.communication.UserResponse;

@WebServlet("/UserManager/*")
public class UserManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Create single UserManager object
	 */
	private UserManager userManager;
	private ObjectMapper objectMapper;

	public UserManagerServlet() {
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
			UserRequest message = objectMapper.readValue(inputJSONData, UserRequest.class);

			// Get the task that the user wants to execute
			String task = request.getQueryString();
			
			Response messageResponse = null;

			if (task == "register") {
				messageResponse = processCreateUser(message);
		
			} else if (task == "rename") {
				messageResponse = processRenameUser(message);
				
			} else if (task == "delete") {
				messageResponse = processDeleteUser(message);
				
			} else {
				// TODO user messed up something...
				messageResponse = new UserResponse(false);
			}

			// Convert object to JSON string
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

			// JSON Stringwriter = string??
			StringWriter stringWriter = new StringWriter();
			objectMapper.writeValue(stringWriter, messageResponse);

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
