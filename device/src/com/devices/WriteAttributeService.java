package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/{endpoint}/writeattribute")
public class WriteAttributeService {

	@PUT
	@Path("/0/{objinstance}/{resourceid}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response writeAttributeObject0(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance, @PathParam("resourceid") String resourceid, String newattribute) throws Exception {
		
		if(resourceid.equalsIgnoreCase("6")){
			DeviceMongoService.writeAttributeSMSSecurityMode(endpoint, newattribute);
		}
		else if(resourceid.equalsIgnoreCase("11")){
			DeviceMongoService.writeAttributeClientHoldOffTime(endpoint, newattribute);
		}		
		return Response.status(200).entity("The Write Attributre Operation is successfully done.. \n Run the discover operation to see that the new attribute is added to the specific Object or not.. \n").build();
	}
	
	@PUT
	@Path("/1/{objinstance}/{resourceid}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response writeAttributeObject1(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance, @PathParam("resourceid") String resourceid, String newattribute) throws Exception {
		
		if(resourceid.equalsIgnoreCase("4")){
			DeviceMongoService.writeAttributeDisable(objinstance, newattribute);
		}
		else if(resourceid.equalsIgnoreCase("5")){
			DeviceMongoService.writeAttributeDisableTimeout(objinstance, newattribute);
		}		
		return Response.status(200).entity("The Write Attributre Operation is successfully done.. \n Run the discover operation to see that the new attribute is added to the specific Object or not.. \n").build();
	}
	
}
