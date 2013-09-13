package PA1.UI;

public class ABSstrings implements SystemStrings {
	enum strings {
		PORT("Port"),
		TRIP("Trip"),
		SECTION("Section"),
		SEAT("Seat"),
		LINE("Line");
	
		private String s;
		private strings(String s) {
			this.s = s;
		}
		public String toString() {
			return s;
		}
	}
	
	public String getString(String in) {
		return strings.valueOf(in).toString();
	}
}