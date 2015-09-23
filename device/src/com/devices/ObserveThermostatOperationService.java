package com.devices;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@Path("/{endpoint}/observe/8/{objinstance}")
public class ObserveThermostatOperationService {
	
	static int count;
	int temp;
	
	@GET
	@Path("/1")					
	@Produces(MediaType.TEXT_PLAIN)
	public Response observeAttribute(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance) throws Exception {		

		count =1;
		Runnable r = new Runnable() {
	         public void run() {
	        	 do{
	     			
	     			temp = (int)(Math.random() * 9 + 70);								// generate a random no b/w 70-78
	     			String temperature = Double.toString(temp);
	     			Client client = Client.create();
	     			WebResource webResource;
	     			ClientResponse response;
	     			
	     			DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
	     			Date date = new Date();
	     			String time = formatter.format(date);

	     			String newvalue = "{'Current Time' : '"+time+"'" + ","+"'Temperature' : '"+temperature+"'}";
	     			
	     			webResource = client
	     					.resource("http://vivek:8080/com.273.lwm2m.servers/lwm2m/server2/notify/thermostat/"+endpoint+"/"+objinstance);			// send endpoint name and reg info
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
		String cancel = "Observation Cancelled... Check Serverdb again to verify... \n";
		return Response.status(200).entity(cancel).build();
	}
	
					// FOR WEB UI
	
	@GET
	@Path("/thermo")					
	@Produces(MediaType.TEXT_PLAIN)
	public String observeThermostat(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance) throws Exception {		

 			temp = (int)(Math.random() * 9 + 70);								// generate a random no b/w 70-78
 			String temperature = Integer.toString(temp);
 			Client client = Client.create();
 			WebResource webResource;
 			ClientResponse response;
 			
 			DateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
 			Date date = new Date();
 			String time = formatter.format(date);

 			String newvalue = "{'Current Time' : '"+time+"'" + ","+"'Temperature' : '"+temperature+"'}";
 			
 			webResource = client
 					.resource("http://vivek:8080/com.273.lwm2m.servers/lwm2m/server2/notify/thermostat/"+endpoint+"/"+objinstance);			// send endpoint name and reg info
 			response = webResource.accept("text/plain")
 					.post(ClientResponse.class,newvalue);
 			
 			if (response.getStatus() != 200) {
 				throw new RuntimeException("Failed : HTTP error code : "
 						+ response.getStatus());
 			}
	     			
	     return temperature;
	}
	
}