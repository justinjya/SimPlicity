package src.main.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import src.entities.sim.actions.*;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.KeyHandler;
import src.main.UserInterface;
import src.world.Room;
import src.assets.ImageLoader;

public class PauseUserInterface extends JPanel{
    private static int selectedBox = 0; // Boxes starting from 0 to 4
    private static int selectedBoxX = 203;
    private static int selectedBoxY = 479;
    private static int selectedBoxWidth = Consts.SCALED_TILE + 8;
    private static int selectedBoxHeight = Consts.SCALED_TILE + 8;
    private static int boxStep = 81;

    private BufferedImage[] images = ImageLoader.loadPause();

    private static void moveSelectedBox(String direction) {
        switch (direction)  {
            case "down":
                selectedBox--;
                if (selectedBox < 0) {
                    selectedBox = 4;
                    selectedBoxX += boxStep * 4;
                }
                else {
                    selectedBoxX -= boxStep;
                }
                break;
            case "up":
                selectedBox++;
                if (selectedBox > 4) {
                    selectedBox = 0;
                    selectedBoxX -= boxStep * 4;
                }
                else {
                    selectedBoxX += boxStep;
                }
                break;
            default:
                break;
        }    
    }

    public static void update(UserInterface ui){
        if(ui.isPause()){
            if(KeyHandler.isKeyPressed(KeyHandler.KEY_S)){
                moveSelectedBox("down");
            }
            if(KeyHandler.isKeyPressed(KeyHandler.KEY_W)){
                moveSelectedBox("up");
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(images[0], 0, 0, null);
        

        g2.dispose();
    }

}
