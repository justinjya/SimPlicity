package src.main.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.handlers.KeyHandler;

public class MainMenu {
    private static BufferedImage[] images = ImageLoader.loadMainMenu();
    private static int selectedBox = 0;
    private static int selectedBoxX = 125;
    private static int selectedBoxY = 294;
    private static int boxStepX = 284;
    private static int boxStepY = 88;

    public static void update() {
        int newX = selectedBoxX;
        int newY = selectedBoxY;
        int newSelectedBox = selectedBox;
        
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
            newY -= boxStepY;
            newSelectedBox -= 2;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            newX -= boxStepX;
            newSelectedBox--;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            newY += boxStepY;
            newSelectedBox += 2;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            newX += boxStepX;
            newSelectedBox++;
        }

        if ((newX > 124 && newX < 410) && (newY > 293 && newY < 393)) {
            selectedBox = newSelectedBox;
            selectedBoxX = newX;
            selectedBoxY = newY;
        }
    }

    public static void draw(Graphics2D g) {
        // draw the images
        g.drawImage(images[0], 0, 0, null); // background
        g.drawImage(images[1], 208, 115, null); // title
        g.drawImage(images[2], 79, 243, null); // menu background

        // draw selector and buttons
        g.setColor(Color.GRAY);
        g.fillRect(selectedBoxX, selectedBoxY, 266, 74);

        g.drawImage(images[3], 130, 299, null); // start button
        g.drawImage(images[4], 414, 299, null); // load button
        g.drawImage(images[4], 130, 387, null); // help button
        g.drawImage(images[6], 414, 387, null); // exit button
    }
}
