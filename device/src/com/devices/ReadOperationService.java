package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/read")
public class ReadOperationService {

	@GET
	@Path("/1/{objinstance}/0")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getShortServerID(@PathParam("objinstance") String objinstance) throws Exception {

		String read1 = DeviceMongoService.getShortServerid(objinstance);		
		return Response.status(200).entity(read1).build();	
	}
	
	@GET
	@Path("/1/{objinstance}/1")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLifetime(@PathParam("objinstance") String objinstance) throws Exception {

		String read2 = DeviceMongoService.getLifetime(objinstance);		
		return Response.status(200).entity(read2).build();	
	}
	
	@GET
	@Path("/1/{objinstance}/6")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationStoring(@PathParam("objinstance") String objinstance) throws Exception {

		String read3 = DeviceMongoService.getNotificationStoring(objinstance);	
		return Response.status(200).entity(read3).build();	
	}
	
	@GET
	@Path("/1/{objinstance}/7")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBinding(@PathParam("objinstance") String objinstance) throws Exception {

		String read4 = DeviceMongoService.getBinding(objinstance);	
		return Response.status(200).entity(read4).build();	
	}
	
	@GET
	@Path("/3/{objinstance}/0")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManufacturer(@PathParam("objinstance") String objinstance) throws Exception {

		String read5 = DeviceMongoService.getManufacturer(objinstance);		
		return Response.status(200).entity(read5).build();	
	}
	
	@GET
	@Path("/3/{objinstance}/17")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeviceType(@PathParam("objinstance") String objinstance) throws Exception {

		String read6 = DeviceMongoService.getDeviceType(objinstance);		
		return Response.status(200).entity(read6).build();	
	}
	
	@GET
	@Path("/3/{objinstance}/11")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getErrorCode(@PathParam("objinstance") String objinstance) throws Exception {

		String read7 = DeviceMongoService.getErrorCode(objinstance);		
		return Response.status(200).entity(read7).build();	
	}
}
