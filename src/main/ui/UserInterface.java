package src.main.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.GameTime;
import src.world.World;
import src.assets.ImageLoader;
import src.entities.interactables.Interactables;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.items.foods.BakedFood;
import src.main.KeyHandler;

public class UserInterface {
    // Attributes
    private World world;
    private Sim currentSim;
    private Inventory currentSimInventory;

    // User Interface States
    private boolean viewingWorld = false;
    private boolean tabbed = false;

    // User Interface Images
    private BufferedImage[] images;

    // ONLY FOR DEBUGGING
    private boolean debug = true;
    private BufferedImage mockup;

    // CONSTRUCTOR
    public UserInterface(World world) {
        this.world = world;
        this.currentSim = world.getListOfSim().get(0);
        this.currentSimInventory = currentSim.getInventory();

        // ONLY FOR DEBUGGING
        this.mockup = ImageLoader.loadMockup();
    }

    // GETTERS
    public World getWorld() {
        return world;
    }

    public Sim getCurrentSim() {
        return currentSim;
    }

    public boolean isViewingWorld() {
        return viewingWorld;
    }

    public boolean isTabbed() {
        return tabbed;
    }

    // SETTERS
    public void setCurrentSim(Sim sim) {
        currentSim = sim;
        currentSimInventory = currentSim.getInventory();

        if (currentSim.isBusy())
            currentSim.changeIsBusyState();

        for (Sim s : world.getListOfSim()) {
            if (s == currentSim)
                continue;

            if (!s.isBusy())
                s.changeIsBusyState();
        }
    }

    public void changeIsViewingWorldState() {
        this.viewingWorld = !this.viewingWorld;
    }

    public void tab() {
        if (!currentSimInventory.isOpen()) {
            this.tabbed = !this.tabbed;
            currentSim.changeIsBusyState();
        }
    }

    public void debug() {
        this.debug = !this.debug;
    }

    public void inventory() {
        Inventory inventory = currentSim.getInventory();

        if (tabbed) {
            tab();
        }

        inventory.changeIsOpen();
        currentSim.changeIsBusyState();
    }

    // OTHERS
    public void update() {
        if (tabbed && !currentSim.getInventory().isOpen()) {
            TabUserInterface.update(this);
        }

        if (currentSimInventory.isOpen()) {
            currentSimInventory.update(this);
        }
    }

    public void draw(Graphics2D g) {
        if (viewingWorld) {
            Font font;
            g.setColor(Color.WHITE);

            font = new Font("Inter", Font.PLAIN, 12);

            g.setFont(font);
            g.drawString("press shift to switch cursor movement", Consts.CENTER_X - 100, 25);
        } else {
            drawUI(g);
        }

        if (isViewingRecipes) {
            drawCookUI(g);
        }

        if (!isAbleToCook) {
            drawCantCookUI(g);
        }
    }
    public void drawCantCookUI(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(350, 250, 100, 100);
        g.setColor(Color.BLACK);
        g.drawString("INGREDIENTS NOT AVAILABLE", 370, 300);
        g.drawString("PRESS ESC TO CONTINUE", 370, 330);

    }
    private static boolean isViewingRecipes = false;
    private static boolean isAbleToCook = true;
    private static int slotRow = 0;
    private static int slotColumn = 0;
    private static int slotSize = 45;
    private static int cursorX = 630 + (slotSize * slotColumn);
    private static int cursorY = 250 + (slotSize * slotRow);
    public static void changeIsViewingRecipes() {
        isViewingRecipes = !isViewingRecipes;
    }
    public static void changeIsAbleToCook() {
        isAbleToCook = !isAbleToCook;
    }
    public static boolean getIsAbleToCook() {
        return isAbleToCook;
    }
    public static int getslotRow() {
        return slotRow;
    }
    public static int getslotColumn() {
        return slotColumn;
    }
    public static int getslotSize() {
        return slotSize;
    }
    public static void setSlotRow(int slotRow) {
        UserInterface.slotRow = slotRow;
    }
    public static void setSlotColumn(int slotColumn) {
        UserInterface.slotColumn = slotColumn;
    }
    private void drawCookUI(Graphics2D g) {
        
        
        // set the position and dimensions
        
       

        int frameX = 607;
        int frameY = 209;
        int frameWidth = 182;
        int frameHeight = 262;

        int boxWidth = (slotSize * 3) + 20;
        int boxHeight = (slotSize * 4) + 20;
        int boxX = frameX + ((frameWidth - boxWidth) / 2);
        int boxY = frameY + ((frameHeight - boxHeight) / 2) + ((frameHeight - boxHeight) / 4);

        int slotXstart = boxX + ((boxWidth - (slotSize * 3)) / 2);
        int slotYstart = boxY + ((boxHeight - (slotSize * 4)) / 2);
        int slotX = slotXstart;
        int slotY = slotYstart;

        // initialize available baked food
        BakedFood nasiAyam = new BakedFood(0);
        BakedFood nasiKari = new BakedFood(1);
        BakedFood susuKacang = new BakedFood(2);
        BakedFood tumisSayur = new BakedFood(3);
        BakedFood bistik = new BakedFood(4);

        // draw the title "Cook" box
        g.fillRect(607, 183, 182, 26);
        g.drawString("Cook", 668, 201);

        // draw the frame
        g.setColor(Color.GRAY);
        g.fillRect(frameX, frameY, frameWidth, frameHeight);

        // draw the box
        g.setColor(Color.DARK_GRAY);
        g.fillRect(boxX, boxY, boxWidth, boxHeight);

        // draw image of available baked food
        g.drawImage(nasiAyam.getIcon(), slotX, slotY, null);
        g.drawImage(nasiKari.getIcon(), slotX+slotSize, slotY, null);
        g.drawImage(susuKacang.getIcon(), slotX+2*slotSize, slotY, null);
        g.drawImage(tumisSayur.getIcon(), slotX, slotY+slotSize, null);
        g.drawImage(bistik.getIcon(), slotX+slotSize, slotY+slotSize, null);

        // cursor
        int cursorWidth = slotSize;
        int cursorHeight = slotSize;
        cursorX = slotXstart + (slotSize * slotColumn);
        cursorY = slotYstart + (slotSize * slotRow);

        g.setColor(Color.WHITE);
        g.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);

