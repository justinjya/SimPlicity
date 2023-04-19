package src.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import src.assets.ImageLoader;
import src.entities.handlers.CollisionHandler;
import src.entities.handlers.InteractionHandler;

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
    
    // Image of the sim
    private BufferedImage[] images = new BufferedImage[12];

    // Collisions and interactions
    private CollisionHandler collisionHandler;
    private InteractionHandler interactionHandler;

    public Sim(String name, int x, int y, Room currentRoom) {
        // Atributes
        super(x, y, 1, 1);
        this.name = name;
        this.health = 80;
        this.hunger = 80;
        this.mood = 80;
        this.money = 100;
        this.status = "Idle";
        this.isBusy = false;
        this.currentRoom = currentRoom;

        // Load the image of the sim
        images = ImageLoader.loadSim();

        // Collisions and interactions
        collisionHandler = new CollisionHandler(this, currentRoom);
        interactionHandler = new InteractionHandler(this, currentRoom);
    }

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

    public boolean isBusy() {
        return isBusy;
    }

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

    public void changeBusyState() {
        this.isBusy = !this.isBusy;
    }

    public void draw(Graphics2D g) {
        // Draw the appropriate image based on the direction the sim is facing
        int imageIndex = getDirection();
        if (isMoving() && !currentRoom.isAddingObject() ) {
            imageIndex += (int) ((getDirection() + (System.currentTimeMillis() / 250) % 2) + 4);
        }

        if (!isBusy() && images[imageIndex] != null) {
            g.drawImage(images[imageIndex], getX(), getY(), null);
        }

        // ONLY FOR DEBUGGING
        // Draw the sim collision area as a red rectangle
        // g.setColor(new Color(255, 0, 0, 64)); // Transparent red color
        // g.fillRect(getX() + 8, getY() + 15, getWidth() - 16, getHeight() - 16);
    
        // Draw interaction range as a yellow rectangle
        g.setColor(new Color(255, 255, 0, 128)); // Transparent yellow color
        g.fillRect(interactionHandler.getX(), interactionHandler.getY(), interactionHandler.getWidth(), interactionHandler.getHeight());
    }

    public void update() {
        if (!isBusy() && !currentRoom.isAddingObject()) {
            move(collisionHandler, interactionHandler);
        }
    }

    // ONLY FOR DEBUGGING
    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }
}