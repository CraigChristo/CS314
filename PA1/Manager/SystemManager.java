package PA1.Manager;
/* ASSIGNMENT 1
* File: SystemManager.java
* Date: 08/28/2012
*/

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import PA1.Model.Tier;
import PA1.Model.Line;
import PA1.Model.Port;
import PA1.Model.SeatClass;
import PA1.Model.Section;
import PA1.Model.Trip;
/* The SystemManager is the controlling mechanism for
 * Airport, Airline, Flight, FlightSection, and Seat.
 * The SystemManager will convert their parameters into a 
 * more Object-Oriented form before passing them onto the
 * other classes.
 */
public class SystemManager {
	
	public static enum strings {
		PORT("Airport"),
		TRIP("Flight"),
		TRIPS("Flights"),
		SECTION("Flight Section"),
		SEAT("Seat"),
		LINE("Airline");
		
		
		private String name;
		private strings(String s) {
			name = s;
		}
		public String toString() {
			return name;
		}
	}
	
	//TODO the enum class is hardcoded
	public String enumStrings() {
		String s = new String();
		for (Tier e : SeatClass.class.getEnumConstants()) 
			s += e.toString() + ", ";
		return s;
	}

	private Hashtable<String, Port> portDictionary;
	private Hashtable<String, Line> lineDictionary;

	public SystemManager()
	{
		portDictionary = new Hashtable<String, Port>();
		lineDictionary = new Hashtable<String, Line>();
	}

	//Creates a new Airport. Returns null if there is an error during construction.
	public Port createPort(String idArg)
	{
		Port newPort;

		try
		{
			newPort = new Port(idArg);
			portDictionary.put(idArg, newPort);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newPort = null;
		}

		return newPort;
	}
	
	//Creates a new Airline. Returns null if there is an error during construction.
	public Line createLine(String idArg)
	{
		Line newLine;

		try
		{
			newLine = new Line(idArg);
			lineDictionary.put(idArg, newLine);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newLine = null;
		}

		return newLine;
	}

	//Creates a flight given the name of an airline, origin airport, destination airport, date, and flight id.
	//All parameters are converted to object oriented format before being passed to their respective classes
	public Trip createTrip(String lineName, String origPort, String destPort, int year, int month, int day, String tripIdArg)
	{
		//Create a calendar object given the date arguments
		Calendar date = Calendar.getInstance();
		try
		{
			date.set(year, month, day);
		}
		catch(Exception e)
		{
			//If a bad date is passed in, like Feb 30th, we want to catch any errors
			System.out.println("You are attempting to create a flight with ID " + tripIdArg + " on an invalid date, " +year+"/"+month+"/"+day);
		}

		Trip newTrip;

		try
		{
			//Turn airport & airline strings into airport & airline objects
			Port originalPort = findPort(origPort);
			Port destinationPort = findPort(destPort);
			Line line = findLine(lineName);
			
			//The Flight constructor adds the given flight to the owning airlines flight list
			newTrip = new Trip(line, originalPort, destinationPort, date, tripIdArg);
		}
		catch(Exception e)
		{
			//If lookups on airports or airlines return no result, or the flight creation failed, then an error was thrown... return null
			System.out.println(e);
			newTrip = null;
		}

		return newTrip;
	}

	//Creates a seating section given the airline name, flight id, number of rows and columns and the class of the seating section
	public Section createSection(String lineName, String tripID, int rows, int cols, SeatClass sectionType)
	{
		Section newSection;

		try
		{
			//Lookup airline and flight
			Line line = findLine(lineName);
			Trip trip = line.findTrip(tripID);
			newSection = new Section(trip, rows, cols, sectionType);
		}
		catch(Exception e)
		{
			//If lookups on airline or flight return no result, or the section creation failed, then an error was thrown... return null
			System.out.println(e);
			newSection = null;
		}

		return newSection;
	}
	
