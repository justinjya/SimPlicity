package src.world;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.*;

import src.assets.ImageLoader;
import src.main.Consts;
import src.main.GameTime;

public class World {
    // Atributes
    private int[][] map = new int[64][64];
    private ArrayList<House> listOfHouse;
    private GameTime time;
    
    // Images of the world
    private BufferedImage[] images;
    
    // Cursor position
    private Cursor cursor;
    
    // State of the world (is adding house or selecting)
    private boolean isAdding = false;
    
    // Viewable world inside of the window
    private int viewableGrid = Consts.TILE_SIZE * 32;
    private int centerX = (Consts.WIDTH / 2) - (viewableGrid / 2);
    private int centerY = (Consts.HEIGHT / 2) - (viewableGrid / 2);
    private int lowerBoundsX;
    private int upperBoundsX;
    private int lowerBoundsY;
    private int upperBoundsY;

    // Constructor 
    public World(GameTime gameTime) {
        listOfHouse = new ArrayList<>();
        this.images = ImageLoader.loadWorld();

        for (int y = 0 ; y < 64 ; y++){
            for (int x = 0 ; x < 64 ; x++){
                map[y][x] = 0;
            }
        }

        this.cursor = new Cursor(0, 0, this);
        this.time = gameTime;
        
        // ONLY FOR DEBUGGING
        listOfHouse.add(new House(1, 1, this));
    }

    // Getter and setter
    public int getMap(int x, int y) {
        return map[y][x];
    }

    public boolean isAdding() {
        return isAdding;
    }

    public ArrayList<House> getListOfHouse() {
        return listOfHouse;
    }

    public void setMap(int x, int y, int value) {
        map[y][x] = value;
    }

    public void changeIsAddingState() {
        this.isAdding = !this.isAdding;
    }

    // Method to check the whether the location of new house is being occupied
    public boolean isLocationOccupied() {
        boolean isOccupied = false; // initialize the status of occupation in newHouse location

        if (getMap(cursor.getGridX(), cursor.getGridY()) == 1) {
            isOccupied = true;
        }
        return isOccupied;
    }

    public House findHouse(int x, int y){
        for (House house : getListOfHouse()){
            if (house.getX() == x && house.getY() == y){
                return house;
            }
        }
        return null;
    }

    // Method to add house
    public void addHouse() {
        if (!isLocationOccupied()) {
            listOfHouse.add(new House(cursor.getGridX(), cursor.getGridY(), this));
            setMap(cursor.getGridX(), cursor.getGridY(), 1);
        }
    }

    public void update() {
        System.out.println("cursorX: " + cursor.getX() + " cursorY: " + cursor.getY());
        cursor.move();
    }

    public void draw(Graphics2D g) {
        // Draw the world in quarters with the size of each quarter of 32 x 32
        drawWorld(g);
        
        drawCursor(g);

        drawArrows(g);
    }

    private int getCursorInQuarter() {
        // First Quarter
        int lowerCoords = 0 * Consts.TILE_SIZE;
        int middleCoords = 32 * Consts.TILE_SIZE;
        int upperCoords = 64 * Consts.TILE_SIZE;

        if ((cursor.getX() >= lowerCoords && cursor.getX() < middleCoords) &&
            (cursor.getY() >= lowerCoords && cursor.getY() < middleCoords)) {
            return 1;
        }
        if ((cursor.getX() >= middleCoords && cursor.getX() < upperCoords) &&
            (cursor.getY() >= 0 * lowerCoords && cursor.getY() < middleCoords)) {
            return 2;
        }
        if ((cursor.getX() >= middleCoords && cursor.getX() < upperCoords) &&
            (cursor.getY() >= middleCoords && cursor.getY() < upperCoords)) {
            return 3;
        }
        if ((cursor.getX() >= lowerCoords && cursor.getX() < middleCoords) &&
            (cursor.getY() >= middleCoords && cursor.getY() < upperCoords)) {
            return 4;
        }
        return 0;
    }

