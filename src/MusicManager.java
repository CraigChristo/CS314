/*
 * @file: MusicManager.java
 * @purpose: manages music relationships and actions
 */

import java.util.List;


public MusicManager 
{

        //private data members
        private List <User> users;
        private Library globalLibrary;
        private List <Library> userlibraries;

        //private methods
        private boolean areFriends(User a, User b) //TODO
        {

        }
	//public methods

	//simply add user to users
	public void addUser(User a) //TODO
	{
	
	}
	//print out all songs in some order from all friends of current user, implementation depends on UI
	public void browseFriendsMusic() //TODO
	{
	
	}
	//search for songs based on a friend, returns a list of songs that the friend owns
	public List<Song> searchFriendsMusic(User a) //TODO
	{
	
	}
	//search for friends based on a song, returns a list of friends
	public List<User> searchFriendsMusic(Song a) //TODO
	{
	
	}
	//search for users friend or not based on a song, returns a list of Users
	public List<User> searchMusic(Song a) //TODO
	{
	
	}
	//add a song to a user's library
	public void addSong(Song a, User b) //TODO
	{
	
	}
	//remove a song from a user's library
	public void removeSong(Song a, User b) //TODO
	{
	
	}
	//creates a playlist for a user based on a selection of songs
	public void createPlaylist(List<Song> a, User b) //TODO
	{

	}
	//display the library based on artist, song, or album (string) and the user, depends on UI to impliment
	public void displayLib(String a, User b) //TODO
	{

	}
	//take back a barrowed song from the users, Song argument is from the user's library
	public void takeBack(Song a) //TODO
	{

	}
	//add a user library to the list
	public void addToLib(Library a) //TODO
	{

	}
}

