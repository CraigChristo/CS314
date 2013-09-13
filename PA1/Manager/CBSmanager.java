import java.util.Hashtable;


public class cBSmanager extends SystemManager<CruisePort, Cruise, CruiseTrip, CruiseSection>{
	
	private Hashtable<String, CruisePort> portDictionary;
	private Hashtable<String, Cruise> lineDictionary;

	public CBSmanager()
	{
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
	
	public Cruise createLine(String idArg)
	{
		Airine newLine;
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
			Port originalPort = findPort(origPort);
			Port destinationPort = findPort(destPort);
			Line line = findLine(lineName);
			
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
	
	public CruiseSection createSection(String lineName, String tripID, int rows, int cols, AirSeatClass sectionType)
	{
		Section newSection;

		try
		{
			//Lookup Cruise and flight
			Line line = findLine(lineName);
			Trip trip = line.findTrip(tripID);
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

	
}


