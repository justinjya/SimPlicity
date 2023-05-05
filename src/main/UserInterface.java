package src.main;

import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

import src.assets.ImageLoader;
import src.main.menus.TabMenu;
import src.main.menus.GameMenu;
import src.main.menus.PauseMenu;
import src.main.menus.InteractMenu;
import src.main.menus.GameOverMenu;
import src.main.menus.ListOfSimsMenu;
import src.main.menus.UpgradeHouseMenu;
import src.main.menus.ActiveActionsMenu;
import src.main.menus.ChangeProfessionMenu;
import src.entities.interactables.Door;
import src.entities.sim.Inventory;
import src.items.foods.BakedFood;
import src.entities.sim.Store;
import src.entities.sim.Sim;
import src.world.World;
import src.world.House;
import src.world.Room;

public class UserInterface {
    public static UserInterface ui = new UserInterface();
    
    // Attributes
    private static World world;
    private static Sim currentSim;
    private static Inventory currentSimInventory;
    
    // User Interface States
    private static boolean tabbed = false;
    private static boolean pause = false;
    private static boolean viewingWorld = false;
    private static boolean viewingTime = false;
    private static boolean viewingActiveActions = false;
    private static boolean viewingProfessions = false;
    private static boolean viewingListOfSims = false;
    private static boolean upgradingHouse = false;
    private static boolean viewingInteractions = false;
    private static boolean showingGameOver = false;

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
        world.getListOfHouse().add(newHouse);

        UserInterface.setCurrentSim(world.getListOfSim().get(0));
        UserInterface.currentSim.setMoney(10000);
        UserInterface.currentSim.setCurrentHouse(newHouse);
        UserInterface.currentSim.setCurrentRoom(newRoom);

        // testing game over
        // UserInterface.currentSim.setHealth(1);
        // UserInterface.currentSim.setHunger(1);
        // UserInterface.currentSim.setMood(1);
        GameTime.incrementDay();

        viewingTime = false;
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

    public static boolean isTabbed() {
        return tabbed;
    }

    public static boolean isPaused(){
        return pause;
    }

    public static boolean isViewingWorld() {
        return viewingWorld;
    }

    public static boolean isViewingTime() {
        return viewingTime;
    }

    public static boolean isViewingActiveActions() {
        return viewingActiveActions;
    }

    public static boolean isViewingProfessions() {
        return viewingProfessions;
    }

    public static boolean isViewingListOfSims() {
        return viewingListOfSims;
    }

    public static boolean isUpgradingHouse() {
        return upgradingHouse;
    }

    public static boolean isViewingInteractions() {
        return viewingInteractions;
    }

    public static boolean isShowingGameOver() {
        return showingGameOver;
    }

    // SETTERS
    public static void setCurrentSim(Sim sim) {
        currentSim = sim;
        currentSimInventory = currentSim.getInventory();
    }

    public static void tab() {
        if (pause) return;
        if (upgradingHouse) return;

        tabbed = !tabbed;
        if (currentSim.isStatusCurrently("Idle")) {
            currentSim.changeIsBusyState();
        }
    }

    public static void pause() {
        if (tabbed) tab();
        pause = !pause;

        if (currentSim.isStatusCurrently("Idle")) {
            currentSim.changeIsBusyState();
        }
    }

    public static void viewWorld() {
        viewingWorld = !viewingWorld;
    }

    public static void viewTime() {
        viewingTime = !viewingTime;
    }

    public static void inventory() {
        if (tabbed) tab();
        if (viewingInteractions) return;

        currentSimInventory.changeIsOpen();
        currentSim.changeIsBusyState();
    }

    public static void viewActiveActions() {
        viewingActiveActions = !viewingActiveActions;

        currentSim.changeIsBusyState();
    }

    public static void viewProfessions() {
        viewingProfessions = !viewingProfessions;

        currentSim.changeIsBusyState();
    }

    public static void viewListOfSims() {
        ListOfSimsMenu.reset();
        viewingListOfSims = !viewingListOfSims;

        currentSim.changeIsBusyState();
    }

    public static void upgradeHouse() {
        UpgradeHouseMenu.reset();
        upgradingHouse = !upgradingHouse;

        currentSim.changeIsBusyState();
    }

    public static void viewInteractions() {
        viewingInteractions = !viewingInteractions;

        currentSim.changeIsBusyState();
    }

    public static void showGameOver() {
        showingGameOver = !showingGameOver;

        currentSim.changeIsBusyState();
    }

