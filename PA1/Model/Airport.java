package PA1.Model;
/* ASSIGNMENT 1
* File: Airport.java
* Date: 08/28/2012
*/

import PA1.Manager.ManagementException;


public class Airport extends Port {

	/*Constructs new Airport objects.
	*There are restrictions on airport names, which are enforced in the constructor for Port.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Airport(String idArg) throws ManagementException
	{
			super(idArg);
	}
	
	public String name() {
		return "Airport";
	}
}