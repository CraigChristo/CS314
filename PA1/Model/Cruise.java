package PA1.Model;

import PA1.Manager.ManagementException;

public class Cruise extends Line<CruiseTrip, CruisePort>{

	/*Constructs new Airline objects.
	*There are restrictions on airline names, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Cruise(String idArg) throws ManagementException
	{
		super(idArg);
	}
}
