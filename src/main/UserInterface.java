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
import src.entities.sim.Store;
import src.main.menus.ActiveActionsMenu;
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

    // User Interface Images
    private static BufferedImage[] images = ImageLoader.loadGameMenu();

    //ONLY FOR DEBUGGING
    private static Store store = new Store();
    private static boolean debug = false;
    private static BufferedImage mockup = ImageLoader.readImage("menus/game_menu", "layout working", 1, 1, false);

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

    public static boolean isPaused(){
        return pause;
    }

    public static boolean isViewingActiveActions() {
        return viewingActiveActions;
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
        if (pause) return;
        if (viewingActiveActions) return;
        if (viewingWorld) return;
        if (currentSimInventory.isOpen()) return;

        UserInterface.tabbed = !UserInterface.tabbed;
        currentSim.changeIsBusyState();
    }

    public static void pause() {
        pause = !pause;

        currentSim.changeIsBusyState();
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

    public static void viewActiveActions() {
        viewingActiveActions = !viewingActiveActions;

        currentSim.changeIsBusyState();
    }

    // OTHERS
    public static void update() {
        // if (tabbed && !currentSimInventory.isOpen()) {
        //     TabMenu.update();
        // }

        // if (currentSimInventory.isOpen()) {
        //     currentSimInventory.update();
        // }

        // if (pause) {
        //     PauseMenu.update();
        // }

        // if (viewingActiveActions) {
        //     ActiveActionsMenu.update();
        // }

        if (store.getIsOpen()) {
            store.update();
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

        // GameMenu.draw(g);

        // TabMenu.draw(g);

        // currentSimInventory.draw(g);

        // if (pause) {
        //     PauseMenu.draw(g);
        // }

        // if (viewingActiveActions) {
        //     ActiveActionsMenu.draw(g);
        // }

        if (store.getIsOpen()) {
            store.draw(g);
        }
    }

    public static void drawCenteredText(Graphics2D g, BufferedImage image, int x, int y, String str, Font f) {
        String text = str;
        Font font = f;
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text);
        int centerX = (image.getWidth() - textWidth) / 2;

        g.drawString(str, x + centerX, y);
    }

    public void drawPause(Graphics2D g){
        if(pause){
            currentSim.changeIsBusyState();
        }
        else{
            draw(g);
        }
    }

    private static void drawText(Graphics2D g) {
        Font font;
        g.setColor(Color.BLACK);

        font = new Font("Inter", Font.BOLD, 13);
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

    // ONLY FOR DEBUGGING
    public static void drawMockup(Graphics2D g) {
        g.drawImage(mockup, 0, 0, null);
    }
}
