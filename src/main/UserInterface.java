package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.world.House;
import src.world.Room;
import src.world.World;
import src.assets.ImageLoader;
import src.entities.interactables.Door;
import src.entities.interactables.Interactables;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.main.menus.GameMenu;
import src.main.menus.TabMenu;

public class UserInterface {
    public static UserInterface ui = new UserInterface();
    
    // Attributes
    private static World world;
    private static Sim currentSim;
    private static Inventory currentSimInventory;
    
    // User Interface States
    private static boolean viewingWorld = false;
    private static boolean tabbed = false;
    private boolean pause = false;

    // User Interface Images
    private static BufferedImage[] images = ImageLoader.loadGameMenu();

    //ONLY FOR DEBUGGING
    private static boolean debug = false;
    private static BufferedImage mockup = ImageLoader.readImage("menus/game_menu", "layout inventory", 1, 1, false);

    // CONSTRUCTOR
    public UserInterface() {
    }

    // ONLY FOR DEBUGGING
    public static void initDebug(World world) {
        UserInterface.world = world;
        Room newRoom = new Room("First Room");
        newRoom.getListOfObjects().add(new Door(null));

        House newHouse = new House(16, 16, world, world.getListOfSim().get(0), newRoom);
        newRoom.setHouseInsideOf(newHouse);
        world.getListOfHouse().add(newHouse);

        UserInterface.currentSim = world.getListOfSim().get(0);
        UserInterface.currentSimInventory = UserInterface.currentSim.getInventory();
        UserInterface.currentSim.setCurrentHouse(world.getListOfHouse().get(0));
        UserInterface.currentSim.setCurrentRoom(newRoom);
        viewingWorld = false;
    }

    public static void init(World world) {
        UserInterface.world = world;
        UserInterface.currentSim = world.getListOfSim().get(0);
        UserInterface.currentSimInventory = UserInterface.currentSim.getInventory();
        UserInterface.world.changeIsAddingState();
        viewingWorld = true;
    }

    // GETTERS
    public static UserInterface getInstance() {
        return ui;
    }

    public static World getWorld() {
        return world;
    }

    public static Sim getCurrentSim() {
        return currentSim;
    }

    public static boolean isViewingWorld() {
        return viewingWorld;
    }

    public static boolean isTabbed() {
        return tabbed;
    }

    public boolean isPause(){
        return pause;
    }

    // SETTERS
    public static void setCurrentSim(Sim sim) {
        currentSim = sim;
        currentSimInventory = currentSim.getInventory();

        if (currentSim.isBusy()) currentSim.changeIsBusyState();

        for (Sim s : world.getListOfSim()) {
            if (s == currentSim) continue;

            if (!s.isBusy()) s.changeIsBusyState();
        }
    }

    public static void changeIsViewingWorldState() {
        viewingWorld = !viewingWorld;
    }

    public static void tab() {
        if (!currentSimInventory.isOpen()) {
            UserInterface.tabbed = !UserInterface.tabbed;
            currentSim.changeIsBusyState();
        }
    }

    public void pause() {
        this.pause = !this.pause;
    }


    public static void inventory() {
        if (tabbed) {
            tab();
        }

        currentSimInventory.changeIsOpen();
        currentSim.changeIsBusyState();
    }

    public static void debug() {
        debug = !debug;
    }

    // OTHERS
    public static void update() {
        if (tabbed && !currentSimInventory.isOpen()) {
            TabMenu.update();
        }

        if (currentSimInventory.isOpen()) {
            currentSimInventory.update();
        }
    }
    
    public static void draw(Graphics2D g) {
        // if (viewingWorld) {
            // Font font;
            // g.setColor(Color.WHITE);

            // font = new Font("Inter", Font.PLAIN, 12);

            // g.setFont(font);
            // g.drawString("press shift to switch cursor movement", Consts.CENTER_X - 100, 25);
        // }
        // else {
        //     drawUI(g);
        // }

        GameMenu.draw(g);

        currentSimInventory.draw(g);

        TabMenu.draw(g);
    }

    public static void centerText(Graphics2D g, BufferedImage image, int x, int y, String str, Font f) {
        String text = str;
        Font font = f;
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int centerX = (image.getWidth() - textWidth) / 2;

        g.drawString(str, x + centerX, y);
    }

    public static void addCenteredText(BufferedImage image, String text) {
        Graphics2D g2d = image.createGraphics();
        FontMetrics fm = g2d.getFontMetrics();
    
        int x = (image.getWidth() - fm.stringWidth(text)) / 2;
        int y = (image.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
    
        g2d.drawString(text, x, y);
        g2d.dispose();
    }
    

    public void drawPause(Graphics2D g){
        if(pause){
            currentSim.changeIsBusyState();
        }
        else{
            draw(g);
        }
    }

    private static void drawUI(Graphics2D g) {
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
       TabMenu.draw(g);
    }

    private static void drawText(Graphics2D g) {
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
            if (!tabbed && currentSim.getInteractionHandler().isObjectInRange() && currentSim.isStatusCurrently("Idle")) {
                Interactables object = currentSim.getInteractionHandler().getInteractableObject();
                if (object != null) {
                    g.drawString("Press F to Interact with " + object.getName(), Consts.CENTER_X - 72, Consts.CENTER_Y + 172);
                }
            }
        }
        catch (NullPointerException e) {}

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

    private static void drawAttributes(Graphics2D g) {
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

    private static void drawValue(Graphics2D g, int value, int offsetX, int offsetY) {
        if (value < 100) {
            g.drawString("" + value, offsetX, 188 + (36 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 188 + (36 * offsetY));
        }
    }

    private static void drawTime(Graphics2D g, int value, int offsetX, int offsetY) {
        if (value < 100) {
            g.drawString("" + value, offsetX, 340 + (37 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 340 + (37 * offsetY));
        }
    }

    // ONLY FOR DEBUGGING
    public static void drawMockup(Graphics2D g) {
        g.drawImage(mockup, 0, 0, null);
    }
}
