/*
 * @file: User.java
 * @purpose: consists of User properties and actions
 */
package edu.SouthernComfort.Model;

import java.util.LinkedList;
import java.util.List;

import javax.json.*;


public class User
{
    //private data members
	private String username;
	private String password;
	private Library mylibrary;
	private List<User> invites;
	private List<User> myfriends;
	private PermType libpermview;
	
	//public methods
	
	public User(String name, String password) {
		this.username = name;
		this.password = password;
		
		this.invites = new LinkedList<User>();
		this.myfriends = new LinkedList<User>();
		
		this.mylibrary = new Library(this);
	}

	//accept an invite from the user(string) if invite exist
	public void acceptInvite(User friend)
	{
		if (this.invites.contains(friend)) {
			if (!this.isFriendsWith(friend)) {
				this.myfriends.add(friend);
				friend.getFriends().add(this);
			}
			
			this.invites.remove(friend);
		}
	}
	
	//add invite to list of invites
	public void addInvite(User a) 
	{
		if (!this.invites.contains(a))
			this.invites.add(a);
	}
	
	public void sendInvite(User b)
	{
		b.addInvite(this);
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
	
	public boolean checkPassword(String pwd) {
		return checkPassword(false, pwd);
	}
	
	public boolean checkPassword(Boolean hashed, String hash) {
		if (hashed) {
//			if (hash.equals(hash(this.password)))
				return true;
		}
		else 
			if (hash.equals(this.password))
				return true;
		return false;
	}
	
	public List<User> getFriends() {
		return this.myfriends;
	}
	
	public Library getLibrary() {
		return this.mylibrary;
	}
	
	public String getUsername(){
		return this.username;
	}

    public void addFriend(User friend)
    {
        if(!isFriendsWith(friend))
        {
            myfriends.add(friend);
        }
    }
	
	public boolean isFriendsWith(User other)
	{
		return (this.myfriends.contains(other));
	}
	
	public boolean isEqual(Object o) {
		if (User.class.isInstance(o)) {
			if(this.username.equalsIgnoreCase(((User) o).getName())) 
				return true;
		}
		return false;
	}

	public void sendBarrow(Song song, User dest) {
		// TODO Auto-generated method stub
		
	}
	
	public JsonObject toJson() {
		JsonArrayBuilder friendsList = Json.createArrayBuilder();
		
		for (User friend : this.myfriends)
			friendsList.add(friend.getName());
		
		JsonArrayBuilder inviteList = Json.createArrayBuilder();
		
		for (User user : this.invites)
			inviteList.add(user.getName());
		
		return Json.createObjectBuilder()
    			.add("username", this.username)
    			.add("password", this.password)
    			.add("social", Json.createObjectBuilder()
    					.add("friends", friendsList.build())
						.add("invites", inviteList.build())
					)
				.build();
    }
}
