package com.devices;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.codehaus.jettison.json.JSONException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/{endpoint}/observe/9/{objinstance}")
public class ObserveLightSwitchOperationService {

	static int count;
	
	@GET
	@Path("/1")					
	@Produces(MediaType.TEXT_PLAIN)
	public Response observeAttribute(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance) throws Exception {		

		int def = DeviceMongoService.checkForDefaultLightSwitchValue(objinstance);
		if(def == 0){													// for every new light switch				
			DeviceMongoService.storeInitialLightSwitchTriggerValue(endpoint, objinstance);
		}
		
		count =1;
		Runnable r = new Runnable() {
	         public void run() {
	        	 do{
	     			
	        		String status = null;
					try {
						status = DeviceMongoService.getCurrentStatusLightSwitch(objinstance);
					} catch (UnknownHostException | JSONException e1) {
						e1.printStackTrace();
					}
	        		 
	     			Client client = Client.create();
	     			WebResource webResource;
	     			ClientResponse response;
	     			
	     			DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	     			Date date = new Date();
	     			String time = formatter.format(date);

	     			String newvalue = "{'Current Time' : '"+time+"'" + ","+"'Current Status' : '"+status+"'}";
	     			
	     			webResource = client
	     					.resource("http://vivek:8080/com.273.lwm2m.servers/lwm2m/server2/notify/lightswitch/"+endpoint+"/"+objinstance);
	     			response = webResource.accept("text/plain")
	     					.post(ClientResponse.class,newvalue);
	     			
	     			if (response.getStatus() != 200) {
	     				throw new RuntimeException("Failed : HTTP error code : "
	     						+ response.getStatus());
	     			}
	     			
	     			try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}							// at interval of 10 seconds
	     	}  while(count != 0);								// end of while;	
	         }
	     };
	     new Thread(r).start();    
	     return Response.status(200).build();
	}
	
	@GET
	@Path("/0")					
	@Produces(MediaType.TEXT_PLAIN)
	public Response cancelObserveAttribute(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance) throws Exception {
		
		count = 0;
		String cancel = "Observation Cancelled... Check Devicedb again to verify... \n";
		return Response.status(200).entity(cancel).build();
	}
		
						// FOR WEB UI
	
	@GET
	@Path("/ls")					
	@Produces(MediaType.TEXT_PLAIN)
	public String currentStatusLightSwitch(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance) throws Exception {		

		int def = DeviceMongoService.checkForDefaultLightSwitchValue(objinstance);
		if(def == 0){													// for every new light switch				
			DeviceMongoService.storeInitialLightSwitchTriggerValue(endpoint, objinstance);
		}
	
		String status = DeviceMongoService.getCurrentStatusLightSwitch(objinstance);
		
		return status;
	}
	
	@GET
	@Path("/ls/change/{status}")					
	@Produces(MediaType.TEXT_PLAIN)
	public void triggerLightSwitch(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance, @PathParam("status") String status) throws Exception {

		// changes at device db
		
			DeviceMongoService.triggerLightSwitch(objinstance, status);
		
		// post to server
			Client client = Client.create();
			WebResource webResource;
			ClientResponse response;
			
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
			Date date = new Date();
			String time = formatter.format(date);

			String newvalue = "{'Current Time' : '"+time+"'" + ","+"'Current Status' : '"+status+"'}";
			
			webResource = client
					.resource("http://vivek:8080/com.273.lwm2m.servers/lwm2m/server2/notify/lightswitch/"+endpoint+"/"+objinstance);
			response = webResource.accept("text/plain")
					.post(ClientResponse.class,newvalue);
			
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
		
	}	
	
}