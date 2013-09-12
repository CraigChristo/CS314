package PA1;

import PA1.Manager.SystemManager;
import PA1.Model.AirSeatClass;

public class Tests {
	private SystemManager res;
	
	public Tests() { res = new SystemManager(); }
	
	public Tests(SystemManager man) {
		res = man;
	}
	
	public void testEnums() {
		res.createPort("DIA");
		res.createPort("SFO");
		res.createLine("FRONT");
		res.createTrip("FRONT","DIA","SFO",2014,15,4,"123");
		res.createSection("FRONT", "123", 6, 5, AirSeatClass.valueOf("first") );
	}
	
	public void run() {
		////Create airports
		res.createPort("DEN");
		res.createPort("DFW");
		res.createPort("LON");
		res.createPort("JPN");
		res.createPort("DE"); //invalid
		res.createPort("DEH");
		res.createPort("DEN");
		res.createPort("NCE");
		res.createPort("TRIord9"); //invalid
		res.createPort("DEN");
		//Create airlines
		res.createLine("DELTA");
		res.createLine("AMER");
		res.createLine("JET");
		res.createLine("DELTA");
		res.createLine("SWEST");
		res.createLine("AMER");
		res.createLine("FRONT");
		res.createLine("FRONTIER"); //invalid
		//Create flights
		res.createTrip("DELTA", "DEN", "LON", 2014, 10, 10, "123");
		res.createTrip("DELTA", "DEN", "DEH", 2013, 8, 8, "567");
		res.createTrip("DELTA", "DEN", "NCE", 2013, 9, 8, "567"); 		//invalid
		res.createTrip("JET", "LON", "DEN", 20013, 5, 7, "123");
		res.createTrip("AMER", "DEN", "LON", 2014, 10, 1, "123");
		res.createTrip("JET", "DEN", "LON", 2014, 6, 10, "786");
		res.createTrip("JET", "DEN", "LON", 2013, 1, 12, "909");
		//Create sections
		res.createSection("JET","123", 2, 2, AirSeatClass.economy);
		res.createSection("JET","123", 1, 3, AirSeatClass.economy);
		res.createSection("JET","123", 2, 3, AirSeatClass.first);
		res.createSection("DELTA","123", 1, 1, AirSeatClass.business);
		res.createSection("DELTA","123", 1, 2, AirSeatClass.economy);
		res.createSection("SWSERTT","123", 5, 5, AirSeatClass.economy);		//invalid
		//Book seats
		res.displaySystemDetails();
		res.findAvailableTrips("DEN", "LON");
		res.bookSeat("DELTA", "123", AirSeatClass.business, 1, 'A');
		res.bookSeat("DELTA", "123", AirSeatClass.economy, 1, 'A');
		res.bookSeat("DELTA", "123", AirSeatClass.economy, 1, 'B');
		res.bookSeat("DELTA", "123", AirSeatClass.business, 1, 'A'); 
		//already booked
		res.displaySystemDetails();
		res.findAvailableTrips("DEN", "LON");
	}
}
