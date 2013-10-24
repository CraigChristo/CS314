/*
 * @file: UI.java
 * @purpose: User interface for PA2
 */

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
