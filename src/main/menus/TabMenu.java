package src.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.KeyHandler;
import src.assets.ImageLoader;
import src.main.UserInterface;
import src.entities.sim.actions.*;
import src.entities.sim.Sim;
import src.world.House;
import src.world.Room;

public class TabMenu {
    // Tab Menu Images
    private static BufferedImage[] images = ImageLoader.loadTabMenu();

    // Selection Box Attributes
    private static int selectedBox = 0; // Boxes starting from 0 to 4

    private static void moveSelectedBox(String direction) {
        switch (direction)  {
            case "left":
                selectedBox--;
                if (selectedBox < 0) {
                    selectedBox = 4;
                }
                break;
            case "right":
                selectedBox++;
                if (selectedBox > 4) {
                    selectedBox = 0;
                }
                break;
            default:
                break;
        }
    }

    // TO - DO !!! : Integrate with Store
    private static void boxEntered() {
        UserInterface.tab();
        
        Sim currentSim = UserInterface.getCurrentSim();
        Room currentRoom = currentSim.getCurrentRoom();
        House currentHouse = currentRoom.getHouseInsideOf();
        Sim currentHouseOwner = currentHouse.getOwner();

        if (currentSim.isBusy()) return;

        switch (selectedBox) {
            case 0:
                if (currentSim.getName().equals(currentHouseOwner.getName())) {
                    NonActiveActions.editRoom(currentRoom);
                    break;
                }
            case 1:
                if (currentSim.getName().equals(currentHouseOwner.getName())) {
                    UpgradeActions.addRoom(currentRoom, "Second Room");
                    break;
                }
            case 2:
                // ITEM STORE HERE
                break;
            case 3:
                AddSimAction.addSim();
                break;
            case 4:
                ActiveActions.visitAnotherSim();
                break;
            default:
                break;
        }
    }
    
    public static void update() {
        if (UserInterface.isTabbed()) {
            // Change selected box based on key input
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                moveSelectedBox("left");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                moveSelectedBox("right");
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                boxEntered();
            }
        }
    }

    public static void draw(Graphics2D g) {
        g.drawImage(images[0], 204, 488, null);
        g.drawImage(images[1], 284, 488, null);
        g.drawImage(images[2], 364, 488, null);
        g.drawImage(images[3], 444, 488, null);
        g.drawImage(images[4], 524, 488, null);
        
        // Draw selected box
        if (UserInterface.isTabbed()) {
            g.drawImage(images[selectedBox], 199 + (selectedBox * 80), 484, Consts.SCALED_TILE + 15, Consts.SCALED_TILE + 14, null);
        }
    }

    private static void drawSelectedBoxText(Graphics2D g) {
        Font font = new Font("Inter", Font.PLAIN, 12);

        g.setFont(font);
        switch (selectedBox) {
            case 0:
                g.drawString("Edit Room", Consts.CENTER_X - 18, Consts.CENTER_Y + 172);
                break;
            case 1:
                g.drawString("Upgrade House", Consts.CENTER_X - 38, Consts.CENTER_Y + 172);
                break;
            case 2:
                g.drawString("Buy Items", Consts.CENTER_X - 22, Consts.CENTER_Y + 172);
                break;
            case 3:
                g.drawString("Add Sims", Consts.CENTER_X - 20, Consts.CENTER_Y + 172);
                break;
            case 4:
                g.drawString("Visit Another Sim", Consts.CENTER_X - 38, Consts.CENTER_Y + 172);
                break;
            default:
                g.drawString("Lorem Ipsum", Consts.CENTER_X - 28, Consts.CENTER_Y + 172);
                break;
        }
    }
}
