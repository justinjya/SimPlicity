package src.main.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.main.KeyHandler;

public class PauseMenu {
    private static int selectedBox = 0;

    private static BufferedImage[] images = ImageLoader.loadPause();
    private static BufferedImage background = images[0];
    private static BufferedImage help = images[1];
    private static BufferedImage saveAndExit = images[2];
    private static BufferedImage helpHighlighted = images[3];
    private static BufferedImage saveAndExitHighlighted = images[4]; 

    public static void update(){
        int newSelectedBox = selectedBox;
        if(KeyHandler.isKeyPressed(KeyHandler.KEY_S)){
            newSelectedBox++;
        }
        if(KeyHandler.isKeyPressed(KeyHandler.KEY_W)){
            newSelectedBox--;
        }
        if (newSelectedBox >= 0 && newSelectedBox < 2) {
            selectedBox = newSelectedBox;
        }
    }
    
    public static void draw(Graphics2D g) {
        // g.setColor(new Color(110, 196, 213, 100));
        // g.setColor(new Color(110, 196, 213));
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, 800, 600);

        g.drawImage(background, 233, 60, null); // Background

        g.drawImage(help, 269, 167, null); // Help button
        g.drawImage(saveAndExit, 269, 262, null); // Save and Exit button
        
        if (selectedBox == 0) {
            g.drawImage(helpHighlighted, 265, 163, null);
        }
        if (selectedBox == 1) {
            g.drawImage(saveAndExitHighlighted, 265, 258, null);
        }
    }
}
