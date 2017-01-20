package com.goapp.server.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;

import org.glassfish.jersey.client.ClientConfig;

import com.goapp.common.communication.BroadcastGpsRequest;
import com.goapp.common.communication.BroadcastGpsResponse;
import com.goapp.common.model.GpsObject;

public class SimpleTest {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080").path("tomcat-servlet/GoAppServer/");
		
		BroadcastGpsRequest req = new BroadcastGpsRequest();
		req.setCoordinates(new GpsObject());
		req.setSenderDeviceId("tareks-ultra-sgsII");
		req.setStatusGo(true);
		req.setTargetGroupName("zielgruppeOfDoom");
		//MessageBodyReader<String> reader = new MessageBodyReader<String>();
		Request request = (Request) target.request(MediaType.APPLICATION_JSON_TYPE);
		
		//BroadcastGpsRequest response = target.request(MediaType.APPLICATION_JSON_TYPE)
			//	.post(Entity.entity(req, MediaType.APPLICATION_JSON_TYPE),
				//		BroadcastGpsRequest.class);
	
	}
}