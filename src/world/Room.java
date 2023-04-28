package src.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.assets.ImageLoader;
import src.main.Consts;
import src.main.GameTime;
import src.entities.Interactables;
import src.entities.Sim;
import src.items.interactables.*;
import src.entities.handlers.*;

public class Room {
    // Atributes
    private String name;
    private ArrayList<Interactables> listOfObjects;
    private ArrayList<Sim> listOfSims;
    private GameTime time;
    
    // For adding, editing, and removing objects
    private boolean isEditingRoom;
    private CollisionHandler collisionHandler;
    private Interactables moveableObject = null;
    private Interactables selectedObject = null;

    // Position inside the game window
    private int centerX = Consts.WIDTH / 2 - 3 * Consts.SCALED_TILE;
    private int centerY = Consts.HEIGHT / 2 - 3 * Consts.SCALED_TILE;

    // Image for the room background
    private BufferedImage image;

    // CONSTRUCTOR
    public Room(String name, GameTime time) {
        // Atributes
        this.name = name;
        this.listOfObjects = new ArrayList<>(); 
        this.listOfSims = new ArrayList<>();
        this.time = time;
        this.isEditingRoom = false;

        // Load the image of the room
        this.image = ImageLoader.loadWood();

        // ONLY FOR DEBUGGING
        // testRoom();
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public ArrayList<Interactables> getListOfObjects() {
        return listOfObjects;
    }

    public boolean isEditingRoom() {
        return isEditingRoom;
    }

    public ArrayList<Sim> getListOfSims() {
        return listOfSims;
    }
    
    // SETTERS
    public void addSim(Sim sim) {
        listOfSims.add(sim);
    }

    public void removeSim(Sim sim) {
        listOfSims.remove(sim);
    }

    public void changeisEditingRoomState() {
        this.isEditingRoom = !this.isEditingRoom;
    }

    public void addObject(Interactables object) {
        changeisEditingRoomState();
        moveableObject = object;
        collisionHandler = new CollisionHandler(moveableObject, this);
    }

    public void editObject(Interactables object) {
        moveableObject = object;
        listOfObjects.remove(object);
        collisionHandler = new CollisionHandler(moveableObject, this);
    }
    
    public void selectObject() {
        changeisEditingRoomState();
        try {
            selectedObject = listOfObjects.get(0);
            if (selectedObject instanceof Door) {
                throw new IndexOutOfBoundsException();
            }
        }
        catch (IndexOutOfBoundsException e) {
            selectedObject = null;
            changeisEditingRoomState();
        }
    }

    // OTHERS
    public void update() {
        // Editing an existing object
        updateSelectedObject();

        // Adding a new object
        updateUnaddedObject();
    }

    public void draw(Graphics2D g) {
        // Draw the room floor
        drawFloor(g);
        
        // Draw objects inside of the room
        drawObjects(g);

        // Draw sims inside of the room
        drawSims(g);

        // Draw the selector for an object
        drawObjectSelector(g);
        
        // Draw selected object
        drawSelectedObject(g);
    }
            
    private Interactables findNearestObject(String direction) {
        Interactables minObject = null;
        int minDistance = Integer.MAX_VALUE;
        int distance = Integer.MAX_VALUE;
        int dx = 0;
        int dy = 0;

        for (Interactables object : listOfObjects) {
            if (object == selectedObject || object instanceof Door) {
                continue;
            }
            
            dx = object.getX() - selectedObject.getX();
            dy = object.getY() - selectedObject.getY();
            distance = (int) Math.sqrt((dx * dx) + (dy * dy));
            
            switch (direction) {
                case "up":
                    if (dy < 0 && distance < minDistance)  {
                        minDistance = distance;
                        minObject = object;
                    }
                    break;
                case "left":
                    if (dx < 0 && distance < minDistance) {
                        minDistance = distance;
                        minObject = object;
                    }
                    break;
                case "down":
                    if (dy > 0 && distance < minDistance) {
                        minDistance = distance;
                        minObject = object;
                    }
                    break;
                case "right":
                    if (dx > 0 && distance < minDistance) {
                        minDistance = distance;
                        minObject = object;
                    }
                    break;
                default:
                    break;
            }
        }

        if (minObject == null) {
            return selectedObject;
        }
        else {
            return minObject;
        }
    }

    private void updateSelectedObject() {
        if (isEditingRoom && moveableObject == null) {
            // Find the nearest object based on the WASD keys
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
                selectedObject = findNearestObject("up");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                selectedObject = findNearestObject("left");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
                selectedObject = findNearestObject("down");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                selectedObject = findNearestObject("right");
            }

            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                editObject(selectedObject);
            }
        }
    }

    // TO - DO !!! : Integrate with inventory
    private void updateUnaddedObject() {
        if (isEditingRoom && moveableObject != null) {
            boolean inCollision = false;
            boolean isCollidingWithSim = false;
            boolean isWallOccupied = false;

            inCollision = collisionHandler.isCollision(moveableObject.getX(), moveableObject.getY());
            isCollidingWithSim = collisionHandler.isCollidingWithSim(moveableObject.getX(), moveableObject.getY(), listOfSims);
            
            moveableObject.move(collisionHandler);
            moveableObject.updateBounds();

            // To rotate the door
            if (moveableObject instanceof Door) {
                Door door = (Door) moveableObject;
                isWallOccupied = collisionHandler.isWallOccupied(door);

                if (KeyHandler.isKeyPressed(KeyHandler.KEY_R)) {
                    door.rotate(door.getX(), door.getY());
                }
            }

            // Add the object if enter is pressed and object is not in collision with another object
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER) && (!inCollision && !isWallOccupied && !isCollidingWithSim)) {
                listOfObjects.add(moveableObject);
                moveableObject = null;
                changeisEditingRoomState();
            }

            // Cancel adding or moving an object if escape is pressed
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
                moveableObject = null;
                changeisEditingRoomState();
            }
        }
    }

    private void drawFloor(Graphics2D g) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int tileX = centerX + (x * Consts.SCALED_TILE);
                int tileY = centerY + (y * Consts.SCALED_TILE) - Consts.OFFSET_Y;
                g.drawImage(image, tileX, tileY, Consts.SCALED_TILE, Consts.SCALED_TILE, null);
            }
        }
    }

    private void drawObjects(Graphics2D g) {
        for (Interactables object : listOfObjects) {
            // ONLY FOR DEBUNGGING
            if (object instanceof Placeholder) {
                object.draw(g);
            }
            else {
                object.draw(g, object);
            }
        }
    }

    private void drawSims(Graphics2D g) {
        for (Sim sim: listOfSims) {
            sim.draw(g);
        }
    }

    // TO - DO !!! : Find a better way to show selecting an object
    private void drawObjectSelector(Graphics2D g) {
        if (isEditingRoom && moveableObject == null) {
            g.setColor(new Color(255, 0, 0, 64)); // Transparent red color
            g.fillRect(selectedObject.getX(), selectedObject.getY(), selectedObject.getWidth(), selectedObject.getHeight());
        }
    }

    private void drawSelectedObject(Graphics2D g) {
        if (isEditingRoom && moveableObject != null) {
            if (moveableObject instanceof Placeholder) {
                moveableObject.draw(g);
            }
            else {
                moveableObject.draw(g, moveableObject);
            }
        }
    }

    // ONLY FOR DEBUGGING
    public void drawCollisionBox(Graphics2D g) {
        for (Interactables object : listOfObjects) {
            object.drawCollisionBox(g);
        }
    }

    public void testRoom() {
        listOfObjects.add(new Bed((Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) - 38 - Consts.OFFSET_Y, 0, time));
        listOfObjects.add(new Placeholder("1", "2", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 26 - Consts.OFFSET_Y, 3, 3, Color.CYAN, time));
        listOfObjects.add(new Placeholder("3", "4", 0, (Consts.CENTER_X / 2) + 268, (Consts.CENTER_Y / 2) - 38 - Consts.OFFSET_Y, 2, 1, Color.ORANGE, time));
        listOfObjects.add(new Placeholder("5", "6", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 282 - Consts.OFFSET_Y, 1, 1, Color.MAGENTA, time));
        listOfObjects.add(new Placeholder("7", "8", 0, (Consts.CENTER_X / 2) + 332, (Consts.CENTER_Y / 2) + 154 - Consts.OFFSET_Y, 1, 1, Color.LIGHT_GRAY, time));
    }
}
