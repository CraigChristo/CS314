package PA1.UI;


public class CBSstrings implements SystemStrings {
	public static enum strings {
		PORT("Port"),
		TRIP("Cruise"),
		SECTION("Cabin Section"),
		SEAT("Cabin"),
		LINE("Cruise Line");
	
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