package PA1.Model;
/* ASSIGNMENT 1
* File: Flight.java
* Date: 08/28/2012
*/

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import PA1.Manager.ManagementException;

/*
 * A Flight represents one connection between airports (it's an edge
 * in a graph of airport nodes). The Flight can have up to three FlightSections,
 * but it doesn't necessarily have any. The Flight cannot have any seats without having
 * FlightSections. A Flight also must belong to an airline. 
 */

public class Flight extends Trip{

	
	private String id;
	private Airline owner;
	private Airport origin;
	private Airport destination;

	
	/*Constructs new Flight objects.
	*There are restrictions on flight ids, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Flight(Airline ownerArg, Airport originArg, Airport destinationArg, Calendar dateArg, String idArg) throws ManagementException
	{
		super(ownerArg, originArg, destinationArg, dateArg, idArg);
	}
	

}