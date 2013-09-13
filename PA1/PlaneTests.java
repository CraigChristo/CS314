package PA1;

import PA1.Manager.ABSmanager;
import PA1.Model.AirSeatClass;

public class PlaneTests {
	private ABSmanager res;
	
	public PlaneTests() { res = new ABSmanager(); }
	
	public PlaneTests(ABSmanager man) {
		res = man;
	}
	
	public void testEnums() {
		res.createAirport("DIA");
		res.createAirport("SFO");
		res.createAirline("FRONT");
		res.createFlight("FRONT","DIA","SFO",2014,15,4,"123");
		res.createFlightSection("FRONT", "123", 6, 5, AirSeatClass.valueOf("first") );
	}
	
	public void run() {
		////Create airports
		res.createAirport("DEN");
		res.createAirport("DFW");
		res.createAirport("LON");
		res.createAirport("JPN");
		res.createAirport("DE"); //invalid
		res.createAirport("DEH");
		res.createAirport("DEN");
		res.createAirport("NCE");
		res.createAirport("TRIord9"); //invalid
		res.createAirport("DEN");
		//Create airlines
		res.createAirline("DELTA");
		res.createAirline("AMER");
		res.createAirline("JET");
		res.createAirline("DELTA");
		res.createAirline("SWEST");
		res.createAirline("AMER");
		res.createAirline("FRONT");
		res.createAirline("FRONTIER"); //invalid
		//Create flights
		res.createFlight("DELTA", "DEN", "LON", 2014, 10, 10, "123");
		res.createFlight("DELTA", "DEN", "DEH", 2013, 8, 8, "567");
		res.createFlight("DELTA", "DEN", "NCE", 2013, 9, 8, "567"); 		//invalid
		res.createFlight("JET", "LON", "DEN", 20013, 5, 7, "123");
		res.createFlight("AMER", "DEN", "LON", 2014, 10, 1, "123");
		res.createFlight("JET", "DEN", "LON", 2014, 6, 10, "786");
		res.createFlight("JET", "DEN", "LON", 2013, 1, 12, "909");
		//Create sections
		res.createFlightSection("JET","123", 2, 2, AirSeatClass.economy);
		res.createFlightSection("JET","123", 1, 3, AirSeatClass.economy);
		res.createFlightSection("JET","123", 2, 3, AirSeatClass.first);
		res.createFlightSection("DELTA","123", 1, 1, AirSeatClass.business);
		res.createFlightSection("DELTA","123", 1, 2, AirSeatClass.economy);
		res.createFlightSection("SWSERTT","123", 5, 5, AirSeatClass.economy);		//invalid
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
