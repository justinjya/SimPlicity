package src.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.GameTime;
import src.assets.ImageLoader;
import src.main.UserInterface;
import src.entities.handlers.InteractionHandler;
import src.entities.interactables.Interactables;
import src.entities.sim.Sim;
import src.world.House;
import src.world.Room;

public class GameMenu {
    // Load the images for the menu
    private static BufferedImage[] images = ImageLoader.loadGameMenu();
    private static BufferedImage doubleInfoBox = images[0];
    private static BufferedImage moneyBox = images[1];
    private static BufferedImage simInfoBox = images[2];
    private static BufferedImage dayBox = images[3];
    private static BufferedImage healthIcon = images[4];
    private static BufferedImage hungerIcon = images[5];
    private static BufferedImage moodIcon = images[6];
    private static BufferedImage helpBox = images[7];

    // Attributes to make showing it easier
    public static int workDuration = 0;
    public static int exerciseDration = 0;

    public static void draw(Graphics2D g) {
        Sim currentSim = UserInterface.getCurrentSim();
        InteractionHandler simInteract = currentSim.getInteractionHandler();
        boolean objectInRange = simInteract.isObjectInRange();

        // boxes
        drawBoxes(g);

        // icons
        drawIcons(g);

        // game info and values
        drawTexts(g);
        drawSimInfoBarValues(g);

        // press ? for help
        pressQuestionMarkForHelp(g);

        // press esc to pause the game
        if (!UserInterface.isTabbed() && !objectInRange) {
            pressEscapeToPause(g);
        }

        // press f to interact
        if (!UserInterface.isTabbed() && objectInRange) {
            drawObjectToInteract(g);
        }

        // working and exercising tab
        if (currentSim.isStatusCurrently("Working")) {
            drawWorkingTab(g);
        }
        if (currentSim.isStatusCurrently("Exercising")) {
            drawExercisingTab(g);
        }

        if (UserInterface.isViewingTime()) {
            drawTimeRemainingTab(g);
        }

        // ONLY FOR DEBUGGING
        if (UserInterface.isDebug()) {
            drawDebug(g);
        }
    }

    // MAIN GAME MENU
    private static void drawValue(Graphics2D g, int value, int offsetX, int offsetY) {
        if (value < 100) {
            g.drawString("" + value, offsetX, 188 + (36 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 188 + (36 * offsetY));
        }
    }

    private static void drawBoxes(Graphics2D g) {
        g.drawImage(doubleInfoBox, 3, 49, null); // double sim info box
        g.drawImage(moneyBox, 7, 105, null); // money box
        g.drawImage(simInfoBox, 7, 143, null); // sim info box
        g.drawImage(dayBox, 603, 47, null); // day box
        g.drawImage(doubleInfoBox, 599, 87, null); // double house info box
        g.drawImage(helpBox, 305, 0, null); // help box
    }

    private static void drawIcons(Graphics2D g) {
        g.drawImage(healthIcon, 17, 153, null); // health icon
        g.drawImage(hungerIcon, 17, 188, null); // hunger icon
        g.drawImage(moodIcon, 17, 224, null); // mood icon
    }

    private static void drawTexts(Graphics2D g) {
        Sim currentSim = UserInterface.getCurrentSim();
        House currentHouse = currentSim.getCurrentHouse();
        Room currentRoom = currentSim.getCurrentRoom();

        Font font = new Font("Inter", Font.BOLD, 14);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        UserInterface.drawCenteredText(g, doubleInfoBox, 6, 70, currentSim.getName(), font);
        UserInterface.drawCenteredText(g, dayBox, 603, 69, "Day " + GameTime.day, font);
        UserInterface.drawCenteredText(g, doubleInfoBox, 599, 108, currentHouse.getName(), font);
        
        font = new Font("Inter", Font.BOLD, 12);
        g.setFont(font);
        UserInterface.drawCenteredText(g, moneyBox, 7, 125, "$ " + currentSim.getMoney(), font);
        g.drawString("Health", 51, 169);
        g.drawString("Hunger", 51, 205);
        g.drawString("Mood", 51, 241);

        g.setColor(new Color(61, 30, 45)); 
        g.setFont(new Font("Inter", Font.BOLD, 10));
        UserInterface.drawCenteredText(g, doubleInfoBox, 6, 93, currentSim.getStatus(), font);
        UserInterface.drawCenteredText(g, doubleInfoBox, 603, 130, currentRoom.getName(), font);

        g.setColor(Color.WHITE);
        font = new Font("Inter", Font.PLAIN, 8);
        g.setFont(font);
        drawValue(g, currentSim.getHealth(), 175, 0);
        drawValue(g, currentSim.getHunger(), 175, 1);
        drawValue(g, currentSim.getMood(), 175, 2);
    }

    private static void drawObjectToInteract(Graphics2D g) {
        BufferedImage windowArea = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Sim currentSim = UserInterface.getCurrentSim();
        InteractionHandler simInteract = currentSim.getInteractionHandler();

        if (currentSim.isStatusCurrently("Idle") && simInteract.isObjectInRange() &&
            !UserInterface.isTabbed() && !UserInterface.isViewingInteractions()) {
            Interactables object = simInteract.getInteractableObject();
            Font font = new Font("Inter", Font.PLAIN, 12);
            g.setFont(font);
            g.setColor(Color.BLACK);
            UserInterface.drawCenteredText(g, windowArea, 0, 468, "press f to " + object.getInteraction(), font);
        }
    }

    private static void drawBarValue(Graphics2D g, int x, int y, int barWidth, int value, int maxValue) {
        float valuePercentage = (float) value / (float) maxValue;
        int valueBar = (int) (barWidth * valuePercentage);

        g.setColor(new Color(61, 30, 45));
        g.fillRoundRect(x, y, barWidth, 6, 4, 4);

        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, valueBar, 6, 4, 4);
    }

