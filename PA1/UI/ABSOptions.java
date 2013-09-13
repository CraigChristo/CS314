package PA1.UI;

import PA1.PlaneTests;
import PA1.Manager.ABSmanager;
import PA1.Model.AirSeatClass;

public class ABSOptions extends MenuOptions<ABSmanager> {
	
	public ABSOptions() {
		manager = new ABSmanager();
	}
	
	public ABSOptions(ABSmanager m) {
		manager = m;
	}

	@Override
	protected void portPrompt() {
		String name = Menu.promptString("Enter new " + getString("PORT") + " name");
		manager.createAirport(name);
	}

	@Override
	protected void linePrompt() {
		String name = Menu.promptString("Enter new " + getString("LINE") + " name");
		manager.createAirline(name);
	}

	@Override
	protected void tripPrompt() {
		String line = Menu.promptString("Enter " + getString("LINE") + " name");
		String name = Menu.promptString("Enter new " + getString("TRIP") + " id");
		String orig = Menu.promptString("Origin");
		String dest = Menu.promptString("Destination");
		
		System.out.println();
		
		int day = Menu.promptInt("Day");
		int month = Menu.promptInt("Month");
		int year = Menu.promptInt("Year");
		
		manager.createFlight(line,orig,dest,year,month,day,name);
	}

	@Override
	protected void sectionPrompt() {
		String line = Menu.promptString("Enter " + getString("LINE") + " name");
		String name = Menu.promptString("Enter " + getString("TRIP") + " id");
		
		System.out.println();
		
		int rows = Menu.promptInt("Enter number of rows");
		int cols = Menu.promptInt("Enter number of columns");
		String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
		
		manager.createFlightSection(line,name,rows,cols,AirSeatClass.valueOf(tier));
	}
	
	protected void bookingPrompt() {
		String line = Menu.promptString("Enter " + getString("LINE") + " name");
		String name = Menu.promptString("Enter " + getString("TRIP") + " id");
		
		System.out.println();
		
		String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
		int row = Menu.promptInt("Enter row");
		char col = Menu.promptString("Enter column").charAt(0);
		manager.bookSeat(line,name,AirSeatClass.valueOf(tier),row,col);
	}
	
	@Override
	protected void runTests() {
		(new PlaneTests(manager)).run();
	}
}
