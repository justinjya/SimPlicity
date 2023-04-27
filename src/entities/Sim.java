package src.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import src.assets.ImageLoader;
import src.entities.handlers.CollisionHandler;
import src.entities.handlers.InteractionHandler;
import src.world.Room;
import src.world.House;

public class Sim extends Entity{
    // Atributes
    private String name;
    private int health;
    private int hunger;
    private int mood;
    private int money;
    private String status;
    private boolean isBusy;

    private Room currentRoom;
    private House currentHouse;
    
    // Image of the sim
    private BufferedImage[] images = new BufferedImage[12];

    // Collisions and interactions
    private CollisionHandler collisionHandler;
    private InteractionHandler interactionHandler;

    // CONSTRUCTOR
    public Sim(String name, int x, int y) {
        // Atributes
        super (
            x,
            y,
            1,
            1
        );
        
        this.name = name;
        this.health = 80;
        this.hunger = 80;
        this.mood = 80;
        this.money = 100;
        this.status = "Idle";
        this.isBusy = true;

        // Load the image of the sim
        images = ImageLoader.loadSim();

        // Collisions and interactions
        collisionHandler = new CollisionHandler(this, currentRoom);
        interactionHandler = new InteractionHandler(this, currentRoom);
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public int getMood() {
        return mood;
    }
    
    public int getMoney() {
        return money;
    }

    public String getStatus() {
        return status;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public House getCurrentHouse() {
        return currentHouse;
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public boolean isStatusCurrently(String status) {
        return this.status.equals(status);
    }

    public boolean isBusy() {
        return isBusy;
    }

    // SETTERS
    public void setHealth(int health) {
        this.health = health;
        if (this.health > 100) this.health = 100;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
        if (this.hunger > 100) this.hunger = 100;
    }

    public void setMood(int mood) {
        this.mood = mood;
        if (this.mood > 100) this.mood = 100;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void resetStatus() {
        setStatus("Idle");
    }

    public void setCurrentHouse(House house) {
        this.currentHouse = house;
    }

    public void setCurrentRoom(Room room) {
        if (currentRoom != null) {
            this.currentRoom.removeSim(this);
        }
        
        this.currentRoom = room;
        this.currentRoom.addSim(this);
        collisionHandler = new CollisionHandler(this, room);
        interactionHandler = new InteractionHandler(this, room);
    }

    public void changeIsBusyState() {
        this.isBusy = !this.isBusy;
    }

    public void interact() {
        interactionHandler.interact();
    }

    // OTHERS
    public void update() {
        if (!isStatusCurrently("Idle") || currentRoom.isEditingRoom()) {
            return;
        }
        
        if (isMoving() && !isBusy) {
            move(collisionHandler, interactionHandler);
        }
    }

    public void draw(Graphics2D g) {
        // Draw the appropriate image based on the direction the sim is facing
        int imageIndex = getDirection();
        if (!isBusy && isMoving() && !currentRoom.isEditingRoom()) {
            imageIndex += (int) ((getDirection() + (System.currentTimeMillis() / 250) % 2) + 4);
        }

        if (isStatusCurrently("Idle")) {
            g.drawImage(images[imageIndex], getX(), getY(), null);
        }
    }

    // ONLY FOR DEBUGGING
    public void drawSimStanding(Graphics2D g) {
        g.drawImage(images[2], getX(), getY(), null);
    }

    public void drawCollisionBox(Graphics2D g) {
        // Draw the sim collision area as a red rectangle
        g.setColor(new Color(255, 0, 0, 64)); // Transparent red color
        g.fillRect(getX() + 8, getY() + 15, getWidth() - 16, getHeight() - 16);
    }

    public void drawInteractionRange(Graphics2D g) {
        // Draw the interaction range as a yellow rectangle
        g.setColor(new Color(255, 255, 0, 128)); // Transparent yellow color
        g.fillRect(interactionHandler.getX(), interactionHandler.getY(), interactionHandler.getWidth(), interactionHandler.getHeight());
    }
}