package src.world;

import java.awt.event.KeyEvent;

import src.entities.Sim;
import src.entities.handlers.KeyHandler;
import src.main.Consts;
import src.main.GamePanel;
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

    private boolean isMovingDiagonally() {
        if ((KeyHandler.isKeyDown(KeyHandler.KEY_W) && KeyHandler.isKeyDown(KeyHandler.KEY_A)) ||
            (KeyHandler.isKeyDown(KeyHandler.KEY_W) && KeyHandler.isKeyDown(KeyHandler.KEY_D)) ||
            (KeyHandler.isKeyDown(KeyHandler.KEY_S) && KeyHandler.isKeyDown(KeyHandler.KEY_A)) ||
            (KeyHandler.isKeyDown(KeyHandler.KEY_S) && KeyHandler.isKeyDown(KeyHandler.KEY_D))) {
            return true;
        }
        return false;
    }

    // Others
    public void move(){
        int upperX = (Consts.TILE_SIZE * 64) - 14;
        int upperY = (Consts.TILE_SIZE * 64) - 14;
        int newX = x;
        int newY = y;
        int speed = 5;
        int initialSpeed = speed;

        if (KeyHandler.isKeyPressed(KeyEvent.VK_SHIFT)) {
            gridMovement = !gridMovement;
        }

        if (gridMovement) {
            upperX = (Consts.TILE_SIZE * 64);
            upperY = (Consts.TILE_SIZE * 64);
            // Move the cursor to the nearest grid
            newX -= (newX % Consts.TILE_SIZE);
            newY -= (newY % Consts.TILE_SIZE);

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
            if (isMovingDiagonally()) {
                speed *= 0.707;
            }
            
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
            speed = initialSpeed;
        }

        if ((newX >= 0 && newX < upperX) && (newY >= 0 && newY < upperY)) {
            x = newX;
            y = newY;
        }
    }

    public void update(GamePanel gp, UserInterface ui) {
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE) && !gp.isCurrentState("Starting a new game")) {
            if (world.isAdding()) world.changeIsAddingState();
            ui.changeIsViewingWorldState();
        }

        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            if (world.isAdding() && !world.isLocationOccupied()) {
                world.addHouse();
                enterHouse(gp, ui);
        
                if (gp.isCurrentState("Starting a new game")) {
                    gp.setState("Playing");
                }
            }
            else if (!world.isAdding() && world.isLocationOccupied()) {
                enterHouse(gp, ui);
            }
        }

    }

    private void enterHouse(GamePanel gp, UserInterface ui) {
        Sim currentSim;
        House currentHouse;
        Room currentRoom;

        try {
            currentSim = ui.getCurrentSim();
            currentHouse = world.getHouse(getGridX(), getGridY());
            currentRoom = currentHouse.getRoomWhenEntered();

            if (gp.isCurrentState("Starting a new game")) {
                currentSim = world.getSim(0);
            }
    
            if (world.isAdding()) {
                int latestSim = world.getListOfSim().size() - 1;
    
                currentSim = world.getSim(latestSim);
                currentSim.changeIsBusyState();
                world.changeIsAddingState();
            }
            
            if (world.isLocationOccupied()) {
                currentSim.setCurrentHouse(currentHouse);
                currentSim.setCurrentRoom(currentRoom);
                ui.setCurrentSim(currentSim);
                
                // ONLY FOR DEBUGGING
                System.out.println(currentSim.getName());
                System.out.println(currentSim.getCurrentRoom().getName());
                System.out.println(currentSim.getCurrentHouse().getName());
            }
        }
        catch (NullPointerException e) {}

        ui.changeIsViewingWorldState();
    }
}
