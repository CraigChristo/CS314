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

import PA1.Models.Airline;
import PA1.Models.Airport;
import PA1.Models.Flight;
import PA1.Models.FlightSection;
import PA1.Models.SeatClass;
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

	private Hashtable<String, Airport> airportDictionary;
	private Hashtable<String, Airline> airlineDictionary;

	public SystemManager()
	{
		airportDictionary = new Hashtable<String, Airport>();
		airlineDictionary = new Hashtable<String, Airline>();
	}

	//Creates a new Airport. Returns null if there is an error during construction.
	public Airport createAirport(String idArg)
	{
		Airport newAirport;

		try
		{
			newAirport = new Airport(idArg);
			airportDictionary.put(idArg, newAirport);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newAirport = null;
		}

		return newAirport;
	}
	
	//Creates a new Airline. Returns null if there is an error during construction.
	public Airline createAirline(String idArg)
	{
		Airline newAirline;

		try
		{
			newAirline = new Airline(idArg);
			airlineDictionary.put(idArg, newAirline);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newAirline = null;
		}

		return newAirline;
	}

	//Creates a flight given the name of an airline, origin airport, destination airport, date, and flight id.
	//All parameters are converted to object oriented format before being passed to their respective classes
	public Flight createFlight(String airlineName, String origAirport, String destAirport, int year, int month, int day, String flightIdArg)
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
			System.out.println("You are attempting to create a flight with ID " + flightIdArg + " on an invalid date, " +year+"/"+month+"/"+day);
		}

		Flight newFlight;

		try
		{
			//Turn airport & airline strings into airport & airline objects
			Airport origPort = findAirport(origAirport);
			Airport destPort = findAirport(destAirport);
			Airline airline = findAirline(airlineName);
			
			//The Flight constructor adds the given flight to the owning airlines flight list
			newFlight = new Flight(airline, origPort, destPort, date, flightIdArg);
		}
		catch(Exception e)
		{
			//If lookups on airports or airlines return no result, or the flight creation failed, then an error was thrown... return null
			System.out.println(e);
			newFlight = null;
		}

		return newFlight;
	}

	//Creates a seating section given the airline name, flight id, number of rows and columns and the class of the seating section
	public FlightSection createSection(String airlineName, String flightID, int rows, int cols, SeatClass sectionType)
	{
		FlightSection newSection;

		try
		{
			//Lookup airline and flight
			Airline air = findAirline(airlineName);
			Flight flight = air.findFlight(flightID);
			newSection = new FlightSection(flight, rows, cols, sectionType);
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
	public String[] findAvailableFlights(String originAirportName, String destinationAirportName)
	{
		//Check to make sure strings are not null before converting them to objects
		if(originAirportName == null || destinationAirportName == null)
		{
			System.out.println("You are attempting to find a flight with either a null origin or null destination, or both."
					+ "\nOrigin is " + originAirportName + ", destination is " + destinationAirportName + ".");
		}
		
		String[] flightIds;
		
		try
		{
			//Look up airports
			Airport originAirportObj = findAirport(originAirportName);
			Airport destinationAirportObj = findAirport(destinationAirportName);

			//Create a linked list of all the flights from all airlines that satisfy the criteria
			LinkedList<Flight> flightList = new LinkedList<Flight>();
			LinkedList<Airline> airlineList = hashtableToLinkedList(airlineDictionary);
			
			//Look through all airlines and add those that have flights that satisfy criteria
			for(Airline currentAirline : airlineList)
			{
				LinkedList<Flight> validFlights = currentAirline.findAvailableFlights(originAirportObj, destinationAirportObj);
				flightList.addAll(validFlights);
			}
			
			//Find the length of the linked list of validFlights and create a string array
			int listLength = flightList.size();
			 flightIds = new String[listLength];

			//Put the names of all the flights into the string array and then return it
			int i = 0;
			for(Flight entry : flightList)
			{
				flightIds[i] = entry.toString();
				++i;
			}
		}
		catch(ManagementException me)
		{
			//If lookups on the airports failed, then an error was thrown... return null
			System.out.println(me);
			flightIds = null;
		}

		if(flightIds != null)
		{
			//Print all flights that match that given criteria
			for(String currentFlight : flightIds)
			{
				System.out.println(currentFlight);
			}
		}
		
		return flightIds;
	}

	//Attempt to book a seat on a given airline's flight in the row and column of a given section
	public boolean bookSeat(String airlineName, String flightID, SeatClass sectionType, int row, char column)
	{
		//Check to make sure strings are not null before converting them to objects
		if(airlineName == null) System.out.println("You are attempting to book a seat, but no airline was specified.");
		if(flightID == null) System.out.println("You are attempting to book a seat, but no flight was specified.");

		boolean bookingSuccess;

		try
		{
			//Look up airline and flight
			Airline airline = findAirline(airlineName);
			Flight flight = airline.findFlight(flightID);
			flight.bookSeat(sectionType, row, column);
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
		LinkedList<Airport> airportList = hashtableToLinkedList(airportDictionary);
		for(Airport currentAirport : airportList)
		{
			//Print all airports
			System.out.println(currentAirport);
		}

		System.out.println("\n___Airlines___");
		LinkedList<Airline> airlineList = hashtableToLinkedList(airlineDictionary);
		for(Airline currentAirline : airlineList)
		{
			//Print all airlines
			System.out.println(currentAirline);
			
			LinkedList<Flight> flightList = hashtableToLinkedList(currentAirline.getFlights());
			for(Flight currentFlight : flightList)
			{
				//Print all flights for a given airline
				System.out.println("\tFlight " + currentFlight.getId() + " from " + currentFlight.getOrigin() + " to " 
						+ currentFlight.getDestination() + " on " + currentFlight.getDateString());
				
				LinkedList<FlightSection> sectionList = currentFlight.getFlightSections();
				for(FlightSection currentSection : sectionList)
				{
					//Print all sections and seats for a given flight
					System.out.println("\t\t" + currentSection);
					System.out.println(currentSection.seatDetails(3));	
				}
			}
		}
	}

	//Find an airport object given the string name. Cannot be given null strings.
	public Airport findAirport(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an airport, but the airport name is null.");
		}
		name = name.toUpperCase();
		Airport air = airportDictionary.get(name);
		if(air == null)
		{
			throw new ManagementException("You have attempted to look up an airport that does not exist. The name you gave was " +name);
		}
		return air;
	}

	//Find an airline object given the string name. Cannot be given null strings.
	public Airline findAirline(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an airline, but the airline name is null.");
		}
		name = name.toUpperCase();
		Airline air = airlineDictionary.get(name);
		if(air == null)
		{
			throw new ManagementException("You have attempted to look up an airline that does not exist. The name you gave was " +name);
		}
		return air;
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