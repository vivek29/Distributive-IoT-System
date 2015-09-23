package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/write")
public class WriteOperationService {

	@POST
	@Path("/3/{objinstance}/1")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response writeLifetime(@PathParam("objinstance") String objinstance, String newvalue) throws Exception {

		DeviceMongoService.writeLifetime(objinstance, newvalue);		
		return Response.status(200).build();	
	}
	
	@POST
	@Path("/3/{objinstance}/6")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response writeNotificationStoring(@PathParam("objinstance") String objinstance, String newvalue) throws Exception {

		DeviceMongoService.writeNotificationStoring(objinstance, newvalue);		
		return Response.status(200).build();	
	}
	
	@POST
	@Path("/3/{objinstance}/7")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response writeBinding(@PathParam("objinstance") String objinstance, String newvalue) throws Exception {

		DeviceMongoService.writeBinding(objinstance, newvalue);		
		return Response.status(200).build();	
	}
	
}