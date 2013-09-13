package PA1.Manager;

import java.util.Calendar;
import java.util.Hashtable;

import PA1.Model.*;

public class CBSmanager extends SystemManager<CruisePort, Cruise, CruiseTrip, CruiseSection>{

	public static final String[] BOAT_NOUNS = { "Port", "Cruise", "Section", "Cabin", "Cruise Line" };
	
	public CBSmanager()
	{
		super(BOAT_NOUNS);
		portDictionary = new Hashtable<String, CruisePort>();
		lineDictionary = new Hashtable<String, Cruise>();
	}
	
	public CruisePort createPort(String idArg)
	{
		CruisePort newPort;
		idArg = idArg.toUpperCase();

		try
		{
			newPort = new CruisePort(idArg); 
			portDictionary.put(idArg, newPort);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newPort = null;
		}

		return newPort;
	}
	
	public Cruise createCruiseline(String idArg)
	{
		Cruise newLine;
		idArg = idArg.toUpperCase();

		try
		{
			newLine = new Cruise(idArg);
			lineDictionary.put(idArg, newLine);
		}
		catch(ManagementException me)
		{
			System.out.println(me);
			newLine = null;
		}

		return newLine;
	}
	
	public CruiseTrip createTrip(String lineName, String origPort, String destPort, int year, int month, int day, String tripIdArg){
		Calendar date = getDate(year, month , day);
		
		CruiseTrip newTrip;

		try
		{
			//Turn CruisePort & Cruise strings into CruisePort & Cruise objects
			CruisePort originalPort = findPort(origPort);
			CruisePort destinationPort = findPort(destPort);
			Cruise line = findLine(lineName);
			
			newTrip = new CruiseTrip(line, originalPort, destinationPort, date, tripIdArg);
		
		}
		catch(Exception e)
		{
			//If lookups on CruisePorts or Cruises return no result, or the CruiseTrip creation failed, then an error was thrown... return null
			System.out.println(e);
			newTrip = null;
		}

		return newTrip;
	}
	
	public CruiseSection createCruiseSection(String lineName, String tripID, int rows, int cols, CruiseCabinClass sectionType)
	{
		CruiseSection newSection;

		try
		{
			//Lookup Cruise and flight
			Cruise line = findLine(lineName);
			CruiseTrip trip = line.findTrip(tripID);
			newSection = new CruiseSection(trip, rows, cols, sectionType);
		}
		catch(Exception e)
		{
			//If lookups on Cruise or flight return no result, or the section creation failed, then an error was thrown... return null
			System.out.println(e);
			newSection = null;
		}

		return newSection;
	}

	@Override
	public String enumStrings() {
		return enumStringsFromClass(CruiseCabinClass.class);
	}

	
}
