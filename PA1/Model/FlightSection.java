package PA1.Model;
/* ASSIGNMENT 1
* File: FlightSection.java
* Date: 08/28/2012
*/

import java.util.LinkedList;

import PA1.Manager.ManagementException;



/*
 * The FlightSection class represents a group of seats within a Flight.
 * A FlightSection cannot exist without a parent Flight, and also must
 * have a particular section type (first, business, economy).
 * A FlightSection has a rectangular array of seats which is created at
 * initialization-time, so the FlightSection always has the right number of
 * seats (and there's no way to add more or to take them out without 
 * replacing the section).
 */

public class FlightSection extends Section{
	
	private static final int MAXROWS = 100;
	private static final int MINROWS = 1;
	private static final int MAXCOLUMNS = 10;
	private static final int MINCOLUMNS = 1;
	
	private SeatClass type;
	private int rows;
	private int columns;
	private Flight flight;
	private boolean isEmpty;
	private Seat[][] seatArray;
	
	/*Constructor.
	 * IMPORTANT: When you instantiate a FlightSection, it automatically adds itself to the Flight that you specify.
	 * As a result, the Flight cannot be null. Also, a FlightSection is attached to the Flight you give it at initialization-time,
	 * it cannot be transfered to a new Flight. The FlightSection automatically replaced a previous FlightSection of the same
	 * type (first, business, economy), but only if the previous FlightSection has no booked seats.
	 * Any violations of the above throws a ManagementException.
	 */
	public FlightSection(Flight flightArg,  int rowsArg, int columnsArg, SeatClass typeArg) throws ManagementException
	{
		super(flightArg, rowsArg, columnsArg, typeArg);
	}
	
	//toString returns Strings like "first class" or "economy class"
	public String toString()
	{
		return SeatClassToString(this.type);
	}
	
	public static String SeatClassToString(SeatClass type)
	{
		switch(type){
			case first: return "first class";
			case business: return "business class";
			case economy: return "economy class";
			default: return "unkown class";
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
	protected class PlaneSeat extends Seat {
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
		FlightSection section;

		//Constructors require that rows and columns are given as you would see them (one's based),
		//such as '1A' or '3C'
		public PlaneSeat(FlightSection sectionArg, int rowArg, char colArg) throws ManagementException
		{
			super(sectionArg, rowArg, colArg);
		}
		
		//This constructor lets you give the columns as an integer, but the lowest column
		//you can give is 1.
		public PlaneSeat(FlightSection sectionArg, int rowArg, int colArg) throws ManagementException
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