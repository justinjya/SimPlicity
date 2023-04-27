package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.entities.handlers.KeyHandler;
import src.items.interactables.*;
import src.world.World;
import src.entities.Sim;
import src.assets.ImageLoader;
import src.entities.Interactables;

public class UserInterface {
    // Atributes to show
    private BufferedImage[] images;

    private World world;
    private Sim currentSim;

    private Interactables object;

    private boolean viewingWorld;
    private boolean tabbed;
    private GameTime time;

    // Selection Box Attributes
    private int selectedBox = 0; // Boxes starting from 0 to 4
    private int selectedBoxX = 203;
    private int selectedBoxY = 479;
    private int selectedBoxWidth = Consts.SCALED_TILE + 8;
    private int selectedBoxHeight = Consts.SCALED_TILE + 8;
    private int boxStep = 81;

    //ONLY FOR DEBUGGING
    private boolean debug;
    private BufferedImage mockup;

    // CONSTRUCTOR
    public UserInterface(Sim sim, GameTime time) {
        this.currentSim = sim;
        this.time = time;
        this.debug = false;

        // ONLY FOR DEBUGGING
        this.mockup = ImageLoader.loadMockup();
    }

    public UserInterface(World world, GameTime time) {
        this.world = world;
        this.time = time;
        this.viewingWorld = true;
        this.tabbed = false;
        this.debug = false;

        // ONLY FOR DEBUGGING
        this.mockup = ImageLoader.loadMockup();
    }

    public UserInterface(Sim sim, World world, GameTime time) {
        this.currentSim = sim;
        this.world = world;
        this.time = time;
        this.viewingWorld = true;
        this.tabbed = false;
        this.debug = false;

        // ONLY FOR DEBUGGING
        this.mockup = ImageLoader.loadMockup();
    }

    // GETTERS
    public boolean isTabbed() {
        return tabbed;
    }

    public boolean isViewingWorld() {
        return viewingWorld;
    }

    public Sim getCurrentSim() {
        return currentSim;
    }

    // SETTERS
    public void setCurrentSim(Sim currentSim) {
        this.currentSim = currentSim;
    }

    public void changeIsViewingWorldState() {
        this.viewingWorld = !this.viewingWorld;
    }

    public void debug() {
        this.debug = !this.debug;
    }

    public void tab() {
        this.tabbed = !this.tabbed;
        currentSim.changeIsBusyState();
    }

    // OTHERS
    public void update() {
        if (viewingWorld) {
            world.getCursor().updateUI(this);

            if (!world.isAdding()) {
                if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
                    changeIsViewingWorldState();
                }
            }
        }

