package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/{endpoint}/discover")
public class DiscoverOperationService {

	@GET
	@Path("/{objectid}/{objinstance}")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getObject(@PathParam("endpoint") String endpoint, @PathParam("objectid") String objectid, @PathParam("objinstance") String objinstance) throws Exception {

		String discover1;
		if(objectid.equalsIgnoreCase("0")){									// LWM2MSecurityObject
			discover1 = "The requested Object has following attributes: \n"+DeviceMongoService.discoverSecurityObject(endpoint);		
		}
		else if(objectid.equalsIgnoreCase("1")){								// LWM2MServerObject
			discover1 = "The requested Object has following attributes: \n"+DeviceMongoService.discoverServerObject(objinstance);
		}
		else if(objectid.equalsIgnoreCase("3")){								// DeviceObject	
			discover1 = "The requested Object has following attributes: \n"+DeviceMongoService.discoverDeviceObject(objinstance);
		}
		else{
			discover1 = "There's no object with this object id..\n";
		}		
		return Response.status(200).entity(discover1).build();	
	}
	
	@GET
	@Path("/{objectid}/{objinstance}/{resourceid}")					
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResourceAttribute(@PathParam("endpoint") String endpoint, @PathParam("objectid") String objectid, @PathParam("objinstance") String objinstance, @PathParam("resourceid") String resourceid) throws Exception {
		
		String discover2;
		if(objectid.equalsIgnoreCase("0")){									// LWM2MSecurityObject
			
			if(resourceid.equalsIgnoreCase("0")){
				discover2 = "The requested Resource attribute is: "+DeviceMongoService.discoverLWM2MServerURI(endpoint);
			}
			else if(resourceid.equalsIgnoreCase("2")){
				discover2 = "The requested Resource attribute is: "+DeviceMongoService.discoverSecurityMode(endpoint);
			}
			else{
				discover2 = "There's no Resource to discover with this object id(0).. \n";
			}
		}
		else if(objectid.equalsIgnoreCase("1")){								// LWM2MServerObject
			
			if(resourceid.equalsIgnoreCase("0")){
				discover2 = DeviceMongoService.discoverShortServerId(objinstance);
			}
			else if(resourceid.equalsIgnoreCase("7")){
				discover2 = DeviceMongoService.discoverBindingMode(objinstance);
			}
			else{
				discover2 = "There's no Resource to discover with this object id(1).. \n";
			}
		}
		else if(objectid.equalsIgnoreCase("3")){								// DeviceObject
			
			if(resourceid.equalsIgnoreCase("0")){
				discover2 = DeviceMongoService.discoverManufacturer(objinstance);
			}
			else if(resourceid.equalsIgnoreCase("11")){
				discover2 = DeviceMongoService.discoverErrorCode(objinstance);
			}
			else{
				discover2 = "There's no Resource to discover with this object id(3).. \n";
			}
		}
		
		else{
			discover2 = "There's no object with this object id..\n";
		}
		
		return Response.status(200).entity(discover2).build();	
	}
}
