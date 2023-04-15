package Java.tubes;

import java.util.ArrayList;

public class House {
    private Sim owner;
    private ArrayList<Room> roomList;

    public House(Sim owner)
    {
        this.owner = owner;
        roomList = new ArrayList<>();
        roomList.add(Room.setStarterRoom());
    }

    public Sim getOwner()
    {
        return owner;
    }

    public ArrayList<Room> getRoomList()
    {
        return roomList;
    }

    public Room getRoom(int index)
    {
        return roomList.get(index);
    }

    public void showRoomList()
    {
        System.out.println();
        System.out.println(" --- Room List ---");
        for (Room room : roomList)
        {
            System.out.println(room.getName());
        }
    }
}
