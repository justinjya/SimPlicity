package src.entities;

import java.util.ArrayList;
import src.entities.handlers.KeyHandler;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import src.assets.ImageLoader;

public class House extends Entity implements Cloneable{

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

        if (isMoving()){
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                newX -= 17;
                System.out.println(newX);
                System.out.println(newY);
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                newX += 17;
                System.out.println(newX);
                System.out.println(newY);
                
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
                newY -= 17;
                System.out.println(newX);
                System.out.println(newY);
                
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
                newY += 17;
                System.out.println(newX);
                System.out.println(newY);
                
            }
            if(!world.isLocationOccupied(newX, newY)){
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
