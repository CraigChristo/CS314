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

import PA1.Model.*;
import PA1.UI.MenuOptions.strings;
/* The SystemManager controls all system operations
 * The SystemManager will convert parameters into a 
 * more Object-Oriented form before passing them onto the
 * other classes.
 */
public abstract class SystemManager<P extends Port, L extends Line<T,P>, T extends Trip, S extends Section<T>> {
	
	public final String[] NOUNS = this.getNouns();
	
	public abstract String[] getNouns();
	
	public String getString(String in) {
		return NOUNS[strings.valueOf(in).ordinal()];
	}
	
	public abstract String enumStrings();
	
	protected <ti> String enumStringsFromClass(Class<ti> clazz) {
		String s = new String();
		for (Object e :  clazz.getEnumConstants())
			s += e.toString() + ", ";
		return s;
	}

	protected Hashtable<String, P> portDictionary;
	protected Hashtable<String, L> lineDictionary;

	protected SystemManager()
	{
		portDictionary = new Hashtable<String, P>();
		lineDictionary = new Hashtable<String, L>();
	}
	
	public Calendar getDate(int year, int month, int day){
		Calendar date = Calendar.getInstance();
		try
		{
			date.set(year, month, day);
		}
		catch(Exception e)
		{
			//If a bad date is passed in, like Feb 30th, we want to catch any errors
			System.out.println("You are attempting to create a " + getString("TRIP") + " on an invalid date, " +year+"/"+month+"/"+day);
		}
		return date;
	}
	
	private LinkedList<T> findTrips(String originPortName, String destinationPortName){
		//Check to make sure strings are not null before converting them to objects
				if(originPortName == null || destinationPortName == null)
				{
					System.out.println("You are attempting to find a flight with either a null origin or null destination, or both."
							+ "\nOrigin is " + originPortName + ", destination is " + destinationPortName + ".");
				}
				

				LinkedList<T> tripList = null;
				try
				{
					//Look up airports
					P originPortObj = findPort(originPortName);
					P destinationPortObj = findPort(destinationPortName);

					//Create a linked list of all the flights from all airlines that satisfy the criteria
					tripList = new LinkedList<T>();
					LinkedList<L> lineList = hashtableToLinkedList(lineDictionary);
					
					//Look through all airlines and add those that have flights that satisfy criteria
					for(L currentLine : lineList)
					{
						LinkedList<T> validTrips = currentLine.findAvailableTrips(originPortObj, destinationPortObj);
						tripList.addAll(validTrips);
					}
						
				}		catch(ManagementException me)
				{
					//If lookups on the airports failed, then an error was thrown... return null
					System.out.println(me);
					tripList = null;
				}
				return tripList;
	}
	
	private String [] LLtoStringArr(LinkedList<T> tripList){
		if (tripList == null)
			return null;
		else{
			String[] tripIds;
			//Find the length of the linked list of validFlights and create a string array
			int listLength = tripList.size();
			 tripIds = new String[listLength];

			//Put the names of all the flights into the string array and then return it
			int i = 0;
			for(T entry : tripList)
			{
				tripIds[i] = entry.toString();
				++i;
			}
			return tripIds;
		}
	}
	
	//Returns a string array of flights that have at least one available seat and are flying from the origin airport to a specific destination
	public String[] findAvailableTrips(String originPortName, String destinationPortName)
	{
		LinkedList<T> tripList = findTrips(originPortName, destinationPortName);
		String[] tripIds = LLtoStringArr(tripList);
		
		return tripIds;
	}
	
	public String[] findTripsByDate(String originPortName, String destinationPortName, int year, int month, int day){
		LinkedList<T> tripList = findTrips(originPortName, destinationPortName);
		
		Calendar date = getDate(year,month,day);
		
		if (tripList != null){
			for (T trip : tripList){
				Calendar tripDate = trip.getDate();
				if (!(tripDate.get(Calendar.YEAR) == date.get(Calendar.YEAR) && tripDate.get(Calendar.MONTH) == date.get(Calendar.MONTH) && tripDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)))
					tripList.remove(trip);
			}
		}
		return LLtoStringArr(tripList);
	}

	//Attempt to book a seat on a given airline's flight in the row and column of a given section
	public boolean bookSeat(String lineName, String tripID, Tier sectionType, int row, char column)
	{
		//Check to make sure strings are not null before converting them to objects
		if(lineName == null) System.out.println("You are attempting to book a seat, but no " + getString("LINE") + " was specified.");
		if(tripID == null) System.out.println("You are attempting to book a seat, but no " + getString("TRIP") + " was specified.");

		boolean bookingSuccess;

		try
		{
			//Look up airline and flight
			L line = findLine(lineName);
			T trip = line.findTrip(tripID);
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
		
		System.out.println("___"+ getString("PORT") +"___");
		LinkedList<P> portList = hashtableToLinkedList(portDictionary);
		for(P currentPort : portList)
		{
			//Print all airports
			System.out.println(currentPort);
		}

		System.out.println("\n___"+ getString("LINE") +"___");
		LinkedList<L> lineList = hashtableToLinkedList(lineDictionary);
		for(L currentLine : lineList)
		{
			//Print all airlines
			System.out.println(currentLine);
			
			LinkedList<T> tripList = hashtableToLinkedList(currentLine.getTrips());
			for(T currentTrip : tripList)
			{
				//Print all flights for a given airline
				System.out.println("\t"+ getString("TRIP") + " " + currentTrip.getId() + " from " + currentTrip.getOrigin() + " to " 
						+ currentTrip.getDestination() + " on " + currentTrip.getDateString());
				
				LinkedList<S> sectionList = currentTrip.getSections();
				for(S currentSection : sectionList)
				{
					//Print all sections and seats for a given flight
					System.out.println("\t\t" + currentSection);
					System.out.println(currentSection.seatDetails(3));	
				}
			}
		}
	}

	//Find an airport object given the string name. Cannot be given null strings.
	public P findPort(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an " + getString("PORT") + ", but the" + getString("PORT") + "name is null.");
		}
		name = name.toUpperCase();
		P port = portDictionary.get(name);
		if(port == null)
		{
			throw new ManagementException("You have attempted to look up an " + getString("PORT") + " that does not exist. The name you gave was " +name);
		}
		return port;
	}

	//Find an airline object given the string name. Cannot be given null strings.
	public L findLine(String name) throws ManagementException
	{
		if(name == null)
		{
			throw new NullPointerException("You are attempting to look up an " + getString("LINE") + ", but the " + getString("LINE") + " name is null.");
		}
		name = name.toUpperCase();
		L line = lineDictionary.get(name);
		if(line == null)
		{
			throw new ManagementException("You have attempted to look up an " + getString("LINE") + " that does not exist. The name you gave was " +name);
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