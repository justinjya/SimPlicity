package src.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.GameTime;
import src.assets.ImageLoader;
import src.main.UserInterface;
import src.entities.sim.Sim;
import src.world.House;
import src.world.Room;

public class GameMenu {
    private static BufferedImage[] images = ImageLoader.loadGameMenu();

    private static BufferedImage doubleInfoBox = images[0];
    private static BufferedImage moneyBox = images[1];
    private static BufferedImage simInfoBox = images[2];
    private static BufferedImage dayBox = images[3];
    private static BufferedImage healthIcon = images[4];
    private static BufferedImage hungerIcon = images[5];
    private static BufferedImage moodIcon = images[6];
    private static BufferedImage helpBox = images[7];

    public static void draw(Graphics2D g) {
        Sim currentSim = UserInterface.getCurrentSim();

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
        pressEscapeToPause(g);

        // working and exercising tab
        if (currentSim.isStatusCurrently("Working")) {
            drawWorkingTab(g);
        }
        if (currentSim.isStatusCurrently("Exercising")) {
            drawExercisingTab(g);
        }
    }

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
        Room  currentRoom = currentSim.getCurrentRoom();

        Font font = new Font("Inter", Font.BOLD, 14);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        UserInterface.drawCenteredText(g, doubleInfoBox, 7, 70, currentSim.getName(), font);
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
        UserInterface.drawCenteredText(g, doubleInfoBox, 7, 93, currentSim.getStatus(), font);
        UserInterface.drawCenteredText(g, doubleInfoBox, 603, 130, currentRoom.getName(), font);

        g.setColor(Color.WHITE);
        font = new Font("Inter", Font.PLAIN, 8);
        g.setFont(font);
        drawValue(g, currentSim.getHealth(), 175, 0);
        drawValue(g, currentSim.getHunger(), 175, 1);
        drawValue(g, currentSim.getMood(), 175, 2);
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

    private static void drawTimeRemaining(Graphics2D g, int value, int offsetX, int offsetY) {
        g.setColor(Color.WHITE);
        Font font = new Font("Inter", Font.PLAIN, 8);
        g.setFont(font);

        if (value < 100) {
            g.drawString("" + value, offsetX, 354 + (36 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 354 + (36 * offsetY));
        }
    }

    private static void drawWorkingTab(Graphics2D g) {
        BufferedImage[] images = ImageLoader.loadWorkingTab();
        BufferedImage workingBox = images[0];
        BufferedImage iconWork = images[1];

        g.drawImage(workingBox, 3, 278, null);
        g.drawImage(iconWork, 17, 317, null);

        g.setColor(Color.WHITE);
        Font font = new Font("Inter", Font.BOLD, 12);
        g.setFont(font);

        g.drawString("Working", 51, 335);
        drawBarValue(g, 51, 339, 135, 100, 300);
        drawTimeRemaining(g, 100, 175, 0); // TO - DO!!! : ACTUALLY ADD DURATION VALUE
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

        g.drawString("Exercising", 51, 335);
        drawBarValue(g, 51, 339, 135, 100, 300);
        drawTimeRemaining(g, 100, 175, 0); // TO - DO!!! : ACTUALLY ADD DURATION VALUE
    }
}
