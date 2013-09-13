package PA1.UI;

import PA1.Manager.SystemManager;

public abstract class MenuOptions<T extends SystemManager> {
	
	//lol
	private static final String SECURE_USERNAME = "admin";
	private static final String SECURE_PASSWORD = "admin123";

	public T manager = null;
	
	protected MenuOptions() {}
	
	protected String getString(String in) {
		int i = T.classes.valueOf(in).ordinal();
		return manager.nouns[i];
	}
	
	public final MenuItem[] main = {
		
		new MenuItem("Administration") {
			public void run() {
				if (doAuthentication()) Menu.open(admin);
				else System.out.println("Not authorized");
			}
		},
		
		new MenuItem("User") {
			public void run() {
				Menu.open(user);
			}
		}
	};
	
	public final MenuItem[] admin = {
			
		new MenuItem("Manage " + getString("PORT") + "s") {
			public void run() {
				Menu.open(port);
			}
		},
		
		new MenuItem("Manage " + getString("TRIP") + "s") {
			public void run() {
				Menu.open(trip);
			}
		},
		
		new MenuItem("Run tests") {
			public void run() {
				runTests();
			}
		},
		
		new MenuItem("Display system details") {
			public void run() {
				manager.displaySystemDetails();
			}
		}	
	};
	
	public final MenuItem[] port = {		
			
		new MenuItem("Add a new " + getString("PORT")) {
			public void run() {
				portPrompt();
			}
		},
		
		new MenuItem("Add a new " + getString("LINE")) {
			public void run() {
				linePrompt();
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
			
		new MenuItem("Add a new " + getString("TRIP")) {
			public void run() {
				tripPrompt();
			}
		},
		
		new MenuItem("Add a new " + getString("SECTION")) {
			public void run() {
				sectionPrompt();
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
	
	public final MenuItem[] user = {		

		new MenuItem("Find " + getString("TRIP") + "s by " + getString("PORT") + "s") {
			public void run() {
				findTripByLoc();
			}
		},
		
		new MenuItem("Find " + getString("TRIP") + "s by date") {
			public void run() {
				findTripByLoc();
			}
		},
		
		new MenuItem("Book a " + getString("SEAT")) {
			public void run() {
				bookingPrompt();
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
	
	private boolean doAuthentication() {
		String user = Menu.promptString("Username");
		String pwd = Menu.promptString("Password (plaintext)");
		
		if (user.equals(SECURE_USERNAME))
			if (pwd.equals(SECURE_PASSWORD)) return true;
			else System.out.println("Incorrect password");
		else System.out.println("Unknown username");
		return false;
	}
	
	protected void findTripByLoc() {
		String orig = Menu.promptString("Origin");
		String dest = Menu.promptString("Destination");
		
		System.out.println();
		
		String[] trips = manager.findAvailableTrips(orig, dest);
		
		if (trips.length > 0)
			for (String trip : trips) System.out.println(trip);
		else System.out.println("No " + getString("TRIP") + "s found.");
	}
	
	protected void findTripByDate() {
//		String orig = Menu.promptString("Origin");
//		String dest = Menu.promptString("Destination");
//		
		System.out.println("My dog ate this method");
		
//		String[] trips = manager.findAvailableTrips(orig, dest);
//		boolean tripFound = false;
//		
//		if (trips.length > 0)
//			for (String trip : trips) if (trip.);
//		if(!tripFound) System.out.println("No " + getString("TRIP") + "s found.");
	}
	
	protected abstract void portPrompt();
	
	protected abstract void linePrompt();
	
	protected abstract void tripPrompt();
	
	protected abstract void sectionPrompt();
	
	protected abstract void bookingPrompt();
	
	protected abstract void runTests();
}
