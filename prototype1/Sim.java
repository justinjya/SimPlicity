package Java.tubes;

import java.util.Scanner;

public class Sim
{
    private String name;
    private int hunger;
    private int mood;
    private int health;
    private int money;
    private Profession profession;
    private String status;
    private Inventory<Item> inventory;
    private int currentRoom;

    public Sim(String name)
    {
        this.name = name;
        hunger = 80;
        mood = 80;
        health = 80;
        money = 100;
        profession = new Profession();
        status = "Idle";
        inventory = new Inventory<>();
        currentRoom = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getHunger()
    {
        return hunger;
    }

    public int getMood()
    {
        return mood;
    }

    public int getHealth()
    {
        return health;
    }

    public int getMoney()
    {
        return money;
    }

    public Profession getProfession()
    {
        return profession;
    }

    public String getStatus()
    {
        return status;
    }

    public int getCurrentRoom()
    {
        return currentRoom;
    }

    public Inventory<Item> getInventory()
    {
        return inventory;
    }

    public void setHunger(int hunger)
    {
        this.hunger = hunger;
    }

    public void setMood(int mood)
    {
        this.mood = mood;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }    

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setCurrentRoom(int currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public void showInventory()
    {
        System.out.println();
        System.out.println("----- Inventory -----");
        for (Item item : getInventory().getContents())
        {
            if (item instanceof Interactables)
            {
                System.out.println("== Interactables ==");
                System.out.println(item.getName());
                System.out.println(((Interactables) item).getHeight() + " x " + (((Interactables) item).getWidth()));
                System.out.println(((Interactables) item).getPrice());
                System.out.println(((Interactables) item).getAction());
                System.out.println("---------------------");
            }
        }
        for (Item item : getInventory().getContents())
        {
            if (item instanceof RawConsumables)
            {
                System.out.println("== Raw Consumables ==");
                System.out.println(item.getName());
                System.out.println(((RawConsumables) item).getPrice());
                System.out.println(((RawConsumables) item).getHunger());
                System.out.println("---------------------");
            }
        }
        for (Item item : getInventory().getContents())
        {
            if (item instanceof BakedConsumables)
            {
                System.out.println("== Baked Consumables ==");
                System.out.println(item.getName());
                System.out.println(((BakedConsumables) item).getHunger());
                System.out.println("---------------------");
            }
        }
    }

    public void action(Room room)
    {
        Scanner input = new Scanner(System.in);
        int action;
        int number = 1;
        System.out.println();
        System.out.println("Possible actions:");
        for (Interactables object : room.getObjectList())
        {
            System.out.println(number + ". " + object.getAction());
            number++;
        }
        System.out.println("What action do you want to do?");
        action = input.nextInt();
        room.getObject(action - 1).interact(this);
    }
}
