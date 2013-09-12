package PA1.Model;

import PA1.Manager.ManagementException;

public class CruisePort extends Port{
	
	/*Constructs new Airport objects.
	*There are restrictions on airport names, which are enforced in the constructor for Port.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public CruisePort(String idArg) throws ManagementException
	{
			super(idArg);
	}
}
