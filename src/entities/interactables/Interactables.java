package src.entities.interactables;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

import src.entities.Entity;
import src.entities.sim.Sim;
import src.items.Item;
import src.main.GameTime;

public abstract class Interactables extends Entity implements Item {
    // Atributes
    private String name;
    private String interaction;
    private int imageIndex;
    protected boolean occupied;
    private Rectangle bounds;


    // CONSTRUCTOR
    public Interactables(String name, String interaction, int imageIndex, int x, int y, int width, int height) {
        super (
            x,
            y,
            width,
            height
        );
        
        this.name = name;
        this.interaction = interaction;
        this.imageIndex = imageIndex;
        this.occupied = false;
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getInteraction() {
        return interaction;
    }
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    public int getImageIndex() {
        return imageIndex;
    }
    
    // SETTERS
    public void changeOccupiedState() {
        this.occupied = !this.occupied;
    }
    
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public void updateBounds() {
        this.bounds.setLocation(getX(), getY());
    }

    // OTHER
    public <T extends Interactables> void draw(Graphics2D g, T interactables) {
        g.drawImage(interactables.getImage(), interactables.getX(), interactables.getY(), null);
    }
    
    public abstract BufferedImage getIcon();
    public abstract BufferedImage getImage();
    public abstract void interact(Sim sim, GameTime time);

    // ONLY FOR DEBUGGING
    public void drawCollisionBox(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 64)); // Transparent red color
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    // public void drawTimer(Graphics2D g) {
    //     g.setColor(Color.BLACK);
    //     Font font = new Font("Arial", Font.BOLD, 16);
    //     g.setFont(font);
        
    //     g.drawString("Duration: " + time.getDecrements(), 605, 60);
    // }
}