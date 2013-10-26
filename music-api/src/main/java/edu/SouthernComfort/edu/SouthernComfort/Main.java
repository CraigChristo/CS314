package edu.SouthernComfort;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import edu.SouthernComfort.REST.Song;
import edu.SouthernComfort.REST.UI;
import edu.SouthernComfort.REST.User;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Main class.
 *
 */

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.SouthernComfort package
        final ResourceConfig rc = new ResourceConfig().packages("edu.SouthernComfort");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

//public static void main(String args[])
//{
//	UI bart = new UI();
//	
//	//Create users
//	bart.uMngr.addUser(new User("Bob", "pringles"));
//	bart.uMngr.addUser(new User("Alice", "unicrons"));
//	bart.uMngr.addUser(new User("UltralordSupreme", "galactic_conquest"));
//	
//	//Test login
//	System.out.println("Logging in as: -u Bart -p squids");
//	System.out.println(bart.doLogin("Bob", "squids"));
//	
//	System.out.println("\nLogged In? " + bart.loggedIn() + "\n");
//	
//	System.out.println("Logging in as: -u UltralordSupreme -p galactic_conquest");
//	System.out.println(bart.doLogin("UltralordSupreme", "galactic_conquest"));
//	
//	System.out.println("\nLogged In? " + bart.loggedIn() + "\n");
//	
//	//Other users
//	User Bob = bart.uMngr.findUser("Bob");
//	User Alice = bart.uMngr.findUser("Alice");
//	
//	//Add some songs to everyone's libraries
//	bart.mMngr.addSong(bart.getUser(), new Song(songMeta));
//	bart.mMngr.addSong(bart.getUser(), new Song(songMeta2));
//	bart.mMngr.addSong(bart.getUser(), new Song(songMeta5));
//	bart.mMngr.addSong(bart.uMngr.findUser("Bob"), new Song(songMeta3));
//	bart.mMngr.addSong(bart.uMngr.findUser("Bob"), new Song(songMeta4));
//	bart.mMngr.addSong(bart.uMngr.findUser("Bob"), new Song(songMeta5));
//	bart.mMngr.addSong(bart.uMngr.findUser("Alice"), new Song(songMeta5));
//	
//	//Add some friends
//	bart.getUser().sendInvite(Bob);
//	Bob.acceptInvite(bart.getUser());
//	
//	Alice.sendInvite(bart.getUser());
//	bart.getUser().acceptInvite(Alice);
//	
//	
//	//Print logged-in user library
//	bart.printLibrary();
//	
//	//Search current user library
//	System.out.println("\nSearching for Funk: \n");
//	for (Song song : bart.getUser().getLibrary().search("Funk"))
//		System.out.println(song);
//	
//	//Print friends libraries
//	System.out.println("\nFriend's Libraries \n-------------------------------------\n");
//	
//	for (User f : bart.getUser().getFriends()) {
//		bart.printLibrary(f);
//	}
//	
//	//Search current user's friends libraries
//	System.out.println("\nSearching for Stair in friends: \n");
//	
//	for (Map.Entry<Song, List<User>> entry : bart.mMngr.searchFriendsMusic(bart.getUser(), "stair")) {
//		System.out.println(entry.getKey());
//		System.out.println("Owned by: ");
//		for (User u : entry.getValue())
//			System.out.println(u.getName());
//	}
//}