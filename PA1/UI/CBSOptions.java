package PA1.UI;

import PA1.Manager.CBSmanager;
import PA1.Model.AirSeatClass;

public class CBSOptions extends MenuOptions<CBSmanager> {
	
	public CBSOptions() {
		manager = new CBSmanager();
	}
	
	protected void portPrompt() {
		String name = Menu.promptString("Enter new " + Menu.getString("PORT") + " name");
		manager.createPort(name);
	}

	@Override
	protected void linePrompt() {
		String name = Menu.promptString("Enter new " + Menu.getString("LINE") + " name");
		manager.createLine(name);
	}

	@Override
	protected void tripPrompt() {
		String line = Menu.promptString("Enter " + Menu.getString("LINE") + " name");
		String name = Menu.promptString("Enter new " + Menu.getString("TRIP") + " id");
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
		String line = Menu.promptString("Enter " + Menu.getString("LINE") + " name");
		String name = Menu.promptString("Enter " + Menu.getString("TRIP") + " id");
		
		System.out.println();
		
		int rows = Menu.promptInt("Enter number of rows");
		int cols = Menu.promptInt("Enter number of columns");
		String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
		
		manager.createSection(line,name,rows,cols,AirSeatClass.valueOf(tier));
	}

	@Override
	protected void bookingPrompt() {
		String line = Menu.promptString("Enter " + Menu.getString("LINE") + " name");
		String name = Menu.promptString("Enter " + Menu.getString("TRIP") + " id");
		
		System.out.println();
		
		String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
		int row = Menu.promptInt("Enter row");
		char col = Menu.promptString("Enter column").charAt(0);
		manager.bookSeat(line,name,AirSeatClass.valueOf(tier),row,col);
	}

	@Override
	protected void runTests() {
		// TODO Auto-generated method stub
		
	}
}