    private void setUpperAndLowerBounds() {
        if (getCursorInQuarter() == 1) {
            lowerBoundsX = 0;
            upperBoundsX = 32;
            lowerBoundsY = 0;
            upperBoundsY = 32;
        }
        else if (getCursorInQuarter() == 2) {
            lowerBoundsX = 32;
            upperBoundsX = 64;
            lowerBoundsY = 0;
            upperBoundsY = 32;
        }
        else if (getCursorInQuarter() == 3) {
            lowerBoundsX = 32;
            upperBoundsX = 64;
            lowerBoundsY = 32;
            upperBoundsY = 64;
        }
        else if (getCursorInQuarter() == 4) {
            lowerBoundsX = 32;
            upperBoundsX = 64;
            lowerBoundsY = 0;
            upperBoundsY = 32;
        }
    }

    private void drawCursor(Graphics2D g) {
        if (isLocationOccupied()) {
            return;
        }

        int tileX = centerX + (cursor.getX() % viewableGrid);
        int tileY = centerY + (cursor.getY() % viewableGrid);

        if (getCursorInQuarter() == 1 || getCursorInQuarter() == 3) {
            tileX = centerX + (cursor.getX() % (viewableGrid - 14));
            tileY = centerY + (cursor.getY() % (viewableGrid - 14));
        }

        if (isAdding) {
            g.drawImage(images[3], tileX, tileY, null);
        }
        else {
            g.drawImage(images[2], tileX, tileY, null);
        }
    }

    private void drawHouses(Graphics2D g, int x, int y, int tileX, int tileY) {
        if (getMap(x, y) == 1) {
            g.drawImage(images[1], tileX, tileY, null); 
        }

        if (isAdding) {
            if (isLocationOccupied() && x == cursor.getX() / Consts.TILE_SIZE && y == cursor.getY() / Consts.TILE_SIZE) {
                g.drawImage(images[5], tileX, tileY, null);
            }
        }
        else {
            if (isLocationOccupied() && x == cursor.getX() / Consts.TILE_SIZE && y == cursor.getY() / Consts.TILE_SIZE) {
                g.drawImage(images[4], tileX, tileY, null);
            }
        }
    }

    private void drawWorld(Graphics2D g) {
        setUpperAndLowerBounds();

        for (int y = lowerBoundsY; y < upperBoundsY; y++) {
            for (int x = lowerBoundsX; x < upperBoundsX; x++) {
                int tileX = centerX + (x * Consts.TILE_SIZE) % viewableGrid;
                int tileY = centerY + (y * Consts.TILE_SIZE) % viewableGrid;
                g.drawImage(images[0], tileX, tileY, null);

                drawHouses(g, x, y, tileX, tileY);
            }
        }
    }

    private void drawArrows(Graphics2D g) {
        // Arrows
        if (getCursorInQuarter() == 1){
            g.drawImage(images[8], centerX + (15 * Consts.TILE_SIZE + 8), centerY + (30 * Consts.TILE_SIZE), null);
            g.drawImage(images[9], centerX + (30 * Consts.TILE_SIZE), centerY + (15 * Consts.TILE_SIZE + 8), null);
        }
        if (getCursorInQuarter() == 2){
            g.drawImage(images[7], centerX + Consts.TILE_SIZE, centerY + (15 * Consts.TILE_SIZE + 8), null);
            g.drawImage(images[8], centerX + (15 * Consts.TILE_SIZE + 8), centerY + (30 * Consts.TILE_SIZE), null);
        }
        if (getCursorInQuarter() == 3){
            g.drawImage(images[6], centerX + (15 * Consts.TILE_SIZE + 8), centerY + Consts.TILE_SIZE, null);
            g.drawImage(images[7], centerX + Consts.TILE_SIZE, centerY + (15 * Consts.TILE_SIZE + 8), null);
        }
        if (getCursorInQuarter() == 4){
            g.drawImage(images[6], centerX + (15 * Consts.TILE_SIZE + 8), centerY + Consts.TILE_SIZE, null);
            g.drawImage(images[9], centerX + (30 * Consts.TILE_SIZE), centerY + (15 * Consts.TILE_SIZE + 8), null);
        }
    }
}
