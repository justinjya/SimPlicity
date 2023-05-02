package src.main.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import src.entities.sim.actions.*;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.KeyHandler;
import src.assets.ImageLoader;
import src.main.panels.GamePanel;

public class PauseUserInterface {
    private static int selectedBox = 0; // Boxes starting from 0 to 4
    private static int selectedBoxX = 203;
    private static int selectedBoxY = 479;
    private static int selectedBoxWidth = Consts.SCALED_TILE + 8;
    private static int selectedBoxHeight = Consts.SCALED_TILE + 8;
    private static int boxStep = 81;
    private static boolean pause = false;
    private BufferedImage[] images = ImageLoader.loadPause();

    private static void moveSelectedBox(String direction) {
        switch (direction)  {
            case "down":
                selectedBox--;
                if (selectedBox < 0) {
                    selectedBox = 1;
                    selectedBoxX += boxStep * 1;
                }
                else {
                    selectedBoxX -= boxStep;
                }
                break;
            case "up":
                selectedBox++;
                if (selectedBox > 1) {
                    selectedBox = 0;
                    selectedBoxX -= boxStep * 1;
                }
                else {
                    selectedBoxX += boxStep;
                }
                break;
            default:
                break;
        }    
    }

    public static void update(UserInterface ui, Graphics g){
        if(KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)){
            if(KeyHandler.isKeyPressed(KeyHandler.KEY_S)){
                moveSelectedBox("down");
            }
            if(KeyHandler.isKeyPressed(KeyHandler.KEY_W)){
                moveSelectedBox("up");
            }
        }
    }
    
    public void render(Graphics g) {
        g.drawImage(images[0], 0, 0, null); // Background
        g.drawImage(images[1], 290, 215, null); // Help button
        g.drawImage(images[2], 290, 315, null); // Exit button
    }

}
