package src.world;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.*;

import src.assets.ImageLoader;
import src.entities.handlers.KeyHandler;
import src.entities.interactables.Door;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.ui.UserInterface;

public class World {
    // Attributes
    private int[][] map = new int[64][64];
    private ArrayList<Sim> listOfSim;
    private ArrayList<House> listOfHouse;
    private Room unaddedRoom = null;
    
    // State of the world (is adding a house or selecting a house to visit)
    private boolean isAdding = true;

    // Images of the world
    private BufferedImage[] images;
    
    // Cursor position
    private Cursor cursor;
    
    // Viewable world inside of the window (32 x 32 grid)
    private int viewableGrid = Consts.TILE_SIZE * 32;
    private int centerX = (Consts.WIDTH / 2) - (viewableGrid / 2);
    private int centerY = (Consts.HEIGHT / 2) - (viewableGrid / 2);

    // Bounds for each quarter
    private int lowerBoundsX, upperBoundsX;
    private int lowerBoundsY, upperBoundsY;

    // Constructor 
    public World(Sim sim, Room room) {
        // Attributes
        listOfSim = new ArrayList<>();
        listOfHouse = new ArrayList<>();

        // Load the images of the world
        this.images = ImageLoader.loadWorld();

        // Load the initial state of the map
        for (int y = 0 ; y < 64 ; y++) {
            for (int x = 0 ; x < 64 ; x++) {
                map[y][x] = 0;
            }
        }

        // Initialize the cursor in the center of the grid
        this.cursor = new Cursor(Consts.TILE_SIZE * 16, Consts.TILE_SIZE * 16, this);

        listOfSim.add(sim);
        unaddedRoom = room;
    }

    // Getter and setter
    public int getMap(int x, int y) {
        return map[y][x];
    }
    
    public ArrayList<Sim> getListOfSim() {
        return listOfSim;
    }

    public Sim getSim(int index) {
        return listOfSim.get(index);
    }

    public ArrayList<House> getListOfHouse() {
        return listOfHouse;
    }

    public House getHouse(int x, int y){
        for (House house : getListOfHouse()){
            if (house.getX() == x && house.getY() == y){
                return house;
            }
        }
        return null;
    }

    public boolean isAdding() {
        return isAdding;
    }

    public Room getUnaddedRoom() {
        return unaddedRoom;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setMap(int x, int y, int value) {
        map[y][x] = value;
    }

    public void addSim(Sim sim) {
        listOfSim.add(sim);
    }

    public void addHouse() {
        int x = cursor.getGridX();
        int y = cursor.getGridY();

        Sim newSim = getSim(listOfSim.size() - 1);
        Room newRoom = unaddedRoom;
        newRoom.getListOfObjects().add(new Door(null));
        House newHouse = new House(x, y, this, newSim, newRoom);
        setMap(x, y, 1);
        unaddedRoom = null;

        listOfHouse.add(newHouse);
    }

    public void changeIsAddingState() {
        this.isAdding = !this.isAdding;
    }

    // Others
    public void update(UserInterface ui) {
        if (ui.isViewingWorld()) {
            cursor.move();
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            cursor.enterPressed(ui);
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
            ui.changeIsViewingWorldState();
        }
    }

    public void draw(Graphics2D g) {
        // Draw the world in quarters with the size of each quarter of 32 x 32
        drawWorld(g);
        
        drawHouses(g);

        drawCursor(g);

        drawArrows(g);
    }

    private int getCursorInQuarter() {
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
            lowerBoundsX = 0; upperBoundsX = 32;
            lowerBoundsY = 0; upperBoundsY = 32;
        }
        else if (getCursorInQuarter() == 2) {
            lowerBoundsX = 32; upperBoundsX = 64;
            lowerBoundsY = 0; upperBoundsY = 32;
        }
        else if (getCursorInQuarter() == 3) {
            lowerBoundsX = 32; upperBoundsX = 64;
            lowerBoundsY = 32; upperBoundsY = 64;
        }
        else if (getCursorInQuarter() == 4) {
            lowerBoundsX = 32; upperBoundsX = 64;
            lowerBoundsY = 0; upperBoundsY = 32;
        }
    }

    private void drawWorld(Graphics2D g) {
        setUpperAndLowerBounds();

        for (int y = lowerBoundsY; y < upperBoundsY; y++) {
            for (int x = lowerBoundsX; x < upperBoundsX; x++) {
                int tileX = centerX + (x * Consts.TILE_SIZE) % viewableGrid;
                int tileY = centerY + (y * Consts.TILE_SIZE) % viewableGrid;
                g.drawImage(images[0], tileX, tileY, null);
            }
        }
    }

    private void drawHouses(Graphics2D g) {
        Font font;
        g.setColor(Color.WHITE);

        font = new Font("Inter", Font.PLAIN, 9);

        g.setFont(font);

        for (int y = lowerBoundsY; y < upperBoundsY; y++) {
            for (int x = lowerBoundsX; x < upperBoundsX; x++) {
                int tileX = centerX + (x * Consts.TILE_SIZE) % viewableGrid;
                int tileY = centerY + (y * Consts.TILE_SIZE) % viewableGrid;

                if (getMap(x, y) == 1) {
                    g.drawImage(images[1], tileX, tileY, null);
                }
                
                if (cursor.isAboveHouse() && x == cursor.getGridX() && y == cursor.getGridY()) {
                    if (isAdding) {
                        g.drawImage(images[5], tileX, tileY, null);
                    }
                    else {        
                        g.drawImage(images[4], tileX, tileY, null);
                        g.drawString(getHouse(x, y).getName(), tileX - 5, tileY + 26);
                    }
                }
            }
        }
    }

    private void drawCursor(Graphics2D g) {
        if (cursor.isAboveHouse()) {
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
