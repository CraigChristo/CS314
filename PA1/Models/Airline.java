package PA1.Models;
/* ASSIGNMENT 1
* File: Airline.java
* Date: 08/28/2012
*/

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;
import java.util.LinkedList;
import PA1.Manager.ManagementException;
import PA1.Manager.SystemManager;

public class Airline {

	//Define maximum and minimum number of characters in an id.
	public final static int MAXIDCHARS = 5;
	public final static int MINIDCHARS = 1;

	private String id;
	private Hashtable<String, Flight> myFlights;
	
	/*Constructs new Airline objects.
	*There are restrictions on airline names, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Airline(String idArg) throws ManagementException
	{
		//Check to make sure the string is not null
		if(idArg == null)
		{
			throw new ManagementException("You have attempted to create an airline, but the name is null.");
		}
		
		//Convert all names to upper case
		idArg = idArg.toUpperCase();

		//Do the name check
		boolean nameIsOK = checkId(idArg);
		
		if(!nameIsOK)
		{
			throw new ManagementException("Airline name must be between " + MINIDCHARS + " and " + MAXIDCHARS + " (inclusive) alphabetic characters, name is length " 
		+ idArg.length() + " for " + idArg + ".");
		}

		//If all of the above went OK, assign values
		id = idArg;
		myFlights = new Hashtable<String, Flight>();
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
	
	//Assign a flight to this airline. An airline can only have one instance of a given flight ID.
	public void addFlight(Flight flightArg) throws ManagementException
	{
		//Check if the flight object is null
		if(flightArg == null)
		{
			throw new ManagementException("You tried to assign a null flight to airline " + toString());
		}
		
		//We just need to check if this airline already has a flight with that number.
		Flight existingFlight = myFlights.get(flightArg.getId());
		if(existingFlight != null)
		{
			//Then the flight must already exist if existingFlight is not null
			throw new ManagementException("You are attempting to add " + flightArg + ", but a flight with that ID is already registered with that airline. " +existingFlight.getId() + existingFlight.getDestination()	);
		}
		
		//If the above test passed, then we can accept the flight for this Airline.
		myFlights.put(flightArg.getId(), flightArg);
	}
	
	//Returns a flight object given a flightID string. Returns null if no such flight belongs to this airline.
	public Flight findFlight(String idArg) throws NullPointerException
	{
		//Check if the string is null
		if(idArg == null)
		{
			throw new NullPointerException("You are attempting to look up a flight belonging to airline " + toString() 
					+ ", but the flight ID is null.");
		}
		idArg = idArg.toUpperCase();
		return myFlights.get(idArg);
	}
	
	//Finds a linked list of flights with an open set on a flight from the origin airport to the destination airport
	public LinkedList<Flight> findAvailableFlights(Airport originAirport, Airport destinationAirport) throws ManagementException
	{
		//Check if the airports are null
		if(originAirport == null || destinationAirport == null)
		{
			throw new ManagementException("You are attempting to find flights from one airport to another, but either the destination or origin airport is null, or both. Origin: "
					+ originAirport + ", Destination: " + destinationAirport);
		}
			
		//Create an empty list of acceptable flights and convert the flight hash to linked list for easy iteration
		LinkedList<Flight> acceptableFlightList = new LinkedList<Flight>();
		LinkedList<Flight> allFlightList = SystemManager.hashtableToLinkedList(myFlights);
			
		for(Flight currentFlight : allFlightList)
		{
			//If the current flight matches the given criteria, add it into the list of acceptable flights
			if(currentFlight.getOrigin() == originAirport && currentFlight.getDestination() == destinationAirport && currentFlight.hasAvailableSeat())
			{
				acceptableFlightList.add(currentFlight);
			}
		}
		
		return acceptableFlightList;
	}
	
	public String toString()
	{
		return id;
	}
	
	public Hashtable<String, Flight> getFlights()
	{
		return myFlights;
	}
	
	public String getId() 
	{
		return id;
	}
}