	//Returns a string array of flights that have at least one available seat and are flying from the origin airport to a specific destination
	public String[] findAvailableTrips(String originPortName, String destinationPortName)
	{
		//Check to make sure strings are not null before converting them to objects
		if(originPortName == null || destinationPortName == null)
		{
			System.out.println("You are attempting to find a flight with either a null origin or null destination, or both."
					+ "\nOrigin is " + originPortName + ", destination is " + destinationPortName + ".");
		}
		
		String[] tripIds;
		
		try
		{
			//Look up airports
			Port originPortObj = findPort(originPortName);
			Port destinationPortObj = findPort(destinationPortName);

			//Create a linked list of all the flights from all airlines that satisfy the criteria
			LinkedList<Trip> tripList = new LinkedList<Trip>();
			LinkedList<Line> lineList = hashtableToLinkedList(lineDictionary);
			
			//Look through all airlines and add those that have flights that satisfy criteria
			for(Line currentLine : lineList)
			{
				LinkedList<Trip> validTrips = currentLine.findAvailableTrips(originPortObj, destinationPortObj);
				tripList.addAll(validTrips);
			}
			
			//Find the length of the linked list of validFlights and create a string array
			int listLength = tripList.size();
			 tripIds = new String[listLength];

			//Put the names of all the flights into the string array and then return it
			int i = 0;
			for(Trip entry : tripList)
			{
				tripIds[i] = entry.toString();
				++i;
			}
		}
		catch(ManagementException me)
		{
			//If lookups on the airports failed, then an error was thrown... return null
			System.out.println(me);
			tripIds = null;
		}

		if(tripIds != null)
		{
			//Print all flights that match that given criteria
			for(String currentTrip : tripIds)
			{
				System.out.println(currentTrip);
			}
		}
		
		return tripIds;
	}

	//Attempt to book a seat on a given airline's flight in the row and column of a given section
	public boolean bookSeat(String lineName, String tripID, SeatClass sectionType, int row, char column)
	{
		//Check to make sure strings are not null before converting them to objects
		if(lineName == null) System.out.println("You are attempting to book a seat, but no airline was specified.");
		if(tripID == null) System.out.println("You are attempting to book a seat, but no flight was specified.");

		boolean bookingSuccess;

		try
		{
			//Look up airline and flight
			Line line = findLine(lineName);
			Trip trip = line.findTrip(tripID);
			trip.bookSeat(sectionType, row, column);
			bookingSuccess = true;
		}
		catch(ManagementException me)
		{
			//If look up on the airline or flight failed, or the seat was unable to be booked, throw an error... return false
			System.out.println(me);
			bookingSuccess = false;
		}

		return bookingSuccess;
	}

	//Print out details of all objects
	public void displaySystemDetails()
	{
		System.out.println("___Airports___");
		LinkedList<Port> portList = hashtableToLinkedList(portDictionary);
		for(Port currentPort : portList)
		{
			//Print all airports
			System.out.println(currentPort);
		}

		System.out.println("\n___Airlines___");
		LinkedList<Line> lineList = hashtableToLinkedList(lineDictionary);
		for(Line currentLine : lineList)
		{
			//Print all airlines
			System.out.println(currentLine);
			
			LinkedList<Trip> tripList = hashtableToLinkedList(currentLine.getTrips());
			for(Trip currentTrip : tripList)
			{
				//Print all flights for a given airline
				System.out.println("\tFlight " + currentTrip.getId() + " from " + currentTrip.getOrigin() + " to " 
						+ currentTrip.getDestination() + " on " + currentTrip.getDateString());
				
				LinkedList<Section> sectionList = currentTrip.getSections();
				for(Section currentSection : sectionList)
				{
					//Print all sections and seats for a given flight
					System.out.println("\t\t" + currentSection);
					System.out.println(currentSection.seatDetails(3));	
				}
			}
		}
	}

	//Find an airport object given the string name. Cannot be given null strings.
	public Port findPort(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an airport, but the airport name is null.");
		}
		name = name.toUpperCase();
		Port port = portDictionary.get(name);
		if(port == null)
		{
			throw new ManagementException("You have attempted to look up an airport that does not exist. The name you gave was " +name);
		}
		return port;
	}

	//Find an airline object given the string name. Cannot be given null strings.
	public Line findLine(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an airline, but the airline name is null.");
		}
		name = name.toUpperCase();
		Line line = lineDictionary.get(name);
		if(line == null)
		{
			throw new ManagementException("You have attempted to look up an airline that does not exist. The name you gave was " +name);
		}
		return line;
	}

	//Converts hash tables to linked lists for easier iteration. This is static so that other classes can call this method,
	//and you can use this method even if you aren't otherwise using the SystemManager.
	public static <KeyType, ValueType> LinkedList<ValueType> hashtableToLinkedList(Hashtable<KeyType,ValueType> hash)
	{
		/*This next section was inspired by Adamski's answer to a question on Stackoverflow:
		stackoverflow.com/question/2351331/iterating-hashtable-in-java
		 */
		LinkedList<ValueType> newList = new LinkedList<ValueType>();
		Iterator<Entry<KeyType, ValueType>> iter = hash.entrySet().iterator();

		while(iter.hasNext())
		{
			Map.Entry<KeyType, ValueType> entry = iter.next();
			ValueType current = entry.getValue();

			newList.add(current);
		}
		return newList;
	}
}