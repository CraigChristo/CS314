package PA1.Model;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import PA1.Manager.ManagementException;
import PA1.Manager.SystemManager;

public class Line {
	//Define maximum and minimum number of characters in an id.
		public final static int MAXIDCHARS = 5;
		public final static int MINIDCHARS = 1;

		private String id;
		private Hashtable<String, Trip> myTrips;
		
		/*Constructs new Airline objects.
		*There are restrictions on airline names, which are enforced in the constructor.
		*The max and min characters are defined statically, and requirements are
		*handled through a separate checking method, for easy modularity.
		*/
		public Line(String idArg) throws ManagementException
		{
			//Check to make sure the string is not null
			if(idArg == null)
			{
				throw new ManagementException("You have attempted to create a " + this.getClass().getName() + ", but the name is null.");
			}
			
			//Convert all names to upper case
			idArg = idArg.toUpperCase();

			//Do the name check
			boolean nameIsOK = checkId(idArg);
			
			if(!nameIsOK)
			{
				throw new ManagementException(this.getClass().getName() + " name must be between " + MINIDCHARS + " and " + MAXIDCHARS + " (inclusive) alphabetic characters, name is length " 
			+ idArg.length() + " for " + idArg + ".");
			}

			//If all of the above went OK, assign values
			id = idArg;
			myTrips = new Hashtable<String, Trip>();
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
		
		//Assign a Trip to this Line. An Line can only have one instance of a given trip ID.
		public void addTrip(Trip tripArg) throws ManagementException
		{
			//Check if the flight object is null
			if(tripArg == null)
			{
				throw new ManagementException("You tried to assign a null trip to " + this.getClass().getName() + " " + toString());
			}
			
			//We just need to check if this airline already has a flight with that number.
			Trip existingTrip = myTrips.get(tripArg.getId());
			if(existingTrip != null)
			{
				//Then the flight must already exist if existingFlight is not null
				throw new ManagementException("You are attempting to add " + tripArg + ", but a trip with that ID is already registered with the ID " + existingTrip.getId() + " to "  + existingTrip.getDestination()	);
			}
			
			//If the above test passed, then we can accept the flight for this Airline.
			myTrips.put(tripArg.getId(), tripArg);
		}
		
		//Returns a flight object given a flightID string. Returns null if no such flight belongs to this airline.
		public Trip findTrip(String idArg) throws NullPointerException
		{
			//Check if the string is null
			if(idArg == null)
			{
				throw new NullPointerException("You are attempting to look up a trip belonging to " + this.getClass().getName() + " " + toString() 
						+ ", but the trip ID is null.");
			}
			idArg = idArg.toUpperCase();
			return myTrips.get(idArg);
		}
		
		//Finds a linked list of flights with an open set on a flight from the origin airport to the destination airport
		public LinkedList<Trip> findAvailableTrips(Port originPort, Port destinationPort) throws ManagementException
		{
			//Check if the airports are null
			if(originPort == null || destinationPort == null)
			{
				throw new ManagementException("You are attempting to find flights from one airport to another, but either the destination or origin airport is null, or both. Origin: "
						+ originPort + ", Destination: " + destinationPort);
			}
				
			//Create an empty list of acceptable flights and convert the flight hash to linked list for easy iteration
			LinkedList<Trip> acceptableTripList = new LinkedList<Trip>();
			LinkedList<Trip> allTripList = SystemManager.hashtableToLinkedList(myTrips);
				
			for(Trip currentTrip : allTripList)
			{
				//If the current flight matches the given criteria, add it into the list of acceptable flights
				if(currentTrip.getOrigin() == originPort && currentTrip.getDestination() == destinationPort && currentTrip.hasAvailableSeat())
				{
					acceptableTripList.add(currentTrip);
				}
			}
			
			return acceptableTripList;
		}
		
		public String toString()
		{
			return id;
		}
		
		public Hashtable<String, Trip> getTrips()
		{
			return myTrips;
		}
		
		public String getId() 
		{
			return id;
		}
}
