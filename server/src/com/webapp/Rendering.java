package com.webapp;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.sun.jersey.api.view.Viewable;

@Path("/") 
public class Rendering {
	
	@Produces(MediaType.TEXT_HTML) 
	@GET 
	public Viewable getHomePage() { 
	  return new Viewable("/index.html", null); 
	} 
} 	