        g.setColor(Color.BLACK);
        Font font = new Font("Inter", Font.BOLD, 10);
        g.setFont(font);

        if (slotRow == 0 && slotColumn == 0) {
            g.drawString("Nasi Ayam", frameX + 50, frameY + 20);
        }
        else if (slotRow == 0 && slotColumn == 1) {
            g.drawString("Nasi Kari", frameX + 50, frameY + 20);
        }
        else if (slotRow == 0 && slotColumn == 2) {
            g.drawString("Susu Kacang", frameX + 50, frameY + 20);
        }
        else if (slotRow == 1 && slotColumn == 0) {
            g.drawString("Tumis Sayur", frameX + 50, frameY + 20);
        }
        else if (slotRow == 1 && slotColumn == 1) {
            g.drawString("Bistik", frameX + 50, frameY + 20);
        }

    }

    private void drawUI(Graphics2D g) {
        // ONLY FOR DEBUGGING
        if (debug) {
            currentSim.drawCollisionBox(g);
            currentSim.drawInteractionRange(g);
            currentSim.getCurrentRoom().drawCollisionBox(g);
        }

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

        g.fillRect(607, 147, 182, 26); // Inventory

        drawText(g);
        drawAttributes(g);

        // Draw currentSim's inventory
        if (currentSimInventory.isOpen()) {
            currentSimInventory.draw(g);
        }

        // Draw tab boxes
        TabUserInterface.draw(g, this);
        if (currentSimInventory.isOpen()) {
            currentSimInventory.draw(g);
        }  

        // Draw tab boxes
       TabUserInterface.draw(g, this);
    }

    private void drawText(Graphics2D g) {
        Font font;
        g.setColor(Color.BLACK);

        font = new Font("Inter", Font.BOLD, 13);

        g.setFont(font);
        g.drawString(currentSim.getName(), 83, 68);

        g.drawString("Day " + GameTime.day, 675, 68);
        g.drawString("Time Remaining", 53, 285);

        g.setColor(Color.WHITE);
        g.drawString(currentSim.getCurrentHouse().getName(), 650, 108);
        g.drawString("Inventory", 668, 165);

        drawAttributes(g);

        font = new Font("Inter", Font.PLAIN, 12);
        g.setFont(font);

        try {
            if (!tabbed && currentSim.getInteractionHandler().isObjectInRange()
                    && currentSim.isStatusCurrently("Idle")) {
                Interactables object = currentSim.getInteractionHandler().getInteractableObject();
                if (object != null) {
                    g.drawString("Press F to Interact with " + object.getName(), Consts.CENTER_X - 72,
                            Consts.CENTER_Y + 172);
                }
            }
        } catch (NullPointerException e) {
        }

        // ONLY FOR DEBUGGING
        if (debug) {
            font = new Font("Inter", Font.PLAIN, 10);
            g.setFont(font);

            g.drawString("x: " + currentSim.getX(), 33, 374);
            g.drawString("y: " + currentSim.getY(), 33, 384);
            g.drawString("InRange: " + currentSim.getInteractionHandler().isObjectInRange(), 73, 374);
            g.drawString("isWalking: " + currentSim.isMoving(), 73, 384);
            g.drawString("isEditingRoom: " + currentSim.getCurrentRoom().isEditingRoom(), 33, 398);
            g.drawString("isBusy: " + currentSim.isBusy(), 33, 408);
            g.drawString("isEditingRoom: " + currentSim.getCurrentRoom().isEditingRoom(), 33, 398);
            g.drawString("isBusy: " + currentSim.isBusy(), 33, 408);
            g.drawString("Profession: " + currentSim.getProfession().getName(), 33, 418);
            g.drawString("durationWorked: " + currentSim.getDurationWorked(), 33, 428);
        }
    }

    private void drawAttributes(Graphics2D g) {
        Font font = new Font("Inter", Font.PLAIN, 10);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("" + currentSim.getStatus(), 90, 93);
        g.drawString("" + currentSim.getCurrentRoom().getName(), 670, 130);

        font = new Font("Inter", Font.PLAIN, 12);
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
        drawTime(g, GameTime.timeRemaining, 174, 0);
    }

    private void drawValue(Graphics2D g, int value, int offsetX, int offsetY) {
        Font font = new Font("Inter", Font.PLAIN, 9);

        g.setFont(font);
        if (value < 100) {
            g.drawString("" + value, offsetX, 177 + (37 * offsetY));
        } else {
            g.drawString("" + value, offsetX - 5, 177 + (37 * offsetY));
        }
    }

    private void drawTime(Graphics2D g, int value, int offsetX, int offsetY) {
        Font font = new Font("Inter", Font.PLAIN, 9);

        g.setFont(font);
        if (value < 100) {
            g.drawString("" + value, offsetX, 340 + (37 * offsetY));
        } else {
            g.drawString("" + value, offsetX - 5, 340 + (37 * offsetY));
        }
    }

    // ONLY FOR DEBUGGING
    public void drawMockup(Graphics2D g) {
        g.drawImage(mockup, 0, 0, null);
    }
}
