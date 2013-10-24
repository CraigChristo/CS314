/*
 * @file: Library.java
 * @purpose: consists of Library properties and actions, including playlist
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


class Library implements Iterable<Song>
{
    //private data members
	private List<Song> songs;
	private List<Library> playlists;
	private List<Pair<User,Integer>> friendBarrowLimit;
	
	//public methods

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
	//create a playlist for the user based on a selection of songs
	public void createPlaylist(List<Song> a) //TODO
	{
	
	}
	//display the user's library based on the string value(artist, song ,album)
	public List<Song> toList()
	{
		List<Song> result = new LinkedList<Song>(songs);
		//result.add(borrowed);
		return result;
	}
	
	//Return songs owned by this user
	public List<Song> owned() {
		return songs;
	}
	
	public Iterator<Library> playlist_iter() {
		return playlists.iterator();
	}
	
	@Override
	public Iterator<Song> iterator() {
		return songs.iterator();
	}
	
}
