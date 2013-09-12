package PA1.UI;

import PA1.Manager.SystemManager;

public class MenuOptions {
	
	private SystemManager manager = null;
	
	public MenuOptions(SystemManager m) { manager = m; }
	
	public final MenuItem[] main = {
			
		new MenuItem("Manage " + Menu.getString("PORT")) {
			public void run() {
				Menu.open(port);
			}
		},
		
		new MenuItem("Manage " + Menu.getString("TRIP")) {
			public void run() {
				Menu.open(trip);
			}
		},
		
		new MenuItem("Display system details") {
			public void run() {
				manager.displaySystemDetails();
			}
		}
		
	};
	
	public final MenuItem[] port = {		
			
		new MenuItem("Add a new " + Menu.getString("PORT")) {
			public void run() {
				String name = Menu.promptString("Enter new " + Menu.getString("PORT") + " name");
				manager.createAirport(name);
			}
		},
		
		new MenuItem("Add a new " + Menu.getString("LINE")) {
			public void run() {
				String name = Menu.promptString("Enter new " + Menu.getString("LINE") + " name");
				manager.createAirline(name);
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
			
			new MenuItem("Add a new " + Menu.getString("PORT")) {
				public void run() {
					String name = Menu.promptString("Enter new " + Menu.getString("PORT") + " name");
					manager.createAirport(name);
				}
			},
			
			new MenuItem("Add a new " + Menu.getString("LINE")) {
				public void run() {
					String name = Menu.promptString("Enter new " + Menu.getString("LINE") + " name");
					manager.createAirline(name);
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
