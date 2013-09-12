package PA1.Model;

import PA1.Manager.ManagementException;
/* ASSIGNMENT 1
* File: Airline.java
* Date: 08/28/2012
*/

public class Airline extends Line{

	
	/*Constructs new Airline objects.
	*There are restrictions on airline names, which are enforced in the constructor.
	*The max and min characters are defined statically, and requirements are
	*handled through a separate checking method, for easy modularity.
	*/
	public Airline(String idArg) throws ManagementException
	{
		super(idArg);
	}
	
}