        // If enter is pressed execute a function according to selected box position 
        if (tabbed) {
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

    private void moveSelectedBox(String direction) {
        switch (direction)  {
            case "left":
                selectedBox--;
                if (selectedBox < 0) {
                    selectedBox = 4;
                    selectedBoxX += boxStep * 4;
                }
                else {
                    selectedBoxX -= boxStep;
                }
                break;
            case "right":
                selectedBox++;
                if (selectedBox > 4) {
                    selectedBox = 0;
                    selectedBoxX -= boxStep * 4;
                }
                else {
                    selectedBoxX += boxStep;
                }
                break;
            default:
                break;
        }
    }

    // TO - DO !!! : Add the rest of the boxes features
    private void boxEntered() {
        tab();
        switch (selectedBox) {
            case 0:
                // This is just a test
                currentSim.getCurrentRoom().selectObject();
                break;
            case 1:
                currentSim.getCurrentRoom().addRoom("Second Room");
                break;
            case 2:
                currentSim.getCurrentRoom().addObject(new Bed(time));
                break;
            case 4:
                changeIsViewingWorldState();
                break;
            default:
                break;
        }
    }

    public void draw(Graphics2D g) {
        if (viewingWorld) {
            Font font;
            g.setColor(Color.WHITE);

            font = new Font("Arial", Font.PLAIN, 12);

            g.setFont(font);
            g.drawString("press shift to switch cursor movement", Consts.CENTER_X - 100, 25);
        }
        else {
            // ONLY FOR DEBUGGING
            if (debug) {
                currentSim.drawCollisionBox(g);
                currentSim.drawInteractionRange(g);
                currentSim.getCurrentRoom().drawCollisionBox(g);
            }

            drawUI(g);
        }
    }

    private void drawUI(Graphics2D g) {
        // ONLY FOR DEBUGGING
            // g.setColor(new Color(0, 0, 0, 128)); // Transparent black color
            
            // Draw main ui boxes
            g.setColor(Color.WHITE);
            g.fillRect(11, 51, 182, 24); // currentSim name
            g.fillRect(607, 51, 182, 24); // Day number

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(11, 81, 182, 16); // currentSim status
            g.fillRect(11, 266, 182, 28); // Time remaining title
            g.fillRect(607, 119, 182, 16); // Room name
            
            g.setColor(Color.GRAY);
            g.fillRect(11, 103, 182, 30); // currentSim money
            g.fillRect(11, 294, 182, 58); // Time remaining
            g.fillRect(607, 88, 182, 31); // House name

            // Draw tab boxes
            g.fillRect(207, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
            g.fillRect(288, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
            g.fillRect(369, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
            g.fillRect(450, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);
            g.fillRect(531, 483, Consts.SCALED_TILE, Consts.SCALED_TILE);

            // Draw selected box
            if (tabbed) {
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
        g.drawString(currentSim.getName(), 83, 68);
        
        g.drawString("Day " + time.getDay(), 675, 68);
        g.drawString("Time Remaining", 53, 285);

        g.setColor(Color.WHITE);
        g.drawString("House name", 660, 108);

        drawAttributes(g);

        font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);

        if (currentSim.getInteractionHandler().isObjectInRange() && currentSim.isStatusCurrently("Idle")) {
            object = currentSim.getInteractionHandler().getInteractableObject();
            g.drawString("Press F to Interact with " + object.getName(), Consts.CENTER_X - 72, Consts.CENTER_Y + 172);
        }

        // ONLY FOR DEBUGGING
        if (debug) {
            font = new Font("Arial", Font.PLAIN, 10);
            g.setFont(font);

            g.drawString("x: " + currentSim.getX(), 33, 374);
            g.drawString("y: " + currentSim.getY(), 33, 384);
            g.drawString("InRange: " + currentSim.getInteractionHandler().isObjectInRange(), 73, 374);
            g.drawString("isWalking: " + currentSim.isMoving(), 73, 384);
            g.drawString("isEditingRoom: " + currentSim.getCurrentRoom().isEditingRoom(), 33, 398);
            g.drawString("isBusy: " + currentSim.isBusy(), 33, 408);
            g.drawString("isEditingRoom: " + currentSim.getCurrentRoom().isEditingRoom(), 33, 398);
            g.drawString("isBusy: " + currentSim.isBusy(), 33, 408);
            
            // if (currentSim.getInteractionHandler().isObjectInRange()) {
            //     object = currentSim.getInteractionHandler().getInteractableObject();
            //     g.drawString("isOccupied: " + object.isOccupied(), 33, 394);
            //     g.drawString("imageIndex: " + object.getImageIndex(), 33, 404);
            // }
        }
    }

    private void drawAttributes(Graphics2D g) {
        Font font = new Font("Arial", Font.PLAIN, 10);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("" + currentSim.getStatus(), 90, 93);
        g.drawString("" + currentSim.getCurrentRoom().getName(), 670, 130);

        font = new Font("Arial", Font.PLAIN, 12);
        g.setFont(font);
        g.setColor(Color.WHITE);

        g.drawString("Health", 48, 159);
        g.drawString("Hunger", 48, 196);
        g.drawString("Mood", 48, 233);
        g.drawString("$ " + currentSim.getMoney(), 87, 122);
        
        g.drawString("Time", 48, 320);
        
        drawValue(g, currentSim.getHealth(), 174, 0);
        drawValue(g, currentSim.getHunger(), 174, 1);
        drawValue(g, currentSim.getMood(), 174, 2);
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
            case 0:
                g.drawString("Edit Room", Consts.CENTER_X - 18, Consts.CENTER_Y + 172);
                break;
            case 1:
                g.drawString("Upgrade House", Consts.CENTER_X - 38, Consts.CENTER_Y + 172);
                break;
            case 2:
                g.drawString("Add Object", Consts.CENTER_X - 22, Consts.CENTER_Y + 172);
                break;
            case 3:
                g.drawString("View currentSims", Consts.CENTER_X - 20, Consts.CENTER_Y + 172);
                break;
            case 4:
                g.drawString("Visit Another currentSim", Consts.CENTER_X - 38, Consts.CENTER_Y + 172);
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
