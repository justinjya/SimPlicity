package src.world;

import src.entities.handlers.KeyHandler;

public class Cursor {
    // Location inside of the world
    private int x;
    private int y;
    private World world;

    public Cursor(int x, int y, World world){
        this.x = x;
        this.y = y;
        this.world = world;
    }

    public void move(){
        int newX = x;
        int newY = y;
        
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            newX--;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            newX++;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
            newY--;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            newY++;  
        }

        if ((newX >= 0 && newX < 64) && (newY >= 0 && newY < 64)) {
            x = newX;
            y = newY;
        }

        if (world.isAdding()) {
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                world.addHouse();
            }
        }
        else {
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                if (world.isLocationOccupied()) {
                    // ONLY FOR DEBUGGING
                    System.out.println("BERHASIL VISIT");
                }
            }
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
