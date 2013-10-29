/*
 * @file: UserManager.java
 * @purpose: manages user relationships and actions
 */

import java.util.List;
public UserManager
{
    //private data members
    private List<User> users;
    private List<Pair<User, Song> > waitingList;

    //private methods
    private boolean areFriends(User a, User b) //TODO
    {

    }

    //public methods
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

}

public enum PermType
{
    all, friends, none
}
