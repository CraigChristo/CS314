/*
 * @file: Library.java
 * @purpose: consists of Library properties and actions, including playlist
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


class Library implements Iterable<Song>
{
    //private data members
	private List<Song> songs;
	private Hashtable<String, Library> playlists;
	
	private Dictionary<String, Dictionary<String, Pair<Boolean, Integer>>> friendBorrowLimit;
	
	//public methods
	
	public Library()
	{
		this.songs = new ArrayList<Song>();
		this.playlists = new Hashtable<String, Library>();
		
		this.friendBorrowLimit = new Hashtable<String, Dictionary<String, Pair<Boolean, Integer>>>();
		
		//Set default borrow limit to 10 borrows
		setDefaultBorrowLimit(10, false);
	}
	
	public Library(List<Song> songs2) {
		this.songs = songs2;
		this.playlists = new Hashtable<String, Library>();
		
		this.friendBorrowLimit = new Hashtable<String, Dictionary<String, Pair<Boolean, Integer>>>();
		
		//Set default borrow limit to 10 borrows
		setDefaultBorrowLimit(10, false);
	}

	public void setDefaultBorrowLimit(int limit, boolean timed)
	{
		setBorrowLimit("default", "default", limit, timed);
	}
	
	public Pair<Boolean, Integer> getDefaultBorrowLimit()
	{
		return getSongLimit("default","default");
	}
	
	public void setBorrowLimit(User friend, Song song, int limit, Boolean timed)
	{
		setBorrowLimit(friend.getName(), song.getName(), limit, timed);
	}
	
	public void setBorrowLimit(String friendName, String songName, int limit, Boolean timed) 
	{
		if (getBorrowMap(friendName) == null)
				createBorrowMap(friendName);
		getBorrowMap(friendName).put(songName, new Pair<Boolean, Integer>(timed, limit));
	}
	
	public Dictionary<String, Pair<Boolean, Integer>>  getBorrowMap(User friend)
	{
		return getBorrowMap(friend.getName());
	}
	
	public Dictionary<String, Pair<Boolean, Integer>> getBorrowMap(String userName)
	{
		if (this.friendBorrowLimit.get(userName) == null)
			createBorrowMap(userName);
		
		return this.friendBorrowLimit.get(userName);		
	}
	
	private void createBorrowMap(String userName)
	{
		this.friendBorrowLimit.put(userName, new Hashtable<String, Pair<Boolean, Integer>>());
	}
	
	public Pair<Boolean, Integer> getSongLimit(User user, Song song)
	{
		return getSongLimit(user.getName(), song.getName());
	}
	
	public Pair<Boolean, Integer> getSongLimit(String userName, String songName)
	{
		if (getBorrowMap(userName).get(songName) == null)
			createSongLimit(userName, songName);
		
		return getBorrowMap(userName).get(songName);
	}
	
	private void createSongLimit(String userName, String songName) {
		Pair<Boolean, Integer> defaults = getDefaultBorrowLimit();
		getBorrowMap(userName).put(songName, new Pair<Boolean, Integer>(defaults.fst, defaults.snd));
	}

	//add song to user library
	public void addSong(Song a) //TODO
	{
		songs.add(a);
	}
	//remove a song from the user library
	public void removeSong(Song b)
	{
		
		for (int i = 0; i < songs.size(); i++){
			if(songs.get(i).isEqual(b)){
				songs.remove(i);
			}
		}
	}
	
	public boolean borrowSong(User user, Song song)
	{
		Pair<Boolean, Integer> curr = getSongLimit(user, song);
		
		//If song can be borrowed
		if (curr.snd > 0)
			return true;
		
		return false;
	}
	
	//create a playlist for the user based on a selection of songs
	public void createPlaylist(String name, List<Song> songs)
	{
		this.playlists.put(name, new Library(songs));
	}
	
	//display the user's library based on the string value(artist, song ,album)
	public List<Song> toList()
	{
		return toList("name");
	}	
	public List<Song> toList(String sortBy)
	{
		List<Song> result = new LinkedList<Song>(songs);
		Collections.sort(result,  new Song.SongComparator(sortBy));
		return result;
	}
	
	//Return songs owned by this user
	public List<Song> owned() {
		return songs;
	}
	
	//it.getKey() == name
	//it.getValue() == the playlist
	public Iterator<Map.Entry<String, Library>> playlist_iter() {
		return playlists.entrySet().iterator();
	}
	
	@Override
	public Iterator<Song> iterator() {
		return songs.iterator();
	}
	
}
