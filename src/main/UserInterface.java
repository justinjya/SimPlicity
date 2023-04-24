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
    private int selectedBoxX = 207 - 4;  // Initial x position of selected box
    private int selectedBoxY = 483 - 4;  // Initial y position of selected box
    private int selectedBoxWidth = Consts.SCALED_TILE + 8;  // Width of selected box
    private int selectedBoxHeight = Consts.SCALED_TILE + 8;  // Height of selected box
    private int boxStep = 81;  // Amount to change box position with each key press
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
        g.setColor(Color.WHITE);
        g.fillRect(11, 51, 182, 24); // Sim name
        g.fillRect(607, 51, 182, 24); // Day number
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(11, 81, 182, 16); // Sim status
        g.fillRect(11, 266, 182, 28); // Time remaining title
        g.setColor(Color.GRAY);
        g.fillRect(11, 103, 182, 30); // Sim money
        g.fillRect(11, 294, 182, 58); // Time remaining
        // g.fillRect(607, 88, 182, 64); // House and Room name

        // Draw option boxes
        g.fillRect(207, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(288, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(369, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(450, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(531, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);

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
        g.setColor(Color.BLACK);

        font = new Font("Arial", Font.BOLD, 13);

        g.setFont(font);
        g.drawString(sim.getName(), 83, 68);
        
        g.drawString("Day " + time.getDay(), 675, 68);
        g.drawString("Time Remaining", 53, 285);

        g.setColor(Color.WHITE);

        drawAttributes(g);

        font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);

        if (sim.getInteractionHandler().isObjectInRange()) {
            object = sim.getInteractionHandler().getInteractableObject();
            g.drawString("Press F to Interact with " + object.getName(), Consts.CENTER_X - 72, Consts.CENTER_Y + 172);
        }

        // ONLY FOR DEBUGGING
        if (debug) {
            font = new Font("Arial", Font.PLAIN, 10);
            g.setFont(font);

            g.drawString("x: " + sim.getX(), 33, 374);
            g.drawString("y: " + sim.getY(), 33, 384);
            g.drawString("InRange: " + sim.getInteractionHandler().isObjectInRange(), 73, 374);
            g.drawString("isWalking: " + sim.isMoving(), 73, 384);
            
            if (sim.getInteractionHandler().isObjectInRange()) {
                    object = sim.getInteractionHandler().getInteractableObject();
                    g.drawString("isOccupied: " + object.isOccupied(), 33, 394);
                    g.drawString("imageIndex: " + object.getImageIndex(), 33, 404);
                }
        }
    }

    private void drawAttributes(Graphics2D g) {
        Font font = new Font("Arial", Font.PLAIN, 10);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("" + sim.getStatus(), 90, 93);

        font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);
        g.setColor(Color.WHITE);

        g.drawString("Health", 48, 159);
        g.drawString("Hunger", 48, 196);
        g.drawString("Mood", 48, 233);
        g.drawString("$ " + sim.getMoney(), 87, 122);
        
        g.drawString("Time", 48, 320);
        
        drawValue(g, sim.getHealth(), 174, 0);
        drawValue(g, sim.getHunger(), 174, 1);
        drawValue(g, sim.getMood(), 174, 2);
        drawTime(g, time.getTimeRemaining(), 174, 0);
    }

    private void drawValue(Graphics2D g, int value, int offsetX, int offsetY) {
        Font font = new Font("Arial", Font.PLAIN, 9);

        g.setFont(font);
        if (value < 100) {
            g.drawString("" + value, offsetX, 177 + (37 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 177 + (37 * offsetY));
        }
    }

    private void drawTime(Graphics2D g, int value, int offsetX, int offsetY) {
        Font font = new Font("Arial", Font.PLAIN, 9);

        g.setFont(font);
        if (value < 100) {
            g.drawString("" + value, offsetX, 340 + (37 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 340 + (37 * offsetY));
        }
    }

    private void drawSelectedBoxText(Graphics2D g) {
        Font font = new Font("Arial", Font.PLAIN, 12);

        g.setFont(font);
        switch (selectedBox) {
            case 1:
                g.drawString("Add Object", Consts.CENTER_X - 22, Consts.CENTER_Y + 172);
                break;
            case 2:
                g.drawString("Edit Room", Consts.CENTER_X - 18, Consts.CENTER_Y + 172);
                break;
            default:
                g.drawString("Lorem Ipsum", Consts.CENTER_X - 28, Consts.CENTER_Y + 172);
                break;
        }
    }

    // ONLY FOR DEBUGGING
    public void drawMockup(Graphics2D g) {
        g.drawImage(mockup, 0, 0, null);
    }
}
