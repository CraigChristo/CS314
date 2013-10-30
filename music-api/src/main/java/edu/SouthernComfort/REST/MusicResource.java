package edu.SouthernComfort.REST;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.json.*;

import edu.SouthernComfort.Manager.*;
import edu.SouthernComfort.Model.Metadata;
import edu.SouthernComfort.Model.Song;
import edu.SouthernComfort.Model.User;

/**
 * User resource (exposed at "users" path)
 */
@Path("/")
public class MusicResource {
	
	private static UserManager users = UserManager.instance();
	private static MusicManager music = MusicManager.instance();

    
	@GET @Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray getUserList() {
		List<JsonObject> result = new LinkedList<JsonObject>();
		for (User u :users)
			result.add(u.toJson());
		
		return QuickJson.buildFromJson(result);
	}
	
    @GET @Path("/users/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getUser(@PathParam("username") String username) {
        User result = users.findUser(username);
    	if ( result != null )
    		return result.toJson();
    	else
    		return QuickJson.build("Not found");
    }
    
    @PUT @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject addUser(@QueryParam("username") String username, @QueryParam("password") String password)) {
    	try {
    		users.addUser(
    				new User(
    						username,
    						password
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

    
	@GET @Path("/library")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonValue getLibrary(@HeaderParam("user") String username, @QueryParam("sortby") @DefaultValue("name") String sortBy) {
		if (username == null)
			return QuickJson.build("A valid user is required");
		
		User user = users.findUser(username);
		
		return QuickJson.build(user.getLibrary().toSortedList(sortBy));
	}
	
    @GET @Path("/users/{username}/library")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonValue getLibrary(@HeaderParam("user") String fromUser, @PathParam("username") String toUser, @QueryParam("sortby") @DefaultValue("name") String sortBy) {
        if (fromUser == null || toUser == null)
        	return QuickJson.build("Invalid arguments");
    	
    	if (fromUser.equals(toUser)) {
        	User user = users.findUser(fromUser);
        	
        	return QuickJson.build(user.getLibrary().toSortedList(sortBy));
        } else {
        	User fU = users.findUser(fromUser);
        	User tU = users.findUser(toUser);
        	
        	return QuickJson.build(
        				music.searchFriendsMusic(fU, fU, sortBy)
        			);
        }
        	
    }
    
    @PUT @Path("/library")
    @Consumes(MediaType.APPLICATION_JSON) 
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject addSong(@HeaderParam("user") String username, JsonObject obj) {
    	try {    		
    		User user = users.findUser(username);
    		
    		Map<String, String> parsedObj = new Hashtable<String,String>();
    		
    		
    		for (Map.Entry<String, JsonValue> e : obj.entrySet())
    			parsedObj.put(e.getKey(), e.getValue().toString());
    		
    		Song song = new Song(new Metadata(parsedObj));
    		
    		music.addSong(user, song);
    		
    		return QuickJson.build(song.toString());
    	} catch (NullPointerException e) {
    		return QuickJson.build("Invalid arguments");
    	}
    }
}
