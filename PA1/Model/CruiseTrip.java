package PA1.Model;

import java.util.Calendar;

import PA1.Manager.ManagementException;

public class CruiseTrip extends Trip<CruisePort,Cruise,CruiseSection> {


	/*Constructs new Flight objects.
	*There are restrictions on flight ids, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public CruiseTrip(Cruise ownerArg, CruisePort originArg, CruisePort destinationArg, Calendar dateArg, String idArg) throws ManagementException
	{
		super(ownerArg, originArg, destinationArg, dateArg, idArg);
	}	
	
	public static String name() {
		return "Cruise";
	}
}
