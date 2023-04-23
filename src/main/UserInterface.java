package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.entities.handlers.KeyHandler;
import src.items.interactables.*;
import src.entities.Sim;
import src.assets.ImageLoader;
import src.entities.Interactables;

public class UserInterface {
    // Atributes to show
    private BufferedImage[] images;
    private Sim sim;
    private Interactables object;
    private GameTime time;

    //ONLY FOR DEBUGGING
    private boolean debug;
    private BufferedImage mockup;

    // Selection Box Attributes
    private int selectedBoxX = 196 - 8;  // Initial x position of selected box
    private int selectedBoxY = 500 - 8;  // Initial y position of selected box
    private int selectedBoxWidth = Consts.SCALED_TILE + 16;  // Width of selected box
    private int selectedBoxHeight = Consts.SCALED_TILE + 16;  // Height of selected box
    private int boxStep = 86;  // Amount to change box position with each key press
    private int selectedBox = 0; // Boxes starting from 0 to 4

    public UserInterface(Sim sim, GameTime time) {
        this.sim = sim;
        this.time = time;
        this.debug = false;

        // ONLY FOR DEBUGGING
        this.mockup = ImageLoader.loadMockup();
    }

    public void debug() {
        this.debug = !this.debug;
    }

    private void moveSelectedBox(String direction) {
        switch (direction)  {
            case "left":
                if (selectedBox > 0) {
                    selectedBox--;
                    selectedBoxX -= boxStep;
                }
                break;
            case "right":
                if (selectedBox < 4) {
                    selectedBox++;
                    selectedBoxX += boxStep;
                }
                break;
            default:
                break;
        }
    }

    // TO - DO !!! : Add the rest of the boxes features
    private void boxEntered() {
        sim.resetStatus();
        switch (selectedBox) {
            case 1:
                // This is just a test
                sim.getCurrentRoom().addObject(new Bed(time));
                break;
            case 2:
                sim.getCurrentRoom().selectObject();
                break;
            default:
                break;
        }
    }

    public void tab() {
        if (!sim.isStatusCurrently("Tabbed")) {
            sim.setStatus("Tabbed");
        }
        else {
            sim.resetStatus();
        }
    }

    public void update() {
        // If enter is pressed execute a function according to selected box position 
        if (sim.isStatusCurrently("Tabbed")) {
            // Change selected box based on key input
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                moveSelectedBox("left");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                moveSelectedBox("right");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                boxEntered();
            }
        }
    }

    public void draw(Graphics2D g) {
        // ONLY FOR DEBUGGING
        // g.setColor(new Color(0, 0, 0, 128)); // Transparent black color
        
        // Draw box for filling text
        g.setColor(Color.GRAY);
        g.fillRect(19, 53, 165, 230); // Sim status
        g.fillRect(616, 53, 165, 77); // Day number and time remaining

        // Draw option boxes
        g.fillRect(196, 500, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(282, 500, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(368, 500, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(454, 500, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(540, 500, Consts.SCALED_TILE, Consts.SCALED_TILE);

        // Draw selected box
        if (sim.isStatusCurrently("Tabbed")) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(selectedBoxX, selectedBoxY, selectedBoxWidth, selectedBoxHeight);
            drawSelectedBoxText(g);
        }
        drawText(g);
    }

    private void drawText(Graphics2D g) {
        Font font;
        g.setColor(Color.WHITE);

        font = new Font("Arial", Font.BOLD, 13);

        g.setFont(font);
        g.drawString(sim.getName(), 33, 82);
        g.drawString("Day : " + time.getDay(), 630, 82);

        drawAttributes(g);

        // ONLY FOR DEBUGGING
        if (debug) {
            g.drawString("x: " + sim.getX(), 33, 264);
            g.drawString("y: " + sim.getY(), 33, 274);
            g.drawString("InRange: " + sim.getInteractionHandler().isObjectInRange(), 73, 264);
            g.drawString("isWalking: " + sim.isMoving(), 73, 274);
            
            if (sim.getInteractionHandler().isObjectInRange()) {
                    object = sim.getInteractionHandler().getInteractableObject();
                    g.drawString("Press F to Interact with " + object.getName(), 33, 304);
                    g.drawString("isOccupied: " + object.isOccupied(), 33, 314);
                    g.drawString("imageIndex: " + object.getImageIndex(), 33, 324);
                }
                
            // if (!sim.isIdle()) {
            //     sim.getInteractionHandler().getInteractableObject().drawTimer(g);
            // }
        }

        // ONLY FOR DEBUGGING
        // g.drawString("Duration: " + time.getDecrements(), 605, 60);
    }

    private void drawAttributes(Graphics2D g) {
        Font font = new Font("Arial", Font.PLAIN, 12);

        g.setFont(font);
        g.drawString("Health", 33, 101);
        g.drawString("Hunger", 33, 138);
        g.drawString("Mood", 33, 175);
        g.drawString("Money", 33, 212);
        g.drawString("Status : " + sim.getStatus(), 33, 249);

        g.drawString("Time", 630, 101);

        drawValue(g, sim.getHealth(), 160, 0);
        drawValue(g, sim.getHunger(), 160, 1);
        drawValue(g, sim.getMood(), 160, 2);
        drawValue(g, sim.getMoney(), 160, 3);
        drawValue(g, time.getTimeRemaining(), 757, 0);
    }

    private void drawValue(Graphics2D g, int value, int offsetX, int offsetY) {
        Font font = new Font("Arial", Font.PLAIN, 9);

        g.setFont(font);
        if (value < 100) {
            g.drawString("" + value, offsetX, 120 + (37 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 120 + (37 * offsetY));
        }
    }

    private void drawSelectedBoxText(Graphics2D g) {
        Font font = new Font("Arial", Font.PLAIN, 12);

        g.setFont(font);
        switch (selectedBox) {
            case 1:
                g.drawString("Add Object", Consts.CENTER_X - 22, Consts.CENTER_Y + 180);
                break;
            case 2:
                g.drawString("Edit Room", Consts.CENTER_X - 18, Consts.CENTER_Y + 180);
                break;
            default:
                g.drawString("Lorem Ipsum", Consts.CENTER_X - 28, Consts.CENTER_Y + 180);
                break;
        }
    }

    // ONLY FOR DEBUGGING
    public void drawMockup(Graphics2D g) {
        g.drawImage(mockup, 0, 0, null);
    }
}
