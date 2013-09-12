package PA1;

import PA1.Manager.SystemManager;
import PA1.UI.*;

public class ClientProg {
public static void main(String args[]){

	SystemManager res = new SystemManager();
	Menu.open(new MenuOptions(res));
}
}