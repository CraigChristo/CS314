package PA1;

import PA1.UI.*;

public class ClientProg {
public static void main(String args[]){
	if (args.length == 1) {
		if (args[0].equals("flights"))
			Menu.open(new ABSOptions());
		if (args[0].equals("cruises"))
			Menu.open(new CBSOptions());
		else 
			System.out.println("Invalid command\nValid options are:\n\tflights\n\tcruises");
	} else
		System.out.println("Usage: ClientProg [flights | cruises]");
}
}