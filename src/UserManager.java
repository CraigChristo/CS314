/*
 * @file: UserManager.java
 * @purpose: manages user relationships and actions
 */

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import com.sun.tools.javac.util.Pair;

class UserManager implements Iterable<User>
{
	//Singleton
	private static UserManager me;
	
    //private data members
    private Hashtable<String, User> users;
    private PriorityQueue<Pair<User, Song>> waitingList;

    //private methods
    protected UserManager()
    {
    	users = new Hashtable<String, User>();
    	waitingList = new PriorityQueue<Pair<User,Song>>();
    }
    
    public boolean areFriends(User a, User b)
    {
    	return a.getFriends().contains(b);
    }

    //public methods
    public static UserManager instance()
    {
    	if (me == null)
    		me = new UserManager();
    	return me;
    }
    
    public void addUser(User u)
    {
    	users.put(u.getName(), u);
    }
    
    public void sendInvite(User user, User dest)
    {
    	//TODO
    	//Move to User for cohesion
    }

    public void acceptInvite(User source)
    {
    	//Move to User for cohesion
    }

    public void setUserPermission(User user, PermType p)
    {
    	user.setPerm(p);
    }

    public void getUserPermission(User user)
    {
    	user.getPerm();
    }

    public void sendBorrow(Song song, User dest)
    {
    	//TODO
    }

    public void accceptBorrow(User source)
    {
    	//TODO
    }

    public void setBorrowLimit(User friend, int limit)
    {
    	//TODO
    	//This should probably be in User
    }

    public void checkBorrowLimit(User friend)
    {

    }

    public void SetBorrowLength(User friend, Song song, double numListens)
    {

    }

    public int getBorrowLength(User friend, Song, song)
    {

    }

    public void setBorrowability(User friend, boolean borrowability)
    {

    }

    public void setNoBorrability(Song song)
    {

    }

    public void addToWaitingList(Pair<User, Song> item) //TODO come up with a better variable name
    {

    }

	@Override
	public Iterator<User> iterator() {
		return users.values().iterator();
	}

}
