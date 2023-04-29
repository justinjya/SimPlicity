package src.entities.actions;

import src.entities.interactables.Door;
import src.main.GameTime;
import src.world.Room;

public class UpgradeActions {
    public static void addRoom(Room room, String name) {
        Room thisRoom = room;
        
        Thread addNewRoomThread = new Thread() {
            @Override
            public void run() {
                Room newRoom = new Room(name);
                Door newDoor = new Door(newRoom);
                
                room.addObject(newDoor);
                while (true) {
                    synchronized (thisRoom) {
                        if (!thisRoom.isEditingRoom()) {
                            break;
                        }
                    }
                }
                newRoom.getListOfObjects().add(new Door(newDoor, thisRoom));
            }
        };

        addNewRoomThread.start();
    }

    public static void buyItems() {
        // CODE HERE
    }
}
