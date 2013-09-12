package PA1.UI;

import PA1.Tests;
import PA1.Manager.SystemManager;
import PA1.Model.AirSeatClass;

public class MenuOptions {
	
	private SystemManager manager = null;
	
	public MenuOptions(SystemManager m) { manager = m; }
	
	public final MenuItem[] admin = {
			
		new MenuItem("Manage " + Menu.getString("PORT") + "s") {
			public void run() {
				Menu.open(port);
			}
		},
		
		new MenuItem("Manage " + Menu.getString("TRIP") + "s") {
			public void run() {
				Menu.open(trip);
			}
		},
		
		new MenuItem("Manage Bookings") {
			public void run() {
				Menu.open(booking);
			}
		},
		
		new MenuItem("Display system details") {
			public void run() {
				manager.displaySystemDetails();
			}
		},
		
		new MenuItem("Run tests") {
			public void run() {
				(new Tests(manager)).run();
			}
		}
		
	};
	
	public final MenuItem[] port = {		
			
		new MenuItem("Add a new " + Menu.getString("PORT")) {
			public void run() {
				String name = Menu.promptString("Enter new " + Menu.getString("PORT") + " name");
				manager.createPort(name);
			}
		},
		
		new MenuItem("Add a new " + Menu.getString("LINE")) {
			public void run() {
				String name = Menu.promptString("Enter new " + Menu.getString("LINE") + " name");
				manager.createLine(name);
			}
		},
		
		new MenuItem("Go Back") {
			public void run() {
				Menu.goUp();
			}
		},
		
		new MenuItem("Display system details") {
			public void run() {
				manager.displaySystemDetails();
			}
		}
		
	};
	
	public final MenuItem[] trip = {		
			
		new MenuItem("Add a new " + Menu.getString("TRIP")) {
			public void run() {
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
		},
		
		new MenuItem("Add a new " + Menu.getString("SECTION")) {
			public void run() {
				String line = Menu.promptString("Enter " + Menu.getString("LINE") + " name");
				String name = Menu.promptString("Enter " + Menu.getString("TRIP") + " id");
				
				System.out.println();
				
				int rows = Menu.promptInt("Enter number of rows");
				int cols = Menu.promptInt("Enter number of columns");
				String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
				
				manager.createSection(line,name,rows,cols,AirSeatClass.valueOf(tier));
			}
		},
		
		new MenuItem("Go Back") {
			public void run() {
				Menu.goUp();
			}
		},
		
		new MenuItem("Display system details") {
			public void run() {
				manager.displaySystemDetails();
			}
		}
		
	};
	
	public final MenuItem[] booking = {		
			
		//TODO add dates
		new MenuItem("Find " + Menu.getString("TRIP") + "s") {
			public void run() {
				String orig = Menu.promptString("Origin");
				String dest = Menu.promptString("Destination");
				
				System.out.println();
				
//					String[] trips = 
				manager.findAvailableTrips(orig, dest);
				
//					if (trips.length > 0)
//						for (String trip : trips) System.out.println(trip);
//					else System.out.println("No " + Menu.getString("TRIP") + "s found.");
			}
		},
		
		new MenuItem("Book a " + Menu.getString("SEAT")) {
			public void run() {
				String line = Menu.promptString("Enter " + Menu.getString("LINE") + " name");
				String name = Menu.promptString("Enter " + Menu.getString("TRIP") + " id");
				
				System.out.println();
				
				String tier = Menu.promptString("Enter class [" + manager.enumStrings() + "]");
				int row = Menu.promptInt("Enter row");
				char col = Menu.promptString("Enter column").charAt(0);
				//TODO SeatClass is hardcoded
				manager.bookSeat(line,name,AirSeatClass.valueOf(tier),row,col);
			}
		},
		
		new MenuItem("Go Back") {
			public void run() {
				Menu.goUp();
			}
		},
		
		new MenuItem("Display system details") {
			public void run() {
				manager.displaySystemDetails();
			}
		}
		
	};
	
}
