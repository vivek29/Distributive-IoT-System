package com.servers;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/server1")
public class BootstrapServer {
	
	@GET
	@Path("/bootstrap/{endpoint}")					// 1 for bootstrap
	@Produces(MediaType.APPLICATION_JSON)
	public Response produceJSON( @PathParam("endpoint") String endpoint ) throws Exception {
		
		String detail1;
		String detail2;
		int x = MongoService.checkBootstrapping(endpoint);		
		if(x==0){								// new bootstrap request
			detail1 = MongoService.getSecurityObject(endpoint);		// get security object	
			String objid = MongoService.getobjectinstanceid();	
			detail2 = MongoService.getServerObject(endpoint, objid);
		}
		
		else{									// update the bootstrap request with a new object instance id
			detail1 = MongoService.getUpdatedSecurityObject(endpoint);
			String objid = MongoService.getobjectinstanceid();
			detail2 = MongoService.getServerObject(endpoint, objid);
		}
		String details = detail1+" & \n"+detail2+"\n";
		return Response.status(200).entity(details).build();	
	}
}