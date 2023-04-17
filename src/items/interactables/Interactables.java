package src.items.interactables;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

import src.main.GameTime;
import src.entities.Sim;
import src.items.Item;

public abstract class Interactables implements Item {
    // Atributes
    private String name;
    private String interaction;
    private int imageIndex;

    // Positions and sizes inside the game window 
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean occupied;
    private Color color;
    private Rectangle bounds;
    private GameTime time;

    public Interactables(String name, String interaction, int imageIndex, int x, int y, int width, int height, GameTime time) {
        this.name = name;
        this.interaction = interaction;
        this.imageIndex = imageIndex;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.occupied = false;
        this.color = new Color(0, 0, 0, 100);
        this.bounds = new Rectangle(x + 7, y + 7, width - 17, height - 17);
        this.time = time;
        // x, y, width, and height subtracted to accomodate for collision clipping
    }

    public String getName() {
        return name;
    }

    public String getInteraction() {
        return interaction;
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
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public Color getColor() {
        return color;
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    public GameTime getTime() {
        return time;
    }
    
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
    
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    public <T extends Interactables> void draw(Graphics2D g, T interactables) {
        g.drawImage(interactables.getImage(), interactables.getX(), interactables.getY(),
        interactables.getWidth(), interactables.getHeight(), null);
    }
    
    // ONLY FOR DEBUGGING
    public void drawCollisionBox(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 128)); // Transparent yellow color
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }
    
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public int getImageIndex() {
        return imageIndex;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public abstract BufferedImage getImage();
    public abstract void changeOccupied(Sim sim);
    public abstract void interact(Sim sim);
}