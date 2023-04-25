package src.entities;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.main.GameTime;

import java.awt.*;

public class World {
    // List of house
    private ArrayList<House> listOfHouse;
    
    // Image of grass
    private BufferedImage image;

    // Image of addedHouse
    private BufferedImage addedHouseImage;

    // Image of cursor
    private BufferedImage cursorImage;

    // Matrix as a map
    private int[][] map = new int[64][64];

    // State of the world (is adding house or selecting)
    private boolean isAdding = true;

    // Game time
    private GameTime gameTime;

    // Constructor 
    public World(GameTime gameTime) {
        listOfHouse = new ArrayList<>();
        this.image = ImageLoader.loadGrass();
        this.addedHouseImage = ImageLoader.loadAddedHouse();
        this.cursorImage = ImageLoader.loadCursor();
        for (int i = 0 ; i < 64 ; i++){
            for (int j = 0 ; j < 64 ; j++){
                map[i][j] = 0;
            }
        }
        this.gameTime = gameTime;
    }

    // Method to add house
    public void addHouse(House newHouse) {
        // The location of the new house must be unoccupied for it to be added
        if(isAdding){
            if(!isLocationOccupied(newHouse.getX(), newHouse.getY())){
                try {
                    listOfHouse.add((House) newHouse.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                
                setMap(newHouse.getY()/17, newHouse.getX()/17, 1);
                isAdding = false;
            }
        }
        
    }

    // Method to check the whether the location of new house is being occupied
    public boolean isLocationOccupied(int newX, int newY) {
        boolean isOccupied = false; // initialize the status of occupation in newHouse location

        // Iterate over listOfHouse to check for each house
        for(House house : listOfHouse){
            // Check whether the location of the new house has been occupied
            if(newX >= house.getX() && newX < house.getX() + 17 // size of the house is 17x17
            && newY >= house.getY() && newY < house.getY() + 17){
                isOccupied = true;
                break;
            }
        }
        return isOccupied;
    }

    public House findHouse(int x, int y){
        for (House house : getListOfHouse()){
            if(house.getX() == x && house.getY() == y){
                return house;
            }
        }
        return null;
    }

    // Method to draw the world
    public void draw(Graphics2D g, Cursor cursor, House house){
        // center position
        int centerX = 0;
        int centerY = 0;

        if(isAdding){
            if((house.getY() >= 0 && house.getY() < 544
            && house.getX() >= 0 && house.getX() < 544)){
                for (int y = 0; y < 32; y++) {
                    for (int x = 0; x < 32; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }
            else if((house.getY() >= 544 && house.getY() < 1088
            && house.getX() >= 0 && house.getX() < 544)){
                // draw the world based on map condition
                for (int y = 32; y < 64; y++) {
                    for (int x = 0; x < 32; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }

            else if((house.getY() >= 0 && house.getY() < 544
            && house.getX() >= 544 && house.getX() < 1088)){
                // draw the world based on map condition
                for (int y = 0; y < 32; y++) {
                    for (int x = 32; x < 64; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }

            else if((house.getY() >= 544 && house.getY() < 1088
            && house.getX() >= 544 && house.getX() < 1088)){
                // draw the world based on map condition
                for (int y = 32; y < 64; y++) {
                    for (int x = 32; x < 64; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }
        }
        else{
            if((cursor.getX() >= 0 && cursor.getX() < 544 
            && cursor.getY() >= 0 && cursor.getY() < 544)){
                for (int y = 0; y < 32; y++) {
                    for (int x = 0; x < 32; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }
            else if((cursor.getX() >= 0 && cursor.getX() < 544 
            && cursor.getY() >= 544 && cursor.getY() < 1088)){
                // draw the world based on map condition
                for (int y = 32; y < 64; y++) {
                    for (int x = 0; x < 32; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }

            else if((cursor.getX() >= 544 && cursor.getX() < 1088 
            && cursor.getY() >= 0 && cursor.getY() < 544)){
                // draw the world based on map condition
                for (int y = 0; y < 32; y++) {
                    for (int x = 32; x < 64; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }

            else if((cursor.getX() >= 544 && cursor.getX() < 1088 
            && cursor.getY() >= 544 && cursor.getY() < 1088)){
                // draw the world based on map condition
                for (int y = 32; y < 64; y++) {
                    for (int x = 32; x < 64; x++) {
                        int tileX = centerX + (x * 17);
                        int tileY = centerY + (y * 17);
                        if(getMap(y, x) == 0){
                            g.drawImage(image, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        else if(getMap(y, x) == 1){
                            g.drawImage(addedHouseImage, tileX % 544, tileY % 544, 17, 17, null);
                        }
                        
                    }
                }
            }
        }
    }

    // Method to draw the house
    public void drawHouse(Graphics2D g, House newHouse){
        g.drawImage(newHouse.getImage(), newHouse.getX() % 544, newHouse.getY() % 544, 17, 17, null);
    }

    // Method to draw the cursor
    public void drawCursor(Graphics2D g, Cursor cursor){
        g.drawImage(cursorImage, cursor.getX() % 544, cursor.getY() % 544, 17, 17, null);
    }

    // Getter and setter
    public int getMap(int i, int j){
        return map[i][j];
    }

    public void setMap(int i, int j, int value){
        map[i][j] = value;
    }

    public boolean getIsAdding(){
        return isAdding;
    }

    public BufferedImage getCursorImage(){
        return cursorImage;
    }

    public ArrayList<House> getListOfHouse(){
        return listOfHouse;
    }

    public GameTime getTime(){
        return gameTime;
    }
    
    public void setTime(GameTime gameTime){
        this.gameTime = gameTime;
    }
}
