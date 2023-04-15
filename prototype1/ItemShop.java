package Java.tubes;

import java.util.Scanner;

public class ItemShop extends Menu{
    private static Scanner input = new Scanner(System.in);

    public static void printShopMenu()
    {
        System.out.println();
        System.out.println("--- Shop ---");
        System.out.println("Menu");
        System.out.println("1. Interactables");
        System.out.println("2. Consumables");
        System.out.println("What type of item do you want to buy?");
    }

    public static int printShop(String type)
    {   
        int listLength = 0;
        int buyIndex;

        if (type.equals("Interactables"))
        {
            listLength = printShopInteractables();
        }
        else if (type.equals("Consumables"))
        {
            listLength = printShopConsumables();
        }

        System.out.println("What item do you want to buy?");
        buyIndex = input.nextInt();
        input.nextLine();
        return (buyIndex - 1);
    }

    public static int printShopInteractables()
    {
        String[] interactables = Interactables.getNames();
        int[] heights = Interactables.getHeights();
        int[] widths = Interactables.getWidths();
        String[] actions = Interactables.getActions();
        int[] prices = Interactables.getPrices();

        System.out.println();
        System.out.println("== Interactables ==");
        for (int i = 1; i <= interactables.length; i++)
        {
            System.out.println(i + ". " + interactables[i - 1] + ": " +
            heights[i - 1] + " x " + widths[i - 1] + ": " +
            actions[i - 1] + ": " + prices[i - 1]);
        }
        return interactables.length;
    }

    public static int printShopConsumables()
    {
        String[] consumables = RawConsumables.getNames();
        int[] hungers = RawConsumables.getHungers();
        int[] prices = RawConsumables.getPrices();

        System.out.println();
        System.out.println("== Consumables ==");
        for (int i = 1; i <= consumables.length; i++)
        {
            System.out.println(i + ". " + consumables[i - 1] + ": " + hungers[i -1] + ": " + prices[i - 1]);
        }
        return consumables.length;
    }

    public static void shop(Sim sim)
    {
        String command;
        int buyIndex;

        printShopMenu();
        command = input.nextLine();
        if (command.equals("1"))
        {
            buyIndex = printShop("Interactables");
            Interactables interactables = new Interactables(buyIndex);
            sim.getInventory().addItem(interactables);
            sim.setMoney(sim.getMoney() - interactables.getPrice());
        }
        else if (command.equals("2"))
        {
            buyIndex = printShop("Consumables");
            RawConsumables rawConsumables = new RawConsumables(buyIndex);
            sim.getInventory().addItem(rawConsumables);
            sim.setMoney(sim.getMoney() - rawConsumables.getPrice());
        }
        System.out.println();
        System.out.println("Item succesfully bought.");
        enterToContinue();
    }
}
