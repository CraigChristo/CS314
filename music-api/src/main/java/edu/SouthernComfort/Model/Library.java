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
import java.util.PriorityQueue;
import java.util.Queue;




public class Library implements Iterable<Song>
{
	//private data members
	private List<Song> owned;
	private Map<Song, Pair<Integer, User>> borrowed;
	private List<Song> loaned;
	
	private Map<String, Library> playlists;
	private User owner;
	
    private Dictionary<Song, Queue<User>> waitingList;
	
	private Dictionary<String, Dictionary<String, Pair<borrowSetting, Integer>>> friendBorrowLimit;
	private Dictionary<String, Dictionary<String, Integer>> friendPlayLimit;
	private Song listeningTo;
	
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
		this.loaned = new ArrayList<Song>();
		this.borrowed = new Hashtable<Song, Pair<Integer, User>>();
		this.playlists = new Hashtable<String, Library>();
		this.owner = (User) owner;
		
    	this.waitingList = new Hashtable<Song, Queue<User>>();
		
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
		return getSongBorrowLimit("default","default");
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
	
	public Pair<borrowSetting, Integer> getSongBorrowLimit(User user, Song song)
	{
		Pair<borrowSetting, Integer> limit = getSongBorrowLimit(user.getName(), song.getName());
		
		//If not set, get the default for that friend
		if (limit == null) {
			limit = getSongBorrowLimit(user.getName(), "default");
			
			//If that friend doesn't have a default, get the default for the song
			if (limit == null) {
				limit = getSongBorrowLimit("default", song.getName());
				
				//If the song doesn't have a default, get the default for the library
				if (limit == null) {
					limit = getDefaultBorrowLimit();
				}
			}
		}
		
		return limit;
	}
	
	public Pair<borrowSetting, Integer> getSongBorrowLimit(String userName, String songName)
	{
		if (getBorrowMap(userName).get(songName) == null)
			createSongLimit(userName, songName);
		
		return getBorrowMap(userName).get(songName);
	}
	
	private void createSongLimit(String userName, String songName) {
		Pair<borrowSetting, Integer> defaults = getDefaultBorrowLimit();
		getBorrowMap(userName).put(songName, new Pair<borrowSetting, Integer>(defaults.fst, defaults.snd));
	}
	
	public int getSongPlayLimit(User user, Song song) {
		return getSongPlayLimit(user.getName(), song.getName());
	}
	
	public int getSongPlayLimit(String userName, String songName) {
		Dictionary<String, Integer> dict = this.friendPlayLimit.get(userName);
		
		if (dict == null) {
			dict = this.friendPlayLimit.get("default");
		}
		
		if (dict.get(songName) != null) return dict.get(songName);
		else 
			if (dict.get("default") != null) return dict.get("default");
			else return this.friendPlayLimit.get("default").get("default");
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
	
	public void addBorrowedSong(Song song, int limit, User from)
	{
		this.borrowed.put(song, new Pair<Integer, User>(limit, from);
	}
	
	public void removeBorrowedSong(Song song)
	{
		this.borrowed.remove(song);
	}
	
	public void sendBorrow(User destUser, Song song)
	{
		if (isLoaned(song)) {
			if (this.waitingList.get(song) == null)
				this.waitingList.put(song, new LinkedList<User>());
			this.waitingList.get(song).add(destUser);
		}
		
		else {
			Pair<borrowSetting, Integer> curr = getSongBorrowLimit(destUser, song);
			
			this.setBorrowLimit(destUser, song, curr.snd--, curr.fst);
			this.loaned.add(song);
			
			int limit = getSongPlayLimit(destUser, song);
			
			destUser.getLibrary().addBorrowedSong(song, limit, this.owner);
		}
	}
	
	public boolean returnBorrow(User borrower, Song song)
	{
		Queue<User> q = this.waitingList.get(song);
		
		borrower.getLibrary().removeBorrowedSong(song);
		
		if (q != null) {
			User next = q.remove();
			
			int limit = getSongPlayLimit(next, song);
			
			next.getLibrary().addBorrowedSong(song, limit, this.owner);
			
			if (q.isEmpty())
				this.waitingList.remove(song);
		}  else {
			this.loaned.remove(song);
			return true;
		}
		
		return false;
	}
	
	//create a playlist for the user based on a selection of songs
	public void createPlaylist(String name, List<Song> songs)
	{
		this.playlists.put(name, new Library(songs));
	}
	
	public void play(Song song)
	{
		if (this.listeningTo != null)
			this.stop();
		
		if (isBorrowed(song)) {
			int limit = this.borrowed.get(song).fst;
			User from = this.borrowed.get(song).snd;
			
			this.borrowed.put(song, new Pair<Integer, User>(--limit, from));
		}	
		
		this.listeningTo = song;
	}
	
	public void stop()
	{
		if (this.borrowed.get(listeningTo).fst == 0) 
		{
			User from = this.borrowed.get(listeningTo).snd;
			
			from.getLibrary().returnBorrow(this.owner, this.listeningTo);
		}
		
		this.listeningTo = null;
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
		result.addAll(this.borrowed.keySet());
		
		result.removeAll(this.loaned);
		
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
	
	public List<Song> borrowed() {
		return new LinkedList<Song>(borrowed.keySet());
	}
	
	public List<Song> loaned() {
		return loaned;
	}
	
	//check if a song can be borrowed
	public boolean checkIfBorrowable(User friend, Song song)
	{
		Pair<borrowSetting, Integer> limit = getSongBorrowLimit(friend, song);
		
		if (limit.fst != borrowSetting.NO && limit.snd > 0)
			return true;
		else
			return false;
	}
	
	public boolean isAvailableToPlay(Song song)
	{
		if (this.contains(song) && !this.loaned.contains(song))
		{
			return true;
		} else return false;
	}
	
	public boolean isOwned(Song song)
	{
		return (this.owned.contains(song));
	}
	
	public boolean isBorrowed(Song song)
	{
		return (this.borrowed.get(song) == null);
	}
	
	public boolean isLoaned(Song song)
	{
		return this.loaned.contains(song);
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
        NO, LIMIT, APPROVE
	}
}