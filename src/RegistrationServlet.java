import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RegistrationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();
	}
	
	/**
	 * Servlets main method called when a post-request was received
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   
	        try {
	            int length = request.getContentLength();
	            
	            // Read JSON data from request
	            byte[] inputJSONData = new byte[length];
	            ServletInputStream sin = request.getInputStream();
	            
	            int c, count = 0 ;
	            while ((c = sin.read(inputJSONData, count, inputJSONData.length-count)) != -1) {
	                count +=c;
	            }
	            sin.close();
	            response.setStatus(HttpServletResponse.SC_OK);
	            
	            // Create objectMapper instance
	            ObjectMapper objectMapper = new ObjectMapper();
	            
	            // Convert JSON String to object
	            RegistrationMessage message = objectMapper.readValue(inputJSONData, RegistrationMessage.class);
	            
	            /* 
	             * Process the received message...
	             */
	            UserManager man = new UserManager();
	            
	            User user = man.createUser(message.getSender().getDeviceId(), message.getSender().getName());
	            
	            // Create response message 
	            Message m = new Message(user);
	            
	            // Convert object to JSON string
	            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	           
	            // JSON Stringwriter = string??
	            StringWriter stringWriter = new StringWriter();
	            objectMapper.writeValue(stringWriter, m);
	            
	            // http response writer
	            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
	 
	            // Send response string TODO: toString is wrong here
	            writer.write(stringWriter.toString());
	            writer.flush();
	            writer.close();
	 
	 
	 
	        } catch (IOException e) {
	 
	 
	            try{
	                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                response.getWriter().print(e.getMessage());
	                response.getWriter().close();
	            } catch (IOException ioe) {
	            }
	        }   
	        }
}
