package src.entities.sim.actions;

import src.entities.interactables.Door;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.items.foods.RawFood;
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

    public static void buyRawFood(Inventory inventory, RawFood rawfood, Sim sim, GameTime time) {
        Thread buying = new Thread(){
            @Override
            public void run() {
                int upperBound = 30;
                int lowerBound = 1;
                int deliveryTime = (int) Math.random()*(upperBound-lowerBound) + lowerBound;
                time.startDecrementTimeRemaining(deliveryTime);
                if(sim.getMoney() >= rawfood.getPrice()){
                    // must put the code to add to inventory
                    inventory.addItem(rawfood);
                    sim.setMoney(sim.getMoney() - rawfood.getPrice());
                }
            }
        };
        buying.start();
    }
}
