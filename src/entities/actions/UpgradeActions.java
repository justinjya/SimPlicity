package src.entities.actions;

import src.items.interactables.Door;
import src.main.GameTime;
import src.world.Room;

public class UpgradeActions {
    public static void addRoom(Room room, String name, GameTime time) {
        Room thisRoom = room;
        
        Thread addNewRoomThread = new Thread() {
            @Override
            public void run() {
                Room newRoom = new Room(name, time);
                Door newDoor = new Door(newRoom, time);
                
                room.addObject(newDoor);
                while (true) {
                    synchronized (thisRoom) {
                        if (!thisRoom.isEditingRoom()) {
                            break;
                        }
                    }
                }
                newRoom.getListOfObjects().add(new Door(newDoor, thisRoom, time));
            }
        };

        addNewRoomThread.start();
    }

    public static void buyItems() {
        // CODE HERE
    }
}
