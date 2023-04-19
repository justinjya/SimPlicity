package src.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import src.assets.ImageLoader;

public class Sim extends Entity{
    // Atributes
    private String name;
    private int health;
    private int hunger;
    private int mood;
    private int money;
    private String status;
    private boolean isBusy;
    private Interactables[] solidObjects;
    
    // Image of the sim
    private BufferedImage[] images = new BufferedImage[12];

    public Sim(String name, int x, int y, Interactables[] solidObjects) {
        // Atributes
        super(x, y, 1, 1, solidObjects);
        this.name = name;
        this.health = 80;
        this.hunger = 80;
        this.mood = 80;
        this.money = 100;
        this.status = "Idle";
        this.isBusy = false;

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

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void draw(Graphics2D g) {
        // Draw the appropriate image based on the direction the sim is facing
        int imageIndex = getDirection();
        if (isMoving()) {
            imageIndex += (int) ((getDirection() + (System.currentTimeMillis() / 250) % 2) + 4);
        }

        if (!isBusy() && images[imageIndex] != null) {
            g.drawImage(images[imageIndex], getX(), getY(), null);
        }

        // ONLY FOR DEBUGGING
        // Draw the sim collision area as a red rectangle
        // g.setColor(new Color(255, 0, 0, 128)); // Transparent red color
        // g.fillRect(x, y, width - 8, height);
    
        // Draw interaction range as a yellow rectangle
        g.setColor(new Color(255, 255, 0, 128)); // Transparent yellow color
        g.fillRect(getInteractionHandler().getX(), getInteractionHandler().getY(), getInteractionHandler().getWidth(), getInteractionHandler().getHeight());
    }

    public void update() {
        if (!isBusy) {
            move();
        }
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