package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/create")
public class CreateOperationService {

	@POST
	@Path("/1/{objinstance}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response createNewObject(@PathParam("objinstance") String objinstance, String newvalue) throws Exception {	

		DeviceMongoService.createNewServerObject(newvalue);		// newvalue already contains the objInstance
		
		String newinfo = DeviceMongoService.setNewValuesNewObjectInstance(objinstance);		// return new values
				
		return Response.status(200).entity(newinfo).build();	
	}
	
}