    public static void drawCantCookUI(Graphics2D g) {
        BufferedImage[] images = ImageLoader.loadWarningBox();
        g.drawImage(images[0], 275, 180, null);
        // g.setColor(Color.WHITE);
        // g.fillRect(350, 250, 100, 100);
        // g.setColor(Color.BLACK);
        g.drawString("INGREDIENTS NOT AVAILABLE", 295, 230);
        g.drawString("PRESS ESC TO CONTINUE", 295, 260);

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
    public static boolean getIsViewingRecipes() {
        return isViewingRecipes;
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
    private static void drawCookUI(Graphics2D g) { 
        // set the position and dimensions
        int frameX = 607;
        int frameY = 214;
        int frameWidth = 182;
        int frameHeight = 262;

        int boxWidth = (slotSize * 3) + 20;
        int boxHeight = (slotSize * 4) + 20;
        int boxX = frameX + ((frameWidth - boxWidth) / 2);
        int boxY = frameY + ((frameHeight - boxHeight) / 2);

        int slotXstart = boxX + ((boxWidth - (slotSize * 3)) / 2);
        int slotYstart = boxY + ((boxHeight - (slotSize * 4)) / 2);
        int slotX = slotXstart;
        int slotY = slotYstart;

        // initialize available baked food
        BakedFood almondMilk = new BakedFood(0);
        BakedFood chickenAndRice = new BakedFood(1);
        BakedFood curryAndRice = new BakedFood(2);
        BakedFood cutVegetables = new BakedFood(3);
        BakedFood steak = new BakedFood(4);

        BufferedImage[] images = ImageLoader.loadMenuBook();
        // draw the title "Cook" box
        g.drawImage(images[1], 600, 187, null);
        // g.fillRect(607, 183, 182, 26);
        // g.drawString("Cook", 668, 201);

        
        // draw the frame
        g.drawImage(images[0], frameX, frameY, null);
        // g.setColor(Color.GRAY);
        // g.fillRect(frameX, frameY, frameWidth, frameHeight);

        // draw the box
        g.drawImage(images[2], boxX, boxY, null);
        // g.setColor(Color.DARK_GRAY);
        // g.fillRect(boxX, boxY, boxWidth, boxHeight);

        // draw image of available baked food
        g.drawImage(almondMilk.getIcon(), slotX, slotY, null);
        g.drawImage(chickenAndRice.getIcon(), slotX+slotSize, slotY, null);
        g.drawImage(curryAndRice.getIcon(), slotX+2*slotSize, slotY, null);
        g.drawImage(cutVegetables.getIcon(), slotX, slotY+slotSize, null);
        g.drawImage(steak.getIcon(), slotX+slotSize, slotY+slotSize, null);

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
            g.drawString("Almond Milk", frameX + 65, frameY + 20);
        }
        else if (slotRow == 0 && slotColumn == 1) {
            g.drawString("Chicken and Rice", frameX + 50, frameY + 20);
        }
        else if (slotRow == 0 && slotColumn == 2) {
            g.drawString("Curry and Rice", frameX + 55, frameY + 20);
        }
        else if (slotRow == 1 && slotColumn == 0) {
            g.drawString("Cut Vegetables", frameX + 55, frameY + 20);
        }
        else if (slotRow == 1 && slotColumn == 1) {
            g.drawString("Steak", frameX + 78, frameY + 20);
        }

    }

    // ONLY FOR DEBUGGING
    public static boolean isDebug() {
        return debug;
    }

    public static void debug() {
        debug = !debug;
    }

    // OTHERS
    public static void update() {
        updateListOfSims();

        if (showingGameOver) {
            GameOverMenu.update();
        }

        if (currentSimInventory.isOpen() && !pause) {
            currentSimInventory.update();
        }

        if (tabbed && !currentSimInventory.isOpen()) {
            TabMenu.update();
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

        if (viewingListOfSims) {
            ListOfSimsMenu.update();
        }

        if (viewingInteractions) {
            InteractMenu.update();
        }

        if (store.getIsOpen()) {
            store.update();
        }
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

        if (viewingListOfSims) {
            ListOfSimsMenu.draw(g);
        }

        if (upgradingHouse) {
            UpgradeHouseMenu.draw(g);
        }

        if (viewingInteractions) {
            InteractMenu.draw(g);
        }

        if (showingGameOver) {
            GameOverMenu.draw(g);
        }

        if (isViewingRecipes) {
            UserInterface.drawCookUI(g);
        }

        if (!isAbleToCook) {
            UserInterface.drawCantCookUI(g);
        }

        if (store.getIsOpen()) {
            store.draw(g);
        }
    }

    private static void updateListOfSims() {
        World world = UserInterface.getWorld();
        ArrayList<Sim> listOfSims = world.getListOfSim();
        try {
            for (Sim sim : listOfSims) {
                if (sim.getHealth() > 0 && sim.getHunger() > 0 && sim.getMood() > 0) continue;
    
                if (sim.getHealth() <= 0) GameOverMenu.setDeathMessage(sim.getName() + " passed away due to being very ill");
                if (sim.getHunger() <= 0) GameOverMenu.setDeathMessage(sim.getName() + " starved to death");
                if (sim.getMood() <= 0) GameOverMenu.setDeathMessage(sim.getName() + " died of depression");

                sim.setHealth(0);
                sim.setHunger(0);
                sim.setMood(0);

                listOfSims.remove(sim);
                if (!UserInterface.isShowingGameOver()) UserInterface.showGameOver();
            }
        }
        catch (ConcurrentModificationException cme) {}
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
}
