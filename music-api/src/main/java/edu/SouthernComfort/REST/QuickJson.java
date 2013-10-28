package edu.SouthernComfort.REST;

import javax.json.*;

public class QuickJson {

	private QuickJson() {}
	
	public static JsonObject build(String[][] coll) {
		JsonObjectBuilder b = Json.createObjectBuilder();
		
		for (String[] pair : coll)
			b.add(pair[0], pair[1]);
		
		return b.build();
	}
	
	public static JsonObject build(String string) {
		JsonObjectBuilder b = Json.createObjectBuilder();
		
		b.addNull(string);
		
		return b.build();
	}
}
