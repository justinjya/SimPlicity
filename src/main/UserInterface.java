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
import src.main.menus.RecipeBookMenu;
import src.main.menus.UpgradeHouseMenu;
import src.main.menus.ActiveActionsMenu;
import src.main.menus.ChangeProfessionMenu;
import src.entities.interactables.Door;
import src.entities.sim.Inventory;
import src.items.foods.BakedFood;
import src.entities.sim.Sim;
import src.main.menus.Store;
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
    private static boolean help = false;
    private static boolean tabbed = false;
    private static boolean pause = false;
    private static boolean viewingWorld = false;
    private static boolean viewingTime = false;
    private static boolean viewingActiveActions = false;
    private static boolean viewingProfessions = false;
    private static boolean viewingListOfSims = false;
    private static boolean viewingInteractions = false;
    private static boolean viewingStore = false;
    private static boolean viewingRecipes = false;
    private static boolean upgradingHouse = false;
    private static boolean showingGameOver = false;

    //ONLY FOR DEBUGGING
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
        UserInterface.currentSim.setMoney(500);
        UserInterface.currentSim.setCurrentHouse(newHouse);
        UserInterface.currentSim.setCurrentRoom(newRoom);

        // testing game over
        // UserInterface.currentSim.setHealth(1);
        // UserInterface.currentSim.setHunger(1);
        // UserInterface.currentSim.setMood(1);
        GameTime.incrementDay();
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

    public static boolean isHelped() {
        return help;
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

    public static boolean isViewingInteractions() {
        return viewingInteractions;
    }

    public static boolean isViewingStore() {
        return viewingStore;
    }

    public static boolean isViewingRecipes() {
        return viewingRecipes;
    }

    public static boolean isUpgradingHouse() {
        return upgradingHouse;
    }

    public static boolean isShowingGameOver() {
        return showingGameOver;
    }

    // SETTERS
    public static void setCurrentSim(Sim sim) {
        currentSim = sim;
        currentSimInventory = currentSim.getInventory();
    }

    public static void help() {
        help = !help;
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

    public static void viewInteractions() {
        viewingInteractions = !viewingInteractions;

        currentSim.changeIsBusyState();
    }

    public static void viewStore() {
        viewingStore = !viewingStore;

        currentSim.changeIsBusyState();
    }

    public static void viewRecipes() {
        viewingRecipes = !viewingRecipes;

        currentSim.changeIsBusyState();
    }

    public static void upgradeHouse() {
        UpgradeHouseMenu.reset();
        upgradingHouse = !upgradingHouse;

        currentSim.changeIsBusyState();
    }

    public static void showGameOver() {
        showingGameOver = !showingGameOver;

        currentSim.changeIsBusyState();
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

        if (viewingRecipes) {
            RecipeBookMenu.update();
        }

        if (viewingStore) {
            Store.update();
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

        if (viewingRecipes) {
            RecipeBookMenu.draw(g);
        }

        if (viewingStore) {
            Store.draw(g);
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
