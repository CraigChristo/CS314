package PA1.Manager;

import java.util.Calendar;
import java.util.Hashtable;

import PA1.Model.*;


public class ABSmanager extends SystemManager<Airport, Airline, Flight, FlightSection>{

	private static final String[] AIRPLANE_NOUNS = { "Airport", "Flight", "Flight Section", "Seat", "Airline"};
	
	public ABSmanager()
	{
		super(AIRPLANE_NOUNS);
		portDictionary = new Hashtable<String, Airport>();
		lineDictionary = new Hashtable<String, Airline>();
	}
	
	public Airport createAirport(String idArg)
	{
		Airport newPort;
		idArg = idArg.toUpperCase();

		try
		{
			newPort = new Airport(idArg); 
			portDictionary.put(idArg, newPort);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newPort = null;
		}

		return newPort;
	}
	
	public Airline createAirline(String idArg)
	{
		Airline newLine;
		idArg = idArg.toUpperCase();

		try
		{
			newLine = new Airline(idArg);
			lineDictionary.put(idArg, newLine);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newLine = null;
		}

		return newLine;
	}
	
	public Flight createFlight(String lineName, String origPort, String destPort, int year, int month, int day, String tripIdArg){
		Calendar date = getDate(year, month , day);
		
		Flight newTrip;

		try
		{
			//Turn airport & airline strings into airport & airline objects
			Airport originalPort = findPort(origPort);
			Airport destinationPort = findPort(destPort);
			Airline line = findLine(lineName);
			
			newTrip = new Flight(line, originalPort, destinationPort, date, tripIdArg);
		
		}
		catch(Exception e)
		{
			//If lookups on airports or airlines return no result, or the flight creation failed, then an error was thrown... return null
			System.out.println(e);
			newTrip = null;
		}

		return newTrip;
	}
	
	public FlightSection createFlightSection(String lineName, String tripID, int rows, int cols, AirSeatClass sectionType)
	{
		FlightSection newSection;

		try
		{
			//Lookup airline and flight
			Airline line = findLine(lineName);
			Flight trip = line.findTrip(tripID);
			newSection = new FlightSection(trip, rows, cols, sectionType);
		}
		catch(Exception e)
		{
			//If lookups on airline or flight return no result, or the section creation failed, then an error was thrown... return null
			System.out.println(e);
			newSection = null;
		}

		return newSection;
	}

	@Override
	public String enumStrings() {
		return enumStringsFromClass(AirSeatClass.class);
	}

	
}
