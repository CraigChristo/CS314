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
	
    //private data members
    private User currentUser;
    
    //public data members
    public MusicManager mMngr;
    public UserManager uMngr;

	public static void main(String args[])
    {
    	UI bart = new UI();
    	
    	bart.uMngr.addUser(new User("Bob", "pringles"));
    	bart.uMngr.addUser(new User("Alice", "unicrons"));
    	bart.uMngr.addUser(new User("UltralordSupreme", "galactic_conquest"));
    	
    	System.out.println("Logging in as: -u Bart -p squids");
    	System.out.println(bart.doLogin("Bob", "squids"));
    	
    	System.out.println("\nLogged In? " + bart.loggedIn() + "\n");
    	
    	System.out.println("Logging in as: -u UltralordSupreme -p galactic_conquest");
    	System.out.println(bart.doLogin("UltralordSupreme", "galactic_conquest"));
    	
    	System.out.println("\nLogged In? " + bart.loggedIn() + "\n");
    	
    	bart.mMngr.addSong(bart.getUser(), new Song(songMeta));
    	bart.mMngr.addSong(bart.getUser(), new Song(songMeta2));
    	
    	System.out.println(bart.getUser().getName() + "'s Library \n----------------------------------------");
    	
    	for (Song s : bart.getUser().getLibrary())
    		System.out.println(s + "\n");
    	
    	System.out.println("Searching for Funk: \n" + bart.getUser().getLibrary().search("Funk"));
    	
    }
    
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
                    u.getLibrary().addsong(s);
                
                }

                //friends
                lowerBound = upperBound + 2;
                upperBound = line.indexOf(')', lowerBound);
                String friendsFull = line.substring(lowerBound, upperBound);
                String[] friends = friendsFull.split(",");
                for(int i = 0; i < friends.length; ++i)
                {
                    u.addFriend(friends[i]);
                }
                
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
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
