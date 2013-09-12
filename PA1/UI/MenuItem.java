package PA1.UI;

public abstract class MenuItem {
	private String itemString;
	
	public String toString() {
		return itemString;
	}
	
	public abstract void run();	
		
	public MenuItem(String itemString) {
		this.itemString = itemString;
	}
}