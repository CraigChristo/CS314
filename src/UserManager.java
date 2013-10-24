/*
 * @file: UserManager.java
 * @purpose: manages user relationships and actions
 */

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.sun.tools.javac.util.Pair;

class UserManager implements Iterable<User>
{
	//Singleton
	private static UserManager me;
	
    //private data members
    private Hashtable<String, User> users;
    private List<Pair<User, Song> > waitingList;

    //private methods
    protected UserManager()
    {
    	users = new Hashtable<String, User>();
    }
    
    public boolean areFriends(User a, User b) //TODO
    {
    	return false;
    }

    //public methods
    public static UserManager instance()
    {
    	if (me == null)
    		me = new UserManager();
    	return me;
    }
    
    public void addUser(User u) //TODO
    {

    }
    
    public void sendInvite(User dest)
    {

    }

    public void acceptInvite(User source)
    {

    }

    public void setUserPermission(PermType p)
    {

    }

    public void getUserPermission()
    {

    }

    public void sendBorrow(Song song, User dest)
    {

    }

    public void accceptBorrow(User source)
    {

    }

    public void setBorrowLimit(User friend, int limit)
    {

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
