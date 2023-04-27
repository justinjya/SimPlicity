package src.world;

import java.awt.event.KeyEvent;

import src.entities.Sim;
import src.entities.handlers.KeyHandler;
import src.main.Consts;
import src.main.UserInterface;

public class Cursor {
    // Location inside of the world
    private int x;
    private int y;
    private World world;
    private boolean gridMovement = false;

    // Constructor
    public Cursor(int x, int y, World world){
        this.x = x;
        this.y = y;
        this.world = world;
    }

    // Getter and setter
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getGridX() {
        return x / Consts.TILE_SIZE;
    }

    public int getGridY() {
        return y / Consts.TILE_SIZE;
    }

    // Others
    public void move(){
        if (KeyHandler.isKeyPressed(KeyEvent.VK_SHIFT)) {
            gridMovement = !gridMovement;
        }
        
        int upperX = (Consts.TILE_SIZE * 64) - 14;
        int upperY = (Consts.TILE_SIZE * 64) - 14;
        int newX = x;
        int newY = y;
        int speed = 5;

        if (gridMovement) {
            upperX = (Consts.TILE_SIZE * 64);
            upperY = (Consts.TILE_SIZE * 64);
            newX -= (newX % Consts.TILE_SIZE);
            newY -= (newY % Consts.TILE_SIZE);

            // keyPressed
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                    newX -= Consts.TILE_SIZE;
                }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                    newX += Consts.TILE_SIZE;
                }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
                    newY -= Consts.TILE_SIZE;
                }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
                    newY += Consts.TILE_SIZE;  
                }
        }
        else {
            // keyDown
            if (KeyHandler.isKeyDown(KeyHandler.KEY_A)) {
                newX -= speed;
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_D)) {
                newX += speed;
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_W)) {
                newY -= speed;
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_S)) {
                newY += speed;  
            }
        }

        if ((newX >= 0 && newX < upperX) && (newY >= 0 && newY < upperY)) {
            x = newX;
            y = newY;
        }
    }

    public void updateUI(UserInterface ui) {
        if (world.isAdding()) {
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                world.addHouse();
                world.changeIsAddingState();

                // ONLY FOR DEBUGGING
                System.out.println("added house");
            }
        }
        
        else {
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                enterHouse(ui);
            }
        }
    }

    private void enterHouse(UserInterface ui) {
        if (world.isLocationOccupied()) {
            ui.changeIsViewingWorldState();

            // ONLY FOR DEBUGGING
            Sim currentSim = ui.getCurrentSim();
            House currentHouse = world.findHouse(getGridX(), getGridY());
            Room currentRoom = world.getHouseRoomWhenEntered(getGridX(), getGridY());

            System.out.println("BERHASIL VISIT");
            System.out.println("house.getX(): " + currentHouse.getX());
            System.out.println(currentRoom.getName());

            ui.setCurrentSim(world.getSim(0));
            ui.getCurrentSim().setCurrentRoom(currentRoom);

            System.out.println(currentSim.getName());
            System.out.println(currentSim.getCurrentRoom().getName());
        }
    }
}
