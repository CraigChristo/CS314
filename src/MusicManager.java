/*
 * @file: MusicManager.java
 * @purpose: manages music relationships and actions
 */

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

class MusicManager 
{
	//Singleton
	private static MusicManager me;

    //private data members
	private UserManager users;
//    private Dictionary<String, User> users;
    private Library globalLibrary;
    //I don't think we need this, but its in the diagram
    //private List<Library> userlibraries;

    //private methods
    
    protected MusicManager() {
    	users = UserManager.instance();
    	globalLibrary = new Library();
    }
    
	//public methods
    
    public static MusicManager instance()
    {
    	if (me == null)
    		me = new MusicManager();
    	return me;
    }
    
	//returns a dictionary of songs, each with a list of friends who own that song.
	public Dictionary<Song, List<User>> browseFriendsMusic(User user)
	{
		Hashtable<Song, List<User>> result = new Hashtable<Song, List<User>>();
		
		for (User friend : user.getFriends()) {
			for(Song s : friend.getLibrary()) {
				List<User> userList = result.get(s);
				if ( userList == null) {
					List<User> list = new LinkedList<User>();
					list.add(friend);
					result.put(s, list);
				} else userList.add(friend);
			}
		}
		
		return result;
	}
	
	//search for songs based on a friend, returns a list of songs that the friend owns
	public List<Song> searchFriendsMusic(User user, User friend)
	{
		if (users.areFriends(user, friend))
			return friend.getLibrary().owned();
		return null;
	}
	
	//search for friends based on a song, returns a list of friends
	public List<User> searchFriendsMusic(User user, Song song)
	{
		return searchForUsersBySong(user.getFriends(), song);
	}
	
	//search for users friend or not based on a song, returns a list of Users
	public List<User> searchMusic(Song song)
	{
		return searchForUsersBySong(users, song);
	}
	
	private List<User> searchForUsersBySong(Iterable<User> coll, Song song)
	{
		List<User> result = new LinkedList<User>();
		
		for (User u : coll) {
			if (u.getLibrary().toList().contains(song))
				result.add(u);
		}
		
		return result;
	}
	
	//add a song to a user's library
	public void addSong(User user, Song song)
	{
		user.getLibrary().addSong(song);
	}
	
	//remove a song from a user's library
	public void removeSong(User user, Song song)
	{
		user.getLibrary().removeSong(song);
	}
	
	//creates a playlist for a user based on a selection of songs
	public void createPlaylist(User user, List<Song> songs)
	{
		createPlaylist(user, "untitled", songs);
	}
	
	public void createPlaylist(User user, String plname, List<Song> songs)
	{
		user.getLibrary().createPlaylist(plname, songs);
	}
	
	//public void addToPlaylist(User user, String name, List<Song> songs)
	
	//get a list of songs from user library based on artist, song, or album (string)
	public List<Song> getList(User user, String query)
	{
		List<Song> result = new LinkedList<Song>();
		
		for (Song s : user.getLibrary()) {
			if (s.getName().equals(query) || s.getArtist().equals(query) || s.getAlbum().equals(query))
				result.add(s);
		}
		
		return result;
	}
	
	//take back a borrowed song from the users, Song argument is from the user's library
	public void takeBack(User user, Song song)
	{
		//TODO
		user.getLibrary().removeSong(song);
		song.unBorrow();
	}
	
	//add a user library to the global library
	public void addToLib(Library lib)
	{
		for(Song s : lib)
			globalLibrary.addSong(s);
	}
}

