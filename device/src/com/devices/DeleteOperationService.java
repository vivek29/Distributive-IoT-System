package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/{endpoint}/delete")
public class DeleteOperationService {

	@DELETE
	@Path("/1/{objinstance}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteObjectInstance(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance) throws Exception {

		System.out.println("Deleting the Object Instance within the Device... \n");
		Thread.sleep(10000);								// load for 10 seconds	
		DeviceMongoService.executeFactoryResetDevice(endpoint, objinstance);		// delete and factory reset does same
		return Response.status(200).build();
	}
}
