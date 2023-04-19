package src.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.assets.ImageLoader;
import src.main.Consts;
import src.items.interactables.*;
import src.entities.handlers.*;

public class Room {
    // Atributes
    private String name;
    private ArrayList<Interactables> listOfObjects;

    // Position inside the game window
    private int centerX = Consts.WIDTH / 2 - 3 * Consts.SCALED_TILE;
    private int centerY = Consts.HEIGHT / 2 - 3 * Consts.SCALED_TILE;

    // Image for the room background
    private BufferedImage image;

    public Room(String name) {
        this.name = name;
        image = ImageLoader.loadRoom();
    }

    public String getName() {
        return name;
    }

    public <T extends Interactables> void addInteractableObject(T newObject) {
        boolean intersectsWithExistingObject;
        boolean enterPressed = false;
    
        while (!enterPressed) {
            // Update the object's position using wasd keys
            if (KeyHandler.isKeyDown(KeyHandler.KEY_A)) {
                newObject.setX(newObject.getX() - Consts.SCALED_TILE);
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_D)) {
                newObject.setX(newObject.getX() + Consts.SCALED_TILE);
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_W)) {
                newObject.setY(newObject.getY() - Consts.SCALED_TILE);
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_S)) {
                newObject.setY(newObject.getY() + Consts.SCALED_TILE);
            }
    
            // Check if the object intersects with an existing object
            intersectsWithExistingObject = false;
            for (Interactables existingObject : listOfObjects) {
                if (newObject.getBounds().intersects(existingObject.getBounds())) {
                    intersectsWithExistingObject = true;
                    break;
                }
            }
    
            // Exit the loop if Enter key is pressed and the new object is not intersecting with any existing object
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER) && !intersectsWithExistingObject) {
                enterPressed = true;
            }
        }
    
        // Add the new object to the room's list of objects
        listOfObjects.add(newObject);
    }
    
    public void draw(Graphics2D g) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int tileX = centerX + x * Consts.SCALED_TILE;
                int tileY = centerY + y * Consts.SCALED_TILE;
                g.drawImage(image, tileX, tileY, Consts.SCALED_TILE, Consts.SCALED_TILE, null);
            }
        }
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
