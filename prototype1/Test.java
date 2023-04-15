package Java.tubes;

import java.util.Scanner;

public class Test
{
    private static Scanner input = new Scanner(System.in);

    public void terminalGridTest()
    {
        for (int i = 0; i < 64; i++)
        {
            for (int j = 0; j < 64; j++)
            {
                System.out.print("| ");
            }
            System.out.println();
            for (int j = 0; j < 64; j++)
            {
                if (j % 2 == 0)
                {
                    System.out.print("+ ");
                }
                else
                {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public static void commands(String command, World world)
    {
        if (command.equals("1"))
        {

        }
        else if (command.equals("2"))
        {
            world.getCurrentSim().showInventory();
        }
        else if (command.equals("3"))
        {
            world.getCurrentHouse().getRoom(world.getCurrentSim().getCurrentRoom()).showRoomObjects();
        }
        else if (command.equals("4"))
        {
            world.getCurrentSim().action(world.getCurrentHouse().getRoom(world.getCurrentSim().getCurrentRoom()));
        }
        else if (command.equals("5"))
        {
            ItemShop.shop(world.getCurrentSim());
        }
    }

    public static void main(String[] args)
    {
        boolean running;
        String name, command;

        // System.out.println("Create a name for your Sim:");
        // name = input.nextLine();
        World world = new World("Justin");

        running = true;
        System.out.println("Starting game...");
        while (running)
        {
            MainMenu.printStatus(world);
            MainMenu.printMenu();
            System.out.println("Input your command:");
            command = input.nextLine();
            commands(command, world);
            Menu.enterToContinue();
        }
        input.close();
    }
}