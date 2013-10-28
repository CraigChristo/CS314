/*
 * @file: Library.java
 * @purpose: consists of Library properties and actions, including playlist
 */
package edu.SouthernComfort.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




public class Library implements Iterable<Song>
{
    //private data members
	private List<Song> owned;
	private List<Song> borrowed;
	private List<Song> loaned;
	
	private Hashtable<String, Library> playlists;
	private User owner;
	
	private Dictionary<String, Dictionary<String, Pair<borrowSetting, Integer>>> friendBorrowLimit;
	
	//public methods
	
	public Library()
	{
		makeLibrary(null);
	}
	
	public Library(List<Song> songs2) {
		makeLibrary(null, songs2);
	}
	
	public Library(User user)
	{
		makeLibrary(user);
	}
	
	private void makeLibrary(Object owner) {
		makeLibrary(owner, new ArrayList<Song>());
	}
	
	private void makeLibrary(Object owner, List<Song> songs) {
		this.owned = songs;
		this.borrowed = new ArrayList<Song>();
		this.loaned = new ArrayList<Song>();
		this.playlists = new Hashtable<String, Library>();
		this.owner = (User) owner;
		
		this.friendBorrowLimit = new Hashtable<String, Dictionary<String, Pair<borrowSetting, Integer>>>();
		
		//Set default borrow limit to 3 borrows
		setDefaultBorrowLimit(3, borrowSetting.LIMIT);
	}
	
	public User getOwner() 
	{
		return this.owner;
	}

	public void setDefaultBorrowLimit(int limit, borrowSetting setting)
	{
		setBorrowLimit("default", "default", limit, setting);
	}
	
	public void setSongDefaultBorrowLimit(Song song, int limit, borrowSetting setting)
	{
		setBorrowLimit("default", song.getName(), limit, setting);
	}
	
	public void setFriendDefaultBorrowLimit(User friend, int limit, borrowSetting setting)
	{
		setBorrowLimit(friend.getName(), "default", limit, setting);
	}
	
	public Pair<borrowSetting, Integer> getDefaultBorrowLimit()
	{
		return getSongLimit("default","default");
	}
	
	public void setBorrowLimit(User friend, Song song, int limit, borrowSetting setting)
	{
		setBorrowLimit(friend.getName(), song.getName(), limit, setting);
	}
	
	public void setBorrowLimit(String friendName, String songName, int limit, borrowSetting setting) 
	{
		if (getBorrowMap(friendName) == null)
				createBorrowMap(friendName);
		getBorrowMap(friendName).put(songName, new Pair<borrowSetting, Integer>(setting, limit));
	}
	
	public Dictionary<String, Pair<borrowSetting, Integer>>  getBorrowMap(User friend)
	{
		return getBorrowMap(friend.getName());
	}
	
	public Dictionary<String, Pair<borrowSetting, Integer>> getBorrowMap(String userName)
	{
		if (this.friendBorrowLimit.get(userName) == null)
			createBorrowMap(userName);
		
		return this.friendBorrowLimit.get(userName);		
	}
	
	private void createBorrowMap(String userName)
	{
		this.friendBorrowLimit.put(userName, new Hashtable<String, Pair<borrowSetting, Integer>>());
	}
	
	public Pair<borrowSetting, Integer> getSongLimit(User user, Song song)
	{
		Pair<borrowSetting, Integer> limit = getSongLimit(user.getName(), song.getName());
		
		//If not set, get the default for that friend
		if (limit == null) {
			limit = getSongLimit(user.getName(), "default");
			
			//If that friend doesn't have a default, get the default for the song
			if (limit == null) {
				limit = getSongLimit("default", song.getName());
				
				//If the song doesn't have a default, get the default for the library
				if (limit == null) {
					limit = getDefaultBorrowLimit();
				}
			}
		}
		
		return limit;
	}
	
	public Pair<borrowSetting, Integer> getSongLimit(String userName, String songName)
	{
		if (getBorrowMap(userName).get(songName) == null)
			createSongLimit(userName, songName);
		
		return getBorrowMap(userName).get(songName);
	}
	
	private void createSongLimit(String userName, String songName) {
		Pair<borrowSetting, Integer> defaults = getDefaultBorrowLimit();
		getBorrowMap(userName).put(songName, new Pair<borrowSetting, Integer>(defaults.fst, defaults.snd));
	}

	//add song to user library
	public void addSong(Song a)
	{
		if (!this.contains(a))
			owned.add(a);
	}
	
	public void addSongs(Collection<Song> songs)
	{
		this.owned.addAll(songs);
	}
	
	//remove a song from the user library
	public void removeSong(Song b)
	{
		for (int i = 0; i < owned.size(); i++){
			if(owned.get(i).isEqual(b)){
				owned.remove(i);
			}
		}
	}
	
	public boolean borrowSong(User user, Song song)
	{
		Pair<borrowSetting, Integer> curr = getSongLimit(user, song);
		
		//If song can be borrowed
		if (curr.fst != borrowSetting.NO && curr.snd > 0) {
			this.loaned.add(song);
		}
		
		return false;
	}
	
	public boolean unborrowSong(Song song)
	{
		if (this.loaned.remove(song)) 
			return true;
		return false;
	}
	
	//create a playlist for the user based on a selection of songs
	public void createPlaylist(String name, List<Song> songs)
	{
		this.playlists.put(name, new Library(songs));
	}
	
	public boolean contains(Song song) {
		return toList().contains(song);
	}
	
	//display the user's library based on the string value(artist, song ,album)
	public List<Song> search(String query)
	{
		List<Song> result = new LinkedList<Song>();
		
		query = query.toLowerCase();
		
		for (Song s : toList()) {
			if (s.getName().toLowerCase().contains(query) || s.get("artist").toLowerCase().contains(query) || s.get("album").toLowerCase().contains(query))
				result.add(s);
		}
		
		return result;
	}	
	
	public List<Song> toList() {
		List<Song> result = new LinkedList<Song>();
		
		result.addAll(this.owned);
		result.addAll(borrowed);
		
		return result;
	}
	
	public List<Song> toSortedList(String sortBy)
	{
		List<Song> result = new LinkedList<Song>(owned);
		Collections.sort(result,  new Song.SongComparator(sortBy));
		return result;
	}
	
	//Return songs owned by this user
	public List<Song> owned() {
		return owned;
	}
	
	//check if a song can be borrowed
	public boolean checkIfBorrowable(User friend, Song song)
	{
		Pair<borrowSetting, Integer> limit = getSongLimit(friend, song);
		
		if (limit.fst != borrowSetting.NO && limit.snd > 0)
			return true;
		else
			return false;
	}
	
	//it.getKey() == name
	//it.getValue() == the playlist
	public Iterator<Map.Entry<String, Library>> playlist_iter() {
		return playlists.entrySet().iterator();
	}
	
	@Override
	public Iterator<Song> iterator() {
		return owned.iterator();
	}

	public Library getPlaylist(String name) {
		return this.playlists.get(name);
	}
	
	private enum borrowSetting {
		NO, TIMED, LIMIT, APPROVE_LIMIT, APPROVE_TIMED
	}
}