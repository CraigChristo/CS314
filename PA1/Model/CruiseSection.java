package PA1.Model;

import PA1.Manager.ManagementException;

public class CruiseSection extends Section<CruiseTrip>{
	
	/*Constructor.
	 * IMPORTANT: When you instantiate a FlightSection, it automatically adds itself to the Flight that you specify.
	 * As a result, the Flight cannot be null. Also, a FlightSection is attached to the Flight you give it at initialization-time,
	 * it cannot be transfered to a new Flight. The FlightSection automatically replaced a previous FlightSection of the same
	 * type (first, business, economy), but only if the previous FlightSection has no booked seats.
	 * Any violations of the above throws a ManagementException.
	 */
	public CruiseSection(CruiseTrip tripArg,  int rowsArg, int columnsArg, CruiseCabinClass typeArg) throws ManagementException
	{
		super(tripArg, rowsArg, columnsArg, typeArg);
	}
	
	//toString returns Strings like "first class" or "economy class"
	public String toString()
	{
		return SeatClassToString(this.type);
	}
	
	public static String SeatClassToString(CruiseCabinClass type)
	{
		switch(type){
			case single: return "single cabin";
			case family: return "family cabin";
			default: return "unknown class";
		}
	}
	
	
	//=====================================================================================================
	//=====================================================================================================
	//=====================================================================================================
	
	/*
	 * The Seat class represents a seat inside a FlightSection 
	 * Seats can only exist in a particular row and column of a particular flight section,
	 * it can't be instantiated without a parent FlightSection.
	 * The seat can reference its FlightSection, and through that, can know things about
	 * what kind of section (first, business, economy) and which Flight it's on.
	 */
	protected class Cabin extends Seat <CruiseSection>{
		/*
		The seat class tracks its X and Y position inside the FlightSection grid, which is zero based.
		Therefore a Seat's actual row is 1 more than its X and its column is 1 more than its Y (columns
		are also represented with characters). The Seat and FlightSection classes encapsulate this 
		behavior-- Just use their row and column methods to deal with rows and columns,	only interact 
		with X and Y if you know what you are doing. Note that the constructors do not take	X,Y 
		coordinates, only rows and columns.
		*/
		private int x; //row - 1
		private int y; //column - 1
		boolean booked;
		CruiseSection section;

		//Constructors require that rows and columns are given as you would see them (one's based),
		//such as '1A' or '3C'
		public Cabin(CruiseSection sectionArg, int rowArg, char colArg) throws ManagementException
		{
			super(sectionArg, rowArg, colArg);
		}
		
		//This constructor lets you give the columns as an integer, but the lowest column
		//you can give is 1.
		public Cabin(CruiseSection sectionArg, int rowArg, int colArg) throws ManagementException
		{
			super(sectionArg, rowArg, colArg);
		}

		
		public String toString()
		{
			return ( Integer.toString(getRow()) + getCol() );
		}
		
		/*==========
		   Getters
		  ==========*/
		
		public int getRow() 
		{
			return x + 1;
		}

		public char getCol() 
		{
			char letter = 'A';
			letter += y;
			return letter;
		}
		
	}
}
