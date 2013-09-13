package PA1.UI;

import PA1.Manager.CBSmanager;
import PA1.Model.CruiseCabinClass;

public class CBSOptions extends MenuOptions<CBSmanager> {
	
	public CBSOptions() {
		manager = new CBSmanager();
	}
	
	public CBSOptions(CBSmanager m) {
		manager = m;
	}
	
	protected void portPrompt() {
		String name = Menu.promptString("Enter new " + getString("PORT") + " name");
		manager.createPort(name);
	}

	@Override
	protected void linePrompt() {
		String name = Menu.promptString("Enter new " + getString("LINE") + " name");
		manager.createCruiseline(name);
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
		
		manager.createTrip(line,orig,dest,year,month,day,name);
	}

	@Override
	protected void sectionPrompt() {
		String line = Menu.promptString("Enter " + getString("LINE") + " name");
		String name = Menu.promptString("Enter " + getString("TRIP") + " id");
		
		System.out.println();
		
		int rows = Menu.promptInt("Enter number of rows");
		int cols = Menu.promptInt("Enter number of columns");
		String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
		
		manager.createCruiseSection(line,name,rows,cols, CruiseCabinClass.valueOf(tier));
	}

	@Override
	protected void bookingPrompt() {
		String line = Menu.promptString("Enter " + getString("LINE") + " name");
		String name = Menu.promptString("Enter " + getString("TRIP") + " id");
		
		System.out.println();
		
		String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
		int row = Menu.promptInt("Enter row");
		char col = Menu.promptString("Enter column").charAt(0);
		manager.bookSeat(line,name,CruiseCabinClass.valueOf(tier),row,col);
	}

	@Override
	protected void runTests() {
		// TODO Auto-generated method stub
		
	}
}
