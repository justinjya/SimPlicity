package Java.tubes;

import java.util.Scanner;

public class Menu {
    private static Scanner input = new Scanner(System.in);
    
    public static void enterToContinue()
    {
        System.out.println("Press 'Enter' to continue.");
        input.nextLine();
    }
}
