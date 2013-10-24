/*
 * @file: User.java
 * @purpose: consists of User properties and actions
 */

import java.util.List;


class User
{
    //private data members
	private String username;
	private String password;
	private Library mylibrary;
	private List<String> invites;
	private List<User> myfriends;
	private PermType libpermview;
	
	//public methods

	//add song to user library
	public void addSong(Song a) //TODO
	{
	
	}
	//remove a song from the user library
	public void removeSong(Song b) //TODO
	{
	
	}
	//create a playlist for the user based on a selection of songs
	public void createPlaylist(List<Song> a) //TODO
	{
	
	}
	//display the user's library based on the string value(artist, song ,album)
	public void diplayLib(String a) //TODO
	{
	
	}
	//accept an invite from the user(string) if invite exist
	public void acceptInvite(String a) //TODO
	{
	
	}
	//check if a song can be borrowed
	public boolean checkIfBorrowable(Song a) //TODO
	{
		//Move to library or manager for cohesion
		//e.g. if (Library.search(a) != null) Library.search(a).isBorrowable()
		return false;
	}
	
	//add invite to list of invites
	public void addInvite(User a) //TODO
	{

	}
	
	public void setPerm(PermType p) {
		this.libpermview = p;
	}
	
	public PermType getPerm() {
		return this.libpermview;
	}
	
	public void setPublic() {
		setPerm(PermType.ALL);
	}
	
	public void setFriendsOnly() {
		setPerm(PermType.FRIENDS);
	}
	
	public void setPrivate() {
		setPerm(PermType.NONE);
	}
	
	public String getName() {
		return username;
	}
	
	public List<User> getFriends() {
		return myfriends;
	}
	
	public Library getLibrary() {
		return this.mylibrary;
	}
	
	public String getUsername(){
		return this.username;
	}
	public boolean isEqual(User a) {
		if(username.equalsIgnoreCase(a.getUsername())) return true;
		else return false;
	}
}
