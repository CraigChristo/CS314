package PA1.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import PA1.Manager.ManagementException;

/*
 * A Flight represents one connection between airports (it's an edge
 * in a graph of airport nodes). The Flight can have up to three FlightSections,
 * but it doesn't necessarily have any. The Flight cannot have any seats without having
 * FlightSections. A Flight also must belong to an airline. 
 */

public class Trip<P extends Port, L extends Line, S extends Section>{
	//Define the maximum and minimum number of ID characters,
	public final static int MAXIDCHARS = 6;
	public final static int MINIDCHARS = 3;
	
	//All flights must be scheduled after this day
	public final static int AFTERYEAR = 2012;
	public final static int AFTERMONTH = 12;
	public final static int AFTERDAY = 31;
	public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private String id;
	private L owner;
	private Port origin;
	private Port destination;
	private Calendar date;
	private LinkedList<S> sections;
	
	/*Constructs new Flight objects.
	*There are restrictions on flight ids, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Trip(L ownerArg, Port originArg, Port destinationArg, Calendar dateArg, String idArg) throws ManagementException
	{
		//Check to see if the string is null
		if(idArg == null)
		{
			throw new ManagementException("You are attempting to create a flight without a null ID. All flights must have IDs.");
		}
		idArg = idArg.toUpperCase();
		boolean nameIsOK = checkId(idArg);
		
		if(!nameIsOK)
		{
			throw new ManagementException("Flight ID must have between " + MINIDCHARS + " and " + MAXIDCHARS + " alphanumeric characters, the length is " 
					+ idArg.length() + " for ID " + idArg);
		}

		//Assign id and owner so that calls to this.toString() can resolve.
		id = idArg;
		
		if(ownerArg == null)
		{
			throw new ManagementException("You are attempting to create flight " + idArg + ", but no owner airline was given.");
		}
		owner = ownerArg;
		
		//Check if airline, airport and date objects are null
		if(originArg == null)
		{
			throw new ManagementException("You are attempting to create " + this.toString() + ", but no origin airport was given.");
		}
		if(destinationArg == null)
		{
			throw new ManagementException("You are attempting to create " + this.toString() + ", but no destination airport was given.");
		}
		if(dateArg == null)
		{
			throw new ManagementException("You are attempting to create " + this.toString() + ", but no date was given.");
		}
		//Flights cannot have the same origin and destination.
		if(originArg == destinationArg)
		{
			throw new ManagementException(this.toString() + " has an origin and destination at " + originArg 
					+".A flight cannot start and end at the same airport.");
		}
		
		//Check that the flight falls after the allowed start date
		Calendar earliestAllowedDate = Calendar.getInstance();
		earliestAllowedDate.set(AFTERYEAR, AFTERMONTH, AFTERDAY);
		if(!dateArg.after(earliestAllowedDate))
		{
			throw new ManagementException("Flights must be scheduled after " + simpleDateFormat.format(earliestAllowedDate.getTime()) 
					+", " + this.toString() + " is scheduled to fly on " + simpleDateFormat.format(dateArg.getTime()));
		}
		
		//Assignments
		origin = originArg;
		destination = destinationArg;
		date = dateArg;
		sections = new LinkedList<S>();
		
		//Add the flight to the Airline. This will throw an error if the airline already has a flight with this id.
		//This constructor will throw that error again up to whoever called it.
		owner.addTrip(this);		
	}
	
	public String name() {
		return "Trip";
	}
	
	//Performs a check on the ID.
	private boolean checkId(String idArg) throws ManagementException
	{
		boolean nameIsOK = true;
		
		//Use a regex to ensure that only alphanumeric characters are in the ID
		Pattern p = Pattern.compile(".*\\W+.*");
		Matcher m = p.matcher(idArg);
		boolean containsNonAlphanumeric = m.matches();
		
		if(idArg.length() < MINIDCHARS || idArg.length() > MAXIDCHARS)
		{
			nameIsOK = false;
		}
		if(containsNonAlphanumeric)
		{
			nameIsOK = false;		
		}
		
		return nameIsOK;
	}
	
	//Returns true if there is at least one available seat in any section
	public boolean hasAvailableSeat()
	{
		boolean seatsAvailable = false;
		
		for(Section section : sections)
		{
			if(section.hasAvailableSeat()) seatsAvailable = true;
		}
		return seatsAvailable;
	}
	
	//Books a seat in the given row and column of the given section
	public void bookSeat(Tier sectionType, int row, char col) throws ManagementException
	{
		Section desiredSection = null;
		
		//This for loop looks for the section we are trying to book the seat in
		for(Section currentSection : sections)
		{
			if(currentSection.getType() == sectionType)
			{
				desiredSection = currentSection;
			}
		}
		
		//If we didn't find the section, throw an error
		if(desiredSection == null) throw new ManagementException("You tried to book a seat on " + this.toString() 
				+ ", in section " + FlightSection.SeatClassToString(sectionType) + ", but the section you specified does not exist.");
		
		//Everything went well, try to book the seat.
		desiredSection.bookSeat(row, col);
	}
	
	public String toString()
	{
		return owner.toString() + " flight " + id;
	}
	
	/*==========
	   Getters
	  ==========*/
	public String getId()
	{
		return id;
	}
	
	public L getLine()
	{
		return owner;
	}
	
	public Calendar getDate()
	{
		return date;
	}
	
	public String getDateString()
	{
		return simpleDateFormat.format(date.getTime());
	}

	public Port getOrigin() 
	{
		return origin;
	}

	public Port getDestination() 
	{
		return destination;
	}
	
	public LinkedList<S> getSections()
	{
		return sections;
	}
}
