package src.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import src.main.Consts;
import src.main.KeyInput;
import src.entities.handlers.*;
import src.items.interactables.*;
import src.entities.loaders.ImageLoader;

public class Sim {
    // Atributes
    private String name;
    private int health;
    private int hunger;
    private int mood;
    private int money;
    private String status;
    private boolean isBusy;
    
    // Positions and sizes inside the game window
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed = 5;
    private int direction; // 0 = down, 1 = up, 2 = left, 3 = right
    private InteractionHandler interactionHandler;
    private CollisionHandler collisionHandler;
    private Interactables[] solidObjects;
    
    // Image of the sim
    private BufferedImage[] images = new BufferedImage[12];

    public Sim(String name, int x, int y, Interactables[] solidObjects) {
        // Atributes
        this.name = name;
        this.health = 80;
        this.hunger = 80;
        this.mood = 80;
        this.money = 100;
        this.status = "Idle";
        this.isBusy = false;

        // Positions and sizes inside the game window
        this.x = x;
        this.y = y;
        this.width = Consts.SCALED_TILE;
        this.height = Consts.SCALED_TILE;
        this.direction = 0;
        interactionHandler = new InteractionHandler(this, solidObjects);
        collisionHandler = new CollisionHandler(this, solidObjects);
        this.solidObjects = solidObjects;

        // Load the image of the sim
        images = ImageLoader.loadSim();
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public boolean isWalking() {
        if (!isBusy) {
            if (KeyInput.isKeyDown(KeyInput.KEY_A) || KeyInput.isKeyDown(KeyInput.KEY_D) || 
                KeyInput.isKeyDown(KeyInput.KEY_W) || KeyInput.isKeyDown(KeyInput.KEY_S)) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics2D g) {
        // Draw the appropriate image based on the direction the sim is facing
        int imageIndex = direction;
        if (isWalking()) {
            imageIndex += (int) ((direction + (System.currentTimeMillis() / 250) % 2) + 4);
        }

        if (!isBusy && images[imageIndex] != null) {
            g.drawImage(images[imageIndex], x, y, null);
        }

        // ONLY FOR DEBUGGING
        // Draw the sim collision area as a red rectangle
        // g.setColor(new Color(255, 0, 0, 128)); // Transparent yellow color
        // g.fillRect(x, y, width - 8, height);
    
        // Draw interaction range as a yellow rectangle
        g.setColor(new Color(255, 255, 0, 128)); // Transparent yellow color
        g.fillRect(interactionHandler.getX(), interactionHandler.getY(), interactionHandler.getWidth(), interactionHandler.getHeight());
    }
    
    public void update() {
        // Update the sim position when walking
        int newX = x;
        int newY = y;

        if (isWalking()) {
            if (KeyInput.isKeyDown(KeyInput.KEY_A)) {
                newX -= speed;
                direction = 2;
                interactionHandler.moveLeft(newX, newY);
            }
            if (KeyInput.isKeyDown(KeyInput.KEY_D)) {
                newX += speed;
                direction = 3;
                interactionHandler.moveRight(newX, newY);
            }
            if (KeyInput.isKeyDown(KeyInput.KEY_W)) {
                newY -= speed;
                direction = 1;
                interactionHandler.moveUp(newX, newY);
            }
            if (KeyInput.isKeyDown(KeyInput.KEY_S)) {
                newY += speed;
                direction = 0;
                interactionHandler.moveDown(newX, newY);
            }
            checkCollision(newX, newY);
        }

        // Keybinds for the sim to interact with the game or window
        if (KeyInput.isKeyPressed(KeyInput.KEY_F)) { // check if the F key has been pressed since the last update
            System.out.println("press");
            interactionHandler.interact();
        }       
    }    

    private void checkCollision(int newX, int newY) {
        if (!collisionHandler.isCollision(newX, newY)) {
            x = newX;
            y = newY;
            boolean currentlyInRange = interactionHandler.isInRange();
            interactionHandler.setInRange(interactionHandler.isObjectInInteractionRange());

            // ONLY FOR DEBUGGING
            if (interactionHandler.isInRange() && !currentlyInRange) {
                decrementHealth();
            }
        }
    }

    // ONLY FOR DEBUGGING
    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public Interactables[] getSolidObjects() {
        return solidObjects;
    }

    private void decrementHealth() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    setHealth(getHealth() - 1);
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}