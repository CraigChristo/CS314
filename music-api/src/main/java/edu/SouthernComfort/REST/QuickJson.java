package edu.SouthernComfort.REST;

import java.util.Collection;

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
	
	public static <T> JsonArray build(Collection<T> c) {
		JsonArrayBuilder b = Json.createArrayBuilder();		
		
		for (T t : c)
			b.add(t.toString());
			
		return b.build();
	}
	
	public static JsonArray buildFromJson(Collection<? extends JsonValue> c) {
		JsonArrayBuilder b = Json.createArrayBuilder();		
		
		for (JsonValue v : c)
			b.add(v);
			
		return b.build();
	}
}
