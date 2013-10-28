package edu.SouthernComfort.REST;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.json.*;

import edu.SouthernComfort.Manager.*;
import edu.SouthernComfort.Model.Metadata;
import edu.SouthernComfort.Model.Song;
import edu.SouthernComfort.Model.User;
import edu.SouthernComfort.Model.Library;

/**
 * Library resource (exposed at "libary" path)
 */
@Path("/")
public class LibraryResource {
	
	private static UserManager users = UserManager.instance();
	private static MusicManager music = MusicManager.instance();

    
	@GET @Path("/library")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonValue getLibrary(@HeaderParam("user") String username, @QueryParam("sortby") @DefaultValue("name") String sortBy) {
		if (username == null)
			return QuickJson.build("A valid user is required");
		
		User user = users.findUser(username);
		
		return QuickJson.build(user.getLibrary().toSortedList(sortBy));
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
