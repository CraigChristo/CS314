package edu.SouthernComfort.REST;

import java.util.List;
import java.util.Map;

import edu.SouthernComfort.Manager.*;
import edu.SouthernComfort.Model.*;

/*
 * @file: UI.java
 * @purpose: User interface for PA2
 */

import java.io.BufferedReader;
import java.io.FileReader;

class UI
{
	private static String[][] songMeta = {
		{"name", "Funkotonic"},
		{"artist", "Funktimus Prime"}
	};
	
	private static Metadata songMeta2 = new Metadata( new String[][]{
		{"name", "Yellow Gatorade"},
		{"genre", "Gypsy Metalcore"}
	});
	
	private static String[][] songMeta3 = {
		{"name", "Cheese Strings"},
		{"artist", "Bon Anchovi"},
		{"album", "Pepper my roni"}
	};
	
	private static String[][] songMeta4 = {
		{"name", "Stairway to Heaven"},
		{"genre", "Bible"}
	};
	
	private static String[][] songMeta5 = {
		{"name", "Stairs go up"},
		{"album", "And Sometimes down"},
		{"artist", "Also has stairs"}
	};
	
    //private data members
    private User currentUser;
    
    //public data members
    public MusicManager mMngr;
    public UserManager uMngr;
    
    public UI()
    {
    	mMngr = MusicManager.instance();
    	uMngr = UserManager.instance();
    	
    	this.parseFile("config.cs314");
    }
    
    //parses input file to create users on songs
    private void parseFile(String file) //TODO
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("testfile"));
            String line = null;
            while((line = reader.readLine()) != null)
            {
                //remove white space
                line = line.replaceAll("\t", "");
                line = line.replaceAll(" ", "");
                
                if(line.isEmpty()) //break if line is empty
                    break;

                //username
                int lowerBound = 0;
                int upperBound = line.indexOf(',');
                String user = line.substring(lowerBound, upperBound);
                //System.out.println(user);

                //password
                lowerBound = ++upperBound;
                upperBound = line.indexOf('[', lowerBound);
                String pw = line.substring(lowerBound, upperBound);
                //System.out.println(pw);

                //create user
                User u = new User(user, pw);

                //song metadata
                lowerBound = ++upperBound;
                upperBound = line.indexOf(']', lowerBound);
                String songsFull = line.substring(lowerBound, upperBound);
                String[] songs = songsFull.split("\\|");
                for(int i = 0; i < songs.length; ++i)
                {
                    String[] data = songs[i].split(",");
                    Metadata m = new Metadata();

                    //create metadata
                    if(!data[0].isEmpty() || !data[1].isEmpty())
                    {
                        m.put("name", data[0]);
                        m.put("artist", data[1]);
                        m.put("album", data[2]);
                        m.put("year", data[3]);
                        m.put("composer", data[4]);
                        m.put("genre", data[5]);
                    }
                    
                    //create song
                    Song s = new Song(m);
                    mMngr.getGlobalLibrary().addSong(s);
                    u.getLibrary().addSong(s);
                
                }

                //friends
                lowerBound = upperBound + 2;
                upperBound = line.indexOf(')', lowerBound);
                String friendsFull = line.substring(lowerBound, upperBound);
                String[] friends = friendsFull.split(",");
                for(int i = 0; i < friends.length; ++i)
                {
                    User friend = uMngr.findUser(friends[i]);
                    if(friend != null)
                        u.addFriend(friend);
                }
                
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void printLibrary() {
    	printLibrary(this.currentUser);
    }
    
    public void printLibrary(User u) {
    	System.out.println(u.getName() + "'s Library \n----------------------------------------");
    	
    	for (Song s : u.getLibrary())
    		System.out.println(s);
    	
    	System.out.println();
    }
    
    public User getUser() {
    	return this.currentUser;
    }
    
    public boolean loggedIn() {
    	if (currentUser != null) return true;
    	else return false;
    }
    
    public boolean doLogin(String username, String password) 
    {
    	User user = uMngr.findUser(username);
    	
    	if (user.checkPassword(password)) {
    		this.currentUser = user;
    		return true;
    	}
    	
    	return false;
    }
}
