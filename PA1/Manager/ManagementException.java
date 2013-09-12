package PA1.Manager;

public class ManagementException extends Exception {

	private static final long serialVersionUID = -5861172479256393626L;

	public ManagementException(String message)
	{
		super(message);
	}
	
	public ManagementException(String message, Throwable thrown)
	{
		super(message, thrown);
	}
}
