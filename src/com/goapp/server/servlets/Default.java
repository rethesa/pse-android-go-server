package com.goapp.server.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/default/")
public class Default extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Default() {
	} 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //try {
			response.getOutputStream().println("Please download and install GoApp @<url>!");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
    }
}
