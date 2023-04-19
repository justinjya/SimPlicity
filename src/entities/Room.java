package src.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.assets.ImageLoader;
import src.main.Consts;
import src.main.GameTime;
import src.items.interactables.*;
import src.entities.handlers.*;

public class Room {
    // Atributes
    private String name;
    private ArrayList<Interactables> listOfObjects;
    private GameTime time;
    
    // For adding objects
    private boolean addingObject;
    private Interactables unaddedObject;
    private CollisionHandler collisionHandler;

    // Position inside the game window
    private int centerX = Consts.WIDTH / 2 - 3 * Consts.SCALED_TILE;
    private int centerY = Consts.HEIGHT / 2 - 3 * Consts.SCALED_TILE;

    // Image for the room background
    private BufferedImage image;

    public Room(String name, GameTime time) {
        this.name = name;
        this.listOfObjects = new ArrayList<>(); 
        this.time = time;
        this.addingObject = false;
        this.image = ImageLoader.loadRoom();
        testRoom();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Interactables> getListOfObjects() {
        return listOfObjects;
    }

    public boolean isAddingObject() {
        return addingObject;
    }

    public void changeAddingObjectState() {
        this.addingObject = !this.addingObject;
    }

    public void addObject(Interactables newObject) {
        if (!isAddingObject()) {
            unaddedObject = newObject;
            this.collisionHandler = new CollisionHandler(unaddedObject, this);
            changeAddingObjectState();
        }
        else {
            listOfObjects.add(unaddedObject);
            changeAddingObjectState();
            unaddedObject = null;
        }
        // ONLY FOR DEBUGGING
        System.out.println("addObject");
    }

    public void update() {
        if (unaddedObject == null) {
            return;
        }

        if (isAddingObject()) {
            unaddedObject.move(collisionHandler);
            unaddedObject.updateBounds();
        }
    }

    public void draw(Graphics2D g) {
        // Draw the room floor
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int tileX = centerX + x * Consts.SCALED_TILE;
                int tileY = centerY + y * Consts.SCALED_TILE;
                g.drawImage(image, tileX, tileY, Consts.SCALED_TILE, Consts.SCALED_TILE, null);
            }
        }
        
        // Draw objects inside of the room
        for (Interactables object : listOfObjects) {
            if (object instanceof Placeholder) {
                object.draw(g);
            }
            else {
                object.draw(g, object);
            }
            // ONLY FOR DEBUGGING
            // object.drawCollisionBox(g);
        }

        // Draw object being added
        if (isAddingObject()) {
            if (unaddedObject instanceof Placeholder) {
                unaddedObject.draw(g);
            }
            else {
                unaddedObject.draw(g, unaddedObject);
            }
        }
    }
    
    // ONLY FOR DEBUGGING
    public void testRoom() {
        listOfObjects.add(new Bed((Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) - 38, 0, time));
        listOfObjects.add(new Placeholder("1", "2", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 26, 3, 3, Color.CYAN, time));
        listOfObjects.add(new Placeholder("3", "4", 0, (Consts.CENTER_X / 2) + 268, (Consts.CENTER_Y / 2) - 38, 2, 1, Color.ORANGE, time));
        listOfObjects.add(new Placeholder("5", "6", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 282, 1, 1, Color.MAGENTA, time));
        listOfObjects.add(new Placeholder("7", "8", 0, (Consts.CENTER_X / 2) + 332, (Consts.CENTER_Y / 2) + 154, 1, 1, Color.LIGHT_GRAY, time));
    }

    public void drawGrid(Graphics2D g) {
        // Draw a drak gray and gray 6x6 grid
        int centerX = Consts.WIDTH / 2 - 3 * Consts.SCALED_TILE;
        int centerY = Consts.HEIGHT / 2 - 3 * Consts.SCALED_TILE;

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int rectX = centerX + x * Consts.SCALED_TILE;
                int rectY = centerY + y * Consts.SCALED_TILE;
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(rectX, rectY, Consts.SCALED_TILE, Consts.SCALED_TILE);
            }
        }
    }
}
