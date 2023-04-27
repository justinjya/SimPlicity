package src.world;

import java.awt.event.KeyEvent;

import src.entities.handlers.KeyHandler;
import src.main.Consts;

public class Cursor {
    // Location inside of the world
    private int x;
    private int y;
    private World world;
    private boolean gridMovement = false;

    public Cursor(int x, int y, World world){
        this.x = x;
        this.y = y;
        this.world = world;
    }

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
