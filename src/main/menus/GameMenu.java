package src.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.GameTime;
import src.main.UserInterface;

public class GameMenu {
    private static BufferedImage[] images = ImageLoader.loadGameMenu();

    public static void draw(Graphics2D g) {
        Sim currentSim = UserInterface.getCurrentSim();
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Inter", Font.BOLD, 14));

        // boxes
        g.drawImage(images[0], 3, 49, null); // double sim info box
        g.drawImage(images[1], 7, 105, null); // money box
        g.drawImage(images[2], 7, 143, null); // sim info box
        g.drawImage(images[3], 603, 47, null); // day box
        g.drawImage(images[0], 599, 87, null); // double house info box
        g.drawImage(images[7], 305, 0, null); // help box

        g.drawImage(images[4], 17, 153, null); // health icon
        g.drawImage(images[5], 17, 188, null); // hunger icon
        g.drawImage(images[6], 17, 224, null); // mood icon

        // text
        g.drawString(currentSim.getName(), 83, 70);
        // UserInterface.addCenteredText(images[1], "$ " + currentSim.getMoney());
        // UserInterface.centerText(g, images[0], 3, 49, currentSim.getName());
        g.drawString("Day " + GameTime.day, 675, 69);
        g.drawString(currentSim.getCurrentHouse().getName(), 650, 108);

        g.setFont(new Font("Inter", Font.BOLD, 12));
        // UserInterface.centerText(g, images[1], 7, 105, "$ " + currentSim.getMoney());
        g.drawString("$ " + currentSim.getMoney(), 84, 125);
        g.drawString("Health", 49, 170);
        g.drawString("Hunger", 49, 206);
        g.drawString("Mood", 49, 242);
        
        g.setColor(new Color(61, 30, 45)); 
        g.setFont(new Font("Inter", Font.BOLD, 10));
        g.drawString("" + currentSim.getStatus(), 90, 93);
        g.drawString("" + currentSim.getCurrentRoom().getName(), 670, 130);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Inter", Font.PLAIN, 8));
        drawValue(g, currentSim.getHealth(), 175, 0);
        drawValue(g, currentSim.getHunger(), 175, 1);
        drawValue(g, currentSim.getMood(), 175, 2);

        g.setFont(new Font("Inter", Font.PLAIN, 10));
        g.drawString("press", 361, 14);
        g.drawString("for help", 400, 14);
        g.setFont(new Font("Inter", Font.BOLD, 10));
        g.drawString("?", 391, 14);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Inter", Font.PLAIN, 12));
        g.drawString("press", 317, 468);
        g.drawString("to pause the game", 376, 468);
        g.setFont(new Font("Inter", Font.BOLD, 12));
        g.drawString("esc", 352, 468);
    }

    private static void drawValue(Graphics2D g, int value, int offsetX, int offsetY) {
        if (value < 100) {
            g.drawString("" + value, offsetX, 188 + (36 * offsetY));
        }
        else {
            g.drawString("" + value, offsetX - 5, 188 + (36 * offsetY));
        }
    }
}
