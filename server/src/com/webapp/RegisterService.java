package com.webapp;
import java.net.UnknownHostException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.Path;
import javax.servlet.http.HttpServletRequest;

import com.servers.MongoService;

@Path("/register")
public class RegisterService {
	
	@POST
	@Path("/device/{endpoint}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String produceJSON(@PathParam("endpoint") String endpoint,
            @FormParam("objinstance") String objinstance, @Context HttpServletRequest request) throws Exception {
		
		String reginfo = "{'_id' : '"+objinstance+"'" + ","+"'Device Endpoint Name' : '"+endpoint+"'" + ","+"'Object Id' : '1'"+","+"'Short Server Id' : '12345'"+","+"'Lifetime' : '86400'"+","+"'Notification Storing' : 'True'"+","+"'Binding' : 'U'"+","+"'Registration Update Trigger' : ''"+"}";
		
		String ip = request.getRemoteAddr();						// get ip
		int remotePort = 8081;										// set port
		String client_addr = ip+":"+remotePort ;
		
		MongoService.updateServerObject(reginfo);						// update the LWM2MServerObject	
		
		String result = "fail";
		
		String registerURI = MongoService.getRegisterURI(endpoint);		// check(endpoint) if bootstrapped device or not
		String validate2 = MongoService.checkServerObjectInstanceID(objinstance);		// check server objinstanceid corresponding to endpoint
		
		if(!registerURI.equalsIgnoreCase("false") && validate2.equalsIgnoreCase(endpoint)){
			String type = null;
			if(endpoint.contains("thermo")){
				type = "Thermostat";
			}
			else if(endpoint.contains("light")){
				type = "Light Switch";
			}
			
			String output = MongoService.CheckIfRegistered(endpoint);
			if(output.equalsIgnoreCase("Registered")){  				// if already registered, than update the registration
				MongoService.UpdateRegisterSuccess(endpoint, reginfo, type);					// register the device again
				MongoService.saveNewIpaddress(endpoint, client_addr);				// save new ip address for future use
				result = "success";
			}
			else{			// new registration
				MongoService.RegisterSuccess(endpoint, reginfo, type);		// for a new registration
				MongoService.saveIpaddress(endpoint, client_addr);			// save ip address for future use
				result = "success";
			}
		}
							// set lifetime in MongoService
		return result;
	}
	
	@GET
	@Path("/data1")					
	@Produces(MediaType.APPLICATION_JSON)
	public String getRegisteredThermostatDevices() throws Exception {
		
		String data1 = '"'+MongoService.getRegisteredThermostatDevices()+'"';		
		return data1;
	}
	
	@GET
	@Path("/data2")					
	@Produces(MediaType.APPLICATION_JSON)
	public String getRegisteredLightSwitchDevices() throws Exception {
		
		String data2 = '"'+MongoService.getRegisteredLightSwitchDevices()+'"';
		return data2;
	}
	
	@DELETE
	@Path("device/{endpoint}/{objid}")
	@Consumes({MediaType.TEXT_PLAIN})	
	public void deregisterClient( @PathParam("endpoint") String endpoint ,  @PathParam("objid") String objid) throws UnknownHostException{
		
		// deregister device from all collection in serverdb		
		MongoService.DeregisterDevice(endpoint , objid);
	}
	
}