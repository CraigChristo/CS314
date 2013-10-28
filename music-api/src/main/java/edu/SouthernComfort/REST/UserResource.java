package edu.SouthernComfort.REST;

import java.util.Dictionary;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import javax.json.*;

import edu.SouthernComfort.Manager.UserManager;
import edu.SouthernComfort.Model.User;

/**
 * User resource (exposed at "users" path)
 */
@Path("/users")
public class UserResource {
	
	private static UserManager users = UserManager.instance();

    
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserList() {
		String result = "";
		for (User u :users.toList())
			result += u.toString();
		return result;
	}
	
    @GET @Path("/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUser(@PathParam("username") String username) {
        User result = users.findUser(username);
    	if ( result != null )
    		return result.toString();
    	else
    		return "Not found";
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject addUser(JsonObject obj) {
    	try {
    		users.addUser(
    				new User(
    						obj.getString("username"),
    						obj.getString("password")
					));
    		
    		User result = users.findUser(obj.getString("username"));
    		
    		if ( result != null )
    			return result.toJson();
    		else
    			return QuickJson.build("Error creating user");
    	} catch (NullPointerException e) {
    		return QuickJson.build("Invalid arguments");
    	}
    }
}
