package src.entities;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import java.awt.*;

public class World {
    // List of house
    private ArrayList<House> listOfHouse;
    
    // Image of world background
    private BufferedImage image;

    // Constructor 
    public World() {
        listOfHouse = new ArrayList<>();
        this.image = ImageLoader.loadGrass();
    }

    // Method to add house
    public void addHouse(House newHouse) {
        // The location of the new house must be unoccupied for it to be added
        if(!isLocationOccupied(newHouse)){
            listOfHouse.add(newHouse);
        }
    }

    // Method to check the whether the location of new house is being occupied
    public boolean isLocationOccupied(House newHouse) {
        boolean isOccupied = false; // initialize the status of occupation in newHouse location

        // Iterate over listOfHouse to check for each house
        for(House house : listOfHouse){

            // Check whether the location of the new house has been occupied
            if(newHouse.getX() >= house.getX() && newHouse.getX() <= house.getX() + 8 // size of the house is 8x8
            && newHouse.getY() >= house.getY() && newHouse.getY() <= house.getY() + 8){
                isOccupied = true;
                break;
            }
        }
        return isOccupied;
    }

    // Method to draw the world
    public void draw(Graphics2D g){
        // center position
        int centerX = 135;
        int centerY = 25;

        // draw the grass
        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                int tileX = centerX + (x * 8);
                int tileY = centerY + (y * 8) ;
                g.drawImage(image, tileX, tileY, 8, 8, null);
            }
        }
    }

    // Method to draw the house
    public void drawHouse(Graphics2D g, House newHouse){
        g.drawImage(newHouse.getImage(), newHouse.getX(), newHouse.getY(), 8, 8, null);
    }
}
