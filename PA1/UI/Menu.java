package PA1.UI;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

import PA1.Manager.SystemManager;

public class Menu {
	
	static LinkedList<MenuItem[]> history = new LinkedList<MenuItem[]>();
	
    static Scanner sc= new Scanner(System.in);

    static MenuItem[] options = null;
	
	private Menu() {
	}
	
	public static void open(MenuOptions mops) {
		open(mops.main);
	}
	
	public static void open(MenuItem[] arr) { 
		setOptions(arr);
		
        while(true) {
        	System.out.println("--------------------\nMenu\n--------------------");
	        print();
	        System.out.println();
	        System.out.print("  Select an option: ");
	
	        try {
	        	int option = sc.nextInt();
		        sc.nextLine();
		        System.out.println();
		        
		        if (!run(option)) System.out.println(option + " is not a valid option.");
		        System.out.println();
		        
	        } catch (InputMismatchException e) {
	        	sc.nextLine();
	        	System.out.println("\nPlease enter a number\n");
	        }
        }
	}
	
	private static void setOptions(MenuItem[] arr) {
		options = arr;
		history.push(arr);
	}

	private static boolean run(int in) {
		
		if (in == 0) exit();
		
		int count = 1; 
		for (MenuItem i : options) {
			if (count == in) {
				i.run();
				return true;
			}
			count++;
		}
		
		return false;
	}
	
	public static int promptInt(String msg) {
        System.out.print(msg + ": ");
		int input = sc.nextInt();
        sc.nextLine();
        return input;
	}
	
	public static String promptString(String msg) {
        System.out.print(msg + ": ");
		String input = sc.next();
        sc.nextLine();
        return input;
	}
	
	
	private static void print() {
		int count = 1;
        for (MenuItem i : options) {  
            System.out.println("   " + count + ": " + i.toString());
            count++;
        }
        System.out.println("   " + 0 + ": Exit");
	}
	
	public static String getString(String in) {
		return SystemManager.strings.valueOf(in).toString();
	}
	
	public static void goUp() {
		history.pop(); //Remove the current menu
		if (!history.isEmpty()) Menu.open(history.pop());
		else exit();
	}

	public static void exit() {
		sc.close();
		System.exit(0);
	}

}