    private static void drawSimInfoBarValues(Graphics2D g) {
        Sim currentSim = UserInterface.getCurrentSim();

        drawBarValue(g, 51, 173, 135, currentSim.getHealth(), 100);
        drawBarValue(g, 51, 209, 135, currentSim.getHunger(), 100);
        drawBarValue(g, 51, 245, 135, currentSim.getMood(), 100);
    }

    private static void pressQuestionMarkForHelp(Graphics2D g) {
        g.setFont(new Font("Inter", Font.PLAIN, 10));
        g.drawString("press", 361, 14);
        g.drawString("for help", 400, 14);
        g.setFont(new Font("Inter", Font.BOLD, 10));
        g.drawString("?", 391, 14);
    }

    private static void pressEscapeToPause(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Inter", Font.PLAIN, 12));
        g.drawString("press", 317, 468);
        g.drawString("to pause the game", 376, 468);
        g.setFont(new Font("Inter", Font.BOLD, 12));
        g.drawString("esc", 352, 468);
    }

    // private static void pressEscapeTo(Graphics2D g) {
    //     g.setColor(Color.BLACK);
    //     g.setFont(new Font("Inter", Font.PLAIN, 12));
    //     g.drawString("press", 317, 468);
    //     g.drawString("to pause the game", 376, 468);
    //     g.setFont(new Font("Inter", Font.BOLD, 12));
    //     g.drawString("esc", 352, 468);
    // }

    private static void drawTimeRemainingValue(Graphics2D g, int value, int offsetX, int offsetY) {
        g.setColor(Color.WHITE);
        Font font = new Font("Inter", Font.PLAIN, 8);
        g.setFont(font);

        if (value < 100) {
            g.drawString("" + value, offsetX, 354 + (39 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 354 + (39 * offsetY));
        }
    }

    // TABS
    private static void drawWorkingTab(Graphics2D g) {
        BufferedImage[] images = ImageLoader.loadWorkingTab();
        BufferedImage workingBox = images[0];
        BufferedImage iconWork = images[1];

        g.drawImage(workingBox, 3, 278, null);
        g.drawImage(iconWork, 17, 317, null);

        g.setColor(Color.WHITE);
        Font font = new Font("Inter", Font.BOLD, 12);
        g.setFont(font);

        UserInterface.drawCenteredText(g, workingBox, 4, 298, "Working", font);

        int timeRemaining = GameTime.getDecrements();
        g.drawString("Working", 51, 335);
        drawBarValue(g, 51, 339, 135, timeRemaining, workDuration);
        drawTimeRemainingValue(g, timeRemaining, 175, 0);
    }

    private static void drawExercisingTab(Graphics2D g) {
        BufferedImage[] images = ImageLoader.loadExercisingTab();
        BufferedImage exercisingBox = images[0];
        BufferedImage iconExercise = images[1];

        g.drawImage(exercisingBox, 3, 278, null);
        g.drawImage(iconExercise, 17, 317, null);

        g.setColor(Color.WHITE);
        Font font = new Font("Inter", Font.BOLD, 12);
        g.setFont(font);

        UserInterface.drawCenteredText(g, exercisingBox, 4, 298, "Exercising", font);

        int timeRemaining = GameTime.getDecrements();
        g.drawString("Exercising", 51, 335);
        drawBarValue(g, 51, 339, 135, timeRemaining, exerciseDration);
        drawTimeRemainingValue(g, timeRemaining, 175, 0);
    }

    private static void drawTimeRemainingTab(Graphics2D g) {
        BufferedImage[] images = ImageLoader.loadTimeRemainingTab();
        BufferedImage timeRemainingBox = images[0];
        BufferedImage iconTime = images[1];
        BufferedImage iconBuild = images[2];

        int i = 1;

        g.drawImage(timeRemainingBox, 7, 278, null);
        g.drawImage(iconTime, 17, 317, null);
        g.drawImage(iconBuild, 17, 317 + (i * 39), null);

        g.setColor(Color.WHITE);
        Font font = new Font("Inter", Font.BOLD, 12);
        g.setFont(font);

        UserInterface.drawCenteredText(g, timeRemainingBox, 7, 298, "Time Remaining", font);

        int timeRemaining = GameTime.getTimeRemaining();
        g.drawString("Time", 51, 334);
        g.drawString("Build Room", 51, 334 + (i * 39));

        drawBarValue(g, 51, 338, 135, timeRemaining, GameTime.initialTimeRemaining);
        drawBarValue(g, 51, 338 + (i * 39), 135, timeRemaining, GameTime.initialTimeRemaining);
        drawTimeRemainingValue(g, timeRemaining, 175, 0);

        drawTimeRemainingValue(g, timeRemaining, 175, 1);
    }

    public static void drawDebug(Graphics2D g) {
        Sim currentSim = UserInterface.getCurrentSim();

        g.setColor(Color.BLACK);
        Font font = new Font("Inter", Font.PLAIN, 10);
        g.setFont(font);

        g.drawString("timeRemaining: " + GameTime.timeRemaining, 33, 364);
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
