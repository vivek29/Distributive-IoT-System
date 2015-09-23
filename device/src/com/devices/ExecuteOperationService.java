package com.devices;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/{endpoint}/execute")
public class ExecuteOperationService {

	@POST
	@Path("/1/{objinstance}/8")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response executeRegistrationUpdateTrigger(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance, String newobjectinstance) throws Exception {

		// to re-register, client sends the register info again..		// no use of endpoint name here
		String reginfo = DeviceMongoService.executeNewRegisterInfo(objinstance, newobjectinstance);	
		return Response.status(200).entity(reginfo).build();	
	}
	
	@POST
	@Path("/3/{objinstance}/{resourceid}")
	@Consumes({MediaType.TEXT_PLAIN})
	@Produces(MediaType.TEXT_PLAIN)
	public Response executeDeviceResources(@PathParam("endpoint") String endpoint, @PathParam("objinstance") String objinstance, @PathParam("resourceid") String resourceid ) throws Exception {

		String execute2;
		if(resourceid.equalsIgnoreCase("4")){					// REBOOT	
			
			Thread.sleep(5000);						// load for 5 seconds
			execute2 = "Reboot successful.. \nThe registration still persists with the same object instance id..\n";
		}

		else if(resourceid.equalsIgnoreCase("5")){				// FACTORY RESET
	
			Thread.sleep(10000);								// load for 10 seconds	
			DeviceMongoService.executeFactoryResetDevice(endpoint, objinstance);		// remove from all collections
			execute2 = "Factory Reset successful.. The device now has the same configuration as at the initial deployment..  \nThe device needs to bootstrap again to register to the server..\n";
		}
		else{
			execute2 = "There's no Resource to execute with this object id(3).. \n";			
		}
		
		return Response.status(200).entity(execute2).build();	
	}
	
}