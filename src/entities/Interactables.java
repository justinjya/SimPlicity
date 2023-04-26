package src.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import src.main.GameTime;
import src.items.Item;

public abstract class Interactables extends Entity implements Item {
    // Atributes
    private String name;
    private String interaction;
    private int imageIndex;
    private boolean occupied;
    private Color color;
    private Rectangle bounds;
    private GameTime time;


    // CONSTRUCTOR
    public Interactables(String name, String interaction, int imageIndex, int x, int y, int width, int height, GameTime time) {
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
        this.color = new Color(0, 0, 0, 100); // This acts as the object shadow
        this.bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        this.time = time;
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
    
    public Color getColor() {
        return color;
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    public GameTime getTime() {
        return time;
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
    
    public void setColor(Color color) {
        this.color = color;
    }

    public void updateBounds() {
        this.bounds.setLocation(getX(), getY());
    }

    // OTHERS
    public <T extends Interactables> void draw(Graphics2D g, T interactables) {
        g.drawImage(interactables.getImage(), interactables.getX(), interactables.getY(), null);
    }
   
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    
    // ONLY FOR DEBUGGING
    public void drawTimer(Graphics2D g) {
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 16);
        g.setFont(font);
        
        g.drawString("Duration: " + time.getDecrements(), 605, 60);
    }
    
    public abstract BufferedImage getImage();
    public abstract void changeOccupied(Sim sim);
    public abstract void interact(Sim sim);

    // ONLY FOR DEBUGGING
    public void drawCollisionBox(Graphics2D g) {
        g.setColor(new Color(255, 0, 0, 128)); // Transparent yellow color
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }
}