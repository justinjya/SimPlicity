package src.entities.sim.actions;

import java.util.ArrayList;

import src.main.UserInterface;
import src.main.KeyHandler;
import src.main.GameTime;
import src.main.Consts;
import src.world.Room;
import src.entities.sim.Sim;
import src.items.foods.RawFood;
import src.entities.interactables.Door;
import src.entities.interactables.Interactables;

public class UpgradeActions {
    public static void addRoom(String name) {
        Sim currentSim = UserInterface.getCurrentSim();
        Room currentRoom = currentSim.getCurrentRoom();

        Thread addNewRoomThread = new Thread() {
            @Override
            public void run() {
                Room newRoom = new Room(name);
                Door newDoor = new Door(currentRoom);
                
                currentRoom.addObject(newDoor);
                while (true) {
                    synchronized (currentRoom) {
                        if (!currentRoom.isEditingRoom()) {
                            break;
                        }
                    }
                }

                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_MINUTE * 18);

                ArrayList<Interactables> listOfObjects = currentRoom.getListOfObjects();
                if (listOfObjects.contains(newDoor)) {
                    currentSim.setMoney(currentSim.getMoney() - newDoor.getPrice());
                }

                while (t.isAlive()) continue;

                newDoor.setLeadsIntoRoom(newRoom);
                newRoom.getListOfObjects().add(new Door(newDoor, currentRoom));
            }
        };
        addNewRoomThread.start();
    }

    public static void buyRawFood(Sim sim) {
        Thread buying = new Thread(){
            @Override
            public void run() {
                // choose the raw food to be bought
                RawFood rawFood = null;
                if (KeyHandler.isKeyPressed(KeyHandler.KEY_1)) {
                    rawFood = new RawFood(0);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_2)) {
                    rawFood = new RawFood(1);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_3)) {
                    rawFood = new RawFood(2);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_4)) {
                    rawFood = new RawFood(3);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_5)) {
                    rawFood = new RawFood(4);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_6)) {
                    rawFood = new RawFood(5);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_7)) {
                    rawFood = new RawFood(6);
                }
                else if(KeyHandler.isKeyPressed(KeyHandler.KEY_8)) {
                    rawFood = new RawFood(7);
                }
                int upperBound = 30;
                int lowerBound = 1;
                int deliveryTime = (int) Math.random()*(upperBound-lowerBound) + lowerBound;
                GameTime.startDecrementTimeRemaining(deliveryTime);
                if(sim.getMoney() >= rawFood.getPrice()){
                    // must put the code to add to inventory
                    sim.getInventory().addItem(rawFood);
                    sim.setMoney(sim.getMoney() - rawFood.getPrice());
                }
            }
        };
        buying.start();
    }
}
