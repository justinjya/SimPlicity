package Java.tubes;

public class MainMenu extends Menu{
    public static void printStatus(World world)
    {
        System.out.println();
        System.out.println("--- " + world.getCurrentSim().getName() + " ---");
        System.out.println("Uang: " + world.getCurrentSim().getMoney());
        System.out.println("Kekenyangan: " + world.getCurrentSim().getHunger());
        System.out.println("Mood: " + world.getCurrentSim().getMood());
        System.out.println("Kesehatan: " + world.getCurrentSim().getHealth());
        System.out.println("Profession: " + world.getCurrentSim().getProfession().getName());
        System.out.println("Status: " + world.getCurrentSim().getStatus());
        System.out.println("Current room: " + world.getCurrentHouse().getRoom(world.getCurrentSim().getCurrentRoom()).getName());
    }

    public static void printMenu()
    {
        System.out.println();
        System.out.println("--- Menu ---");
        System.out.println("1. Move");
        System.out.println("2. View inventory");
        System.out.println("3. List of objects");
        System.out.println("4. Action");
        System.out.println("5. Shop");
        System.out.println("6. Add Sim");
        System.out.println("7. Change Sim");
    }
}
