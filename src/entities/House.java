package src.entities;

import java.util.ArrayList;
import src.entities.handlers.KeyHandler;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import src.assets.ImageLoader;

public class House extends Entity {

    // Owner of house
    private Sim owner;

    // List of room
    private ArrayList<Room> listOfRoom;

    // Image of house
    private BufferedImage image;

    // Constructor
    public House(int x, int y, Sim owner) {
        super(x,y,1,1);
        this.owner = owner;
        this.listOfRoom = new ArrayList<>();
        this.image = ImageLoader.loadHouse();
    }
    
    public House(int x, int y) {
        super(x,y,1,1);
        this.listOfRoom = new ArrayList<>();
        this.image = ImageLoader.loadHouse();
    }

    // Method to move the house
    public void move(World world){
        int newX = getX();
        int newY = getY();

        if(isMoving()){
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                newX -= 1; 
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                newX += 1; 
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
                newY -= 1; 
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
                newY += 1; 
            }
            if(!world.isLocationOccupied(this)){
                setX(newX);
                setY(newY);
            }
        }
    }

    public Sim getOwner(){
        return owner;
    }

    public BufferedImage getImage(){
        return image;
    }
}
