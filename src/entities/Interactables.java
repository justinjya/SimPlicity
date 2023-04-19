package src.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import src.main.GameTime;
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
        this.color = new Color(0, 0, 0, 100); // this acts as the object shadow
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getImageIndex() {
        return imageIndex;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public <T extends Interactables> void draw(Graphics2D g, T interactables) {
        g.drawImage(interactables.getImage(), interactables.getX(), interactables.getY(), null);
    }

    public void draw(Graphics2D g, Interactables newObject, ArrayList<Interactables> existingObjects) {
        boolean intersectsWithExistingObject = false;
        for (Interactables existingObject : existingObjects) {
            if (newObject.getBounds().intersects(existingObject.getBounds())) {
                intersectsWithExistingObject = true;
                break;
            }
        }
    
        // Load the image for the object
        BufferedImage objectImage = newObject.getImage();
        
        if (intersectsWithExistingObject) {
            // Create a new image with a red overlay
            BufferedImage redOverlay = new BufferedImage(objectImage.getWidth(), objectImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D redOverlayGraphics = redOverlay.createGraphics();
            redOverlayGraphics.drawImage(objectImage, 0, 0, null);
            redOverlayGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
            redOverlayGraphics.setColor(Color.RED);
            redOverlayGraphics.fillRect(0, 0, objectImage.getWidth(), objectImage.getHeight());
            redOverlayGraphics.dispose();
    
            // Draw the image with the red overlay
            g.drawImage(redOverlay, newObject.getX(), newObject.getY(), null);
        }
        else {
            // Draw the image with 50% opacity
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g.drawImage(objectImage, newObject.getX(), newObject.getY(), null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    
    
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
    
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