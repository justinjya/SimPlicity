package src.main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.world.House;
import src.world.Room;
import src.world.World;
import src.entities.interactables.Door;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.entities.sim.Store;
import src.main.menus.ActiveActionsMenu;
import src.main.menus.ChangeProfessionMenu;
import src.main.menus.GameMenu;
import src.main.menus.PauseMenu;
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
    private static boolean pause = false;
    private static boolean viewingActiveActions = false;
    private static boolean viewingProfessions = false;

    //ONLY FOR DEBUGGING
    private static Store store = new Store();
    private static boolean debug = false;

    // CONSTRUCTOR
    public UserInterface() {
    }

    // ONLY FOR DEBUGGING
    public static void initDebug(World world) {
        UserInterface.world = world;
        Room newRoom = new Room("First Room");
        newRoom.getListOfObjects().add(new Door(null));
        newRoom.getListOfObjects().get(0).setInteraction("view active actions");

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
        viewingWorld = true;
        tabbed = false;
        pause = false;
        viewingActiveActions = false;
        viewingProfessions = false;

        UserInterface.world = world;
        UserInterface.currentSim = world.getListOfSim().get(0);
        UserInterface.currentSimInventory = UserInterface.currentSim.getInventory();
        UserInterface.world.changeIsAddingState();
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

    public static boolean isPaused(){
        return pause;
    }

    public static boolean isViewingActiveActions() {
        return viewingActiveActions;
    }

    public static boolean isViewingProfessions() {
        return viewingProfessions;
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

    public static void viewWorld() {
        viewingWorld = !viewingWorld;
    }

    public static void tab() {
        if (pause) return;
        if (viewingActiveActions) return;
        if (viewingWorld) return;
        if (currentSimInventory.isOpen()) return;

        tabbed = !tabbed;
        currentSim.changeIsBusyState();
    }

    public static void pause() {
        if (tabbed) tab();
        pause = !pause;

        currentSim.changeIsBusyState();
    }


    public static void inventory() {
        if (pause) return;
        if (viewingWorld) return;
        if (viewingActiveActions) return;
        
        if (tabbed) {
            tab();
        }

        currentSimInventory.changeIsOpen();
        currentSim.changeIsBusyState();
    }

    public static void debug() {
        debug = !debug;
    }

    public static void viewActiveActions() {
        if (tabbed) tab();
        if (currentSimInventory.isOpen()) inventory();
        if (viewingProfessions) viewProfessions();

        viewingActiveActions = !viewingActiveActions;

        currentSim.changeIsBusyState();
    }

    public static void viewProfessions() {
        if (viewingActiveActions) viewActiveActions();
        if (tabbed) tab();
        if (currentSimInventory.isOpen()) inventory();

        viewingProfessions = !viewingProfessions;

        currentSim.changeIsBusyState();
    }

    // OTHERS
    public static void update() {
        if (tabbed && !currentSimInventory.isOpen()) {
            TabMenu.update();
        }

        if (currentSimInventory.isOpen()) {
            currentSimInventory.update();
        }

        if (pause) {
            PauseMenu.update();
        }

        if (viewingActiveActions) {
            ActiveActionsMenu.update();
        }

        if (viewingProfessions) {
            ChangeProfessionMenu.update();
        }

        // if (store.getIsOpen()) {
        //     store.update();
        // }
    }
    
    public static void draw(Graphics2D g) {
        GameMenu.draw(g);

        TabMenu.draw(g);

        currentSimInventory.draw(g);

        if (pause) {
            PauseMenu.draw(g);
        }

        if (viewingActiveActions) {
            ActiveActionsMenu.draw(g);
        }

        if (viewingProfessions) {
            ChangeProfessionMenu.draw(g);
        }

        // if (store.getIsOpen()) {
        //     store.draw(g);
        // }
    }

    public static void drawCenteredText(Graphics2D g, BufferedImage image, int x, int y, String str, Font f) {
        String text = str;
        Font font = f;
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int centerX = (image.getWidth() - textWidth) / 2;

        g.drawString(str, x + centerX, y);
    }

    public static void drawCenteredText(Graphics2D g, BufferedImage image, int x, int y, int offset, String str, Font f) {
        String text = str;
        Font font = f;
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int centerX = (image.getWidth() - textWidth) / 2;

        g.drawString(str, x + centerX + offset, y);
    }

    private static void drawText(Graphics2D g) {
        Font font;
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
}
