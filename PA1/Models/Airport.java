package PA1.Models;
/* ASSIGNMENT 1
* File: Airport.java
* Date: 08/28/2012
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import PA1.Manager.ManagementException;


public class Airport {

	//Define maximum and minimum number of characters in an id.
	public final static int MAXIDCHARS = 3;
	public final static int MINIDCHARS = 3;
	
	private String id;
	
	/*Constructs new Airport objects.
	*There are restrictions on airport names, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Airport(String idArg) throws ManagementException
	{
		//Check to make sure the string is not null
		if(idArg == null)
		{
			throw new ManagementException("You have attempted to create an airport, but the name is null.");
		}
		
		//Convert all names to upper case
		idArg = idArg.toUpperCase();
		
		//Check the given id
		boolean nameIsOK = checkId(idArg);
		
		if(!nameIsOK)
		{
			throw new ManagementException("Airport name must be between " + MINIDCHARS + " and " + MAXIDCHARS + " (inclusive) alphabetic characters, name is length " 
					+ idArg.length() + " for " + idArg + ".");
		}
		
		//If all of the above went ok, then assign the name and the object is ready
		id = idArg;
	}
	
	//Performs a check on the ID.
	private boolean checkId(String nameArg) throws ManagementException
	{
		boolean nameIsOK = true;
		
		//Check that length of name is in bounds
		if(nameArg.length() < MINIDCHARS || nameArg.length() > MAXIDCHARS )
		{
			nameIsOK = false;
		}
		
		//Use a regex to ensure that only alphabetic characters are in the name
		Pattern p = Pattern.compile("^A-Z");
		Matcher m = p.matcher(nameArg);
		boolean containsNonAlphabetic = m.matches();
		
		if(containsNonAlphabetic)
		{
			nameIsOK = false;
		}
		
		return nameIsOK;
	}
	
	public String toString()
	{
		return id;
	}
	
	public String getId() 
	{
		return id;
	}	
}