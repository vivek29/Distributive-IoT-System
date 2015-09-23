package com.servers;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.Path;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/server2")
public class Lwm2mServer {

	@POST
	@Path("/register/{endpoint}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response produceJSON( @PathParam("endpoint") String endpoint, String reginfo, @Context HttpServletRequest request) throws Exception {
		
		String ip = request.getRemoteAddr();						// get ip
		int remotePort = 8081;										// set port
		String client_addr = ip+":"+remotePort ;
		
		String type = null;
		if(endpoint.contains("thermo")){
			type = "Thermostat";
		}
		else if(endpoint.contains("light")){
			type = "Light Switch";
		}
				
		MongoService.updateServerObject(reginfo);						// update the LWM2MServerObject	
		
		String output = MongoService.CheckIfRegistered(endpoint);
		if(output.equalsIgnoreCase("Registered")){  				// if already registered, than update the registration
			MongoService.UpdateRegisterSuccess(endpoint, reginfo, type);					// register the device again
			MongoService.saveNewIpaddress(endpoint, client_addr);				// save new ip address for future use
		}
		else{			// new registration
			MongoService.RegisterSuccess(endpoint, reginfo, type);		// for a new registration
			MongoService.saveIpaddress(endpoint, client_addr);			// save ip address for future use
		}
							// set lifetime in MongoService
		return Response.status(200).build();	
	}

	@PUT
	@Path("register/{endpoint}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateClient( @PathParam("endpoint") String endpoint, String updateinfo) throws UnknownHostException, JSONException{
		
		String output = MongoService.CheckIfRegistered(endpoint);			// if not returns false
		if(output.equalsIgnoreCase("Registered")){
			MongoService.updateParameters(updateinfo);
		}
		
		return Response.status(200).entity(output).build();	
	}

	@DELETE
	@Path("register/{endpoint}/{objid}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)	
	public Response deregisterClient( @PathParam("endpoint") String endpoint ,  @PathParam("objid") String objid) throws UnknownHostException{
		
		// deregister device from all collection in serverdb
		
		MongoService.DeregisterDevice(endpoint , objid);
		String output3 = "true";
		return Response.status(200).entity(output3).build();
	}
	
	@POST
	@Path("/notify/thermostat/{endpoint}/{objinstance}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response storeObservationValues( @PathParam("endpoint") String endpoint ,  @PathParam("objinstance") String objinstance, String newvalue) throws JSONException, IOException{

			JSONObject jsonObj;
			jsonObj = new JSONObject(newvalue);
			String time = jsonObj.getString("Current Time");					// get time
			String temperature = jsonObj.getString("Temperature"); 				// get temperature

			String messageid = MongoService.getmessageid();
			MongoService.storeObservationValues(messageid, endpoint, objinstance, temperature, time);
			return Response.status(200).build();
	}
	
	@POST
	@Path("/notify/lightswitch/{endpoint}/{objinstance}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response storeCurrentStatusLightSwitch( @PathParam("endpoint") String endpoint ,  @PathParam("objinstance") String objinstance, String newvalue) throws JSONException, IOException{

		JSONObject jsonObj;
		jsonObj = new JSONObject(newvalue);
		String time = jsonObj.getString("Current Time");					// get time
		String status = jsonObj.getString("Current Status"); 				// get temperature

		String messageid = MongoService.getmessageid();
		MongoService.storeCurrentStatusLightSwitch(messageid, endpoint, objinstance, status, time);
		return Response.status(200).build();
	}
		
}