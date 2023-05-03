package src.main.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.KeyHandler;
import src.assets.ImageLoader;
import src.main.panels.GamePanel;

public class PauseUserInterface {
    private static int selectedBox = 0; // Boxes starting from 0 to 4
    private static int selectedBoxX = 290;
    private static int selectedBoxY = 415;
    private static int selectedBoxWidth = Consts.SCALED_TILE + 8;
    private static int selectedBoxHeight = Consts.SCALED_TILE + 8;
    private static int boxStep = 81;
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
    
    public static void draw(Graphics2D g, UserInterface ui){
        if(ui.isPause()){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
            g.drawString("PAUSED", 300, 100);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            g.drawString("RESUME", 350, 250);
            g.drawString("EXIT", 350, 350);
            g.drawImage(ImageLoader.loadPause()[3], 350, 250, null);
            g.drawImage(ImageLoader.loadPause()[4], 350, 350, null);
            g.drawImage(ImageLoader.loadPause()[selectedBox + 3], selectedBoxX, selectedBoxY, null);
        }
    }

    public static void update(UserInterface ui){
        if (ui.isPause()){
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

        // Selection Box
        g.drawImage(images[3], 290, 215, null); //help_highlight
        g.drawImage(images[4], 290, 315, null); //exit_highlight
        
    }

}
