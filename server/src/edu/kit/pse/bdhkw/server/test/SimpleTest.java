package edu.kit.pse.bdhkw.server.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import org.glassfish.jersey.client.ClientConfig;

import edu.kit.pse.bdhkw.common.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.common.model.GpsObject;

public class SimpleTest {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080").path("server/GoAppServer/");
		
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