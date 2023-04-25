package src.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.assets.ImageLoader;
import src.main.Consts;
import src.main.GameTime;
import src.items.interactables.*;
import src.entities.Interactables;
import src.entities.handlers.*;

public class Room {
    // Atributes
    private String name;
    private ArrayList<Interactables> listOfObjects;
    private GameTime time;
    
    // For adding, editing, and removing objects
    private boolean editingRoom;
    private Interactables moveableObject = null;
    private Interactables selectedObject = null;
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
        this.editingRoom = false;
        this.image = ImageLoader.loadWood();

        // ONLY FOR DEBUGGING
        // testRoom();

        Room secondRoom = new Room(this, time);
        listOfObjects.add(new Door(3, secondRoom, time));
        secondRoom.getListOfObjects().add(new Door((Door) listOfObjects.get(0), this, time));
    }

    public Room(Room room, GameTime time) {
        this.name = "Second Room";
        this.listOfObjects = new ArrayList<>(); 
        this.time = time;
        this.editingRoom = false;
        this.image = ImageLoader.loadWood();

        // listOfObjects.add(new Door(0, room, time, true));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Interactables> getListOfObjects() {
        return listOfObjects;
    }

    public boolean isEditingRoom() {
        return editingRoom;
    }

    public void changeEditingRoomState() {
        this.editingRoom = !this.editingRoom;
    }

    public void addObject(Interactables object) {
        changeEditingRoomState();
        moveableObject = object;
        collisionHandler = new CollisionHandler(moveableObject, this);
    }

    public void editObject(Interactables object) {
        moveableObject = object;
        listOfObjects.remove(object);
        collisionHandler = new CollisionHandler(moveableObject, this);
    }
    
    public void selectObject() {
        changeEditingRoomState();
        selectedObject = listOfObjects.get(0);
    }
            
    private Interactables findNearestObject(String direction) {
        Interactables minObject = null;
        int minDistance = Integer.MAX_VALUE;
        int distance = Integer.MAX_VALUE;
        int dx = 0;
        int dy = 0;

        for (Interactables object : listOfObjects) {
            if (object == selectedObject) {
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

    public void update() {
        // Editing an existing object
        if (isEditingRoom() && moveableObject == null) {
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

        // Adding a new object
        if (isEditingRoom() && moveableObject != null) {
            boolean inCollision;
            inCollision = collisionHandler.isCollision(moveableObject.getX(), moveableObject.getY());

            moveableObject.move(collisionHandler);
            moveableObject.updateBounds();

            // Add the object if enter is pressed and object is not in collision with another object
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER) && !inCollision) {
                // TO - DO !!! : Integrate with inventory
                listOfObjects.add(moveableObject);
                moveableObject = null;
                changeEditingRoomState();
            }

            // Cancel adding or moving an object if escape is pressed
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
                // TO - DO !!! : Integrate with inventory
                moveableObject = null;
                changeEditingRoomState();
            }
        }
    }

    public void draw(Graphics2D g) {
        // Draw the room floor
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int tileX = centerX + (x * Consts.SCALED_TILE);
                int tileY = centerY + (y * Consts.SCALED_TILE) - Consts.OFFSET_Y;
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
            object.drawCollisionBox(g);
        }

        if (isEditingRoom() && moveableObject == null) {
            // TO - DO !!! : Find a better way to show selecting an object
            g.setColor(new Color(255, 0, 0, 64)); // Transparent red color
            g.fillRect(selectedObject.getX(), selectedObject.getY(), selectedObject.getWidth(), selectedObject.getHeight());
        }
        
        if (isEditingRoom() && moveableObject != null) {
            if (moveableObject instanceof Placeholder) {
                moveableObject.draw(g);
            }
            else {
                moveableObject.draw(g, moveableObject);
            }
        }
    }
    
    // ONLY FOR DEBUGGING
    public void testRoom() {
        listOfObjects.add(new Bed((Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) - 38 - Consts.OFFSET_Y, 0, time));
        listOfObjects.add(new Placeholder("1", "2", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 26 - Consts.OFFSET_Y, 3, 3, Color.CYAN, time));
        // listOfObjects.add(new Placeholder("3", "4", 0, (Consts.CENTER_X / 2) + 268, (Consts.CENTER_Y / 2) - 38 - Consts.OFFSET_Y, 2, 1, Color.ORANGE, time));
        listOfObjects.add(new Placeholder("5", "6", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 282 - Consts.OFFSET_Y, 1, 1, Color.MAGENTA, time));
        listOfObjects.add(new Placeholder("7", "8", 0, (Consts.CENTER_X / 2) + 332, (Consts.CENTER_Y / 2) + 154 - Consts.OFFSET_Y, 1, 1, Color.LIGHT_GRAY, time));
    }

    public void drawGrid(Graphics2D g) {
        // Draw a drak gray and gray 6x6 grid
        int centerX = (Consts.WIDTH / 2) - (3 * Consts.SCALED_TILE);
        int centerY = (Consts.HEIGHT / 2) - (3 * Consts.SCALED_TILE);

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int rectX = centerX + (x * Consts.SCALED_TILE);
                int rectY = centerY + (y * Consts.SCALED_TILE);
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
