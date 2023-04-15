package Java.tubes;

import java.util.ArrayList;

public class Room {
    private String name;
    private int x;
    private int y;
    ArrayList<Interactables> objectList;

    public Room(String name, int x, int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        objectList = new ArrayList<Interactables>();
    }

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public ArrayList<Interactables> getObjectList()
    {
        return objectList;
    }

    public Interactables getObject(int index)
    {
        return objectList.get(index);
    }

    public void addObject(Interactables object)
    {
        objectList.add(object);
    }

    public static Room setStarterRoom()
    {
        Room starterRoom = new Room("Starter Room", 6, 6);
        starterRoom.addObject(new Interactables(0)); // Kasur Single
        starterRoom.addObject(new Interactables(3)); // Toilet
        starterRoom.addObject(new Interactables(4)); // Kompor Gas
        starterRoom.addObject(new Interactables(6)); // Meja dan Kursi
        starterRoom.addObject(new Interactables(7)); // Jam
        return starterRoom;
    }

    public void showRoomObjects()
    {
        System.out.println();
        System.out.println("--- Objects inside of this room ---");
        for (Interactables object : objectList)
        {
            System.out.println(object.getName());
        }
    }
}
