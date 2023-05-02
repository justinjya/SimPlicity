package src.main.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.handlers.KeyHandler;
import src.main.GameLoader;
import src.main.GamePanel;

public class MainMenu {
    private static String state = "Main menu";
    private static BufferedImage[] mainMenu = ImageLoader.loadMainMenu();
    private static BufferedImage[] creatingSimMenu = ImageLoader.loadCreatingSimMenu();
    private static int selectedBox = 0;
    private static int selectedBoxX = 125;
    private static int selectedBoxY = 294;
    private static int boxStepX = 284;
    private static int boxStepY = 88;

    private static BufferedImage mockup = ImageLoader.readImage("mockup", "inputNameMockup", 1, 1, false);

    private static boolean isCurrentState(String state) {
        return MainMenu.state.equals(state);
    }

    private static void boxEntered(GamePanel gp) {
        switch (selectedBox) {
            case 0:
                state = "Creating sim";
                // GameLoader.newGame(gp);
                break;
            case 1:
                // load
                break;
            case 2:
                // help
                break;
            case 3:
                // quit
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public static void update(GamePanel gp) {
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) boxEntered(gp);

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
        g.drawImage(mockup, 0, 0, null);



        // if (isCurrentState("Main menu")) drawMainMenu(g);
        if (isCurrentState("Main menu")) {
            drawMainMenu(g);
        }
        // if (isCurrentState("Creating sim")) drawCreateSimMenu(g);
        if (isCurrentState("Creating sim")) {
            drawCreateSimMenu(g);
        }
    }

    private static void drawMainMenu(Graphics2D g) {
        // draw the mainMenu
        g.drawImage(mainMenu[0], 0, 0, null); // background
        g.drawImage(mainMenu[1], 208, 115, null); // title
        g.drawImage(mainMenu[2], 79, 243, null); // menu background

        // draw selector and buttons
        g.setColor(Color.GRAY);
        g.fillRect(selectedBoxX, selectedBoxY, 266, 74);

        g.drawImage(mainMenu[3], 130, 299, null); // start button
        g.drawImage(mainMenu[4], 414, 299, null); // load button
        g.drawImage(mainMenu[4], 130, 387, null); // help button
        g.drawImage(mainMenu[6], 414, 387, null); // exit button
    }

    private static void drawCreateSimMenu(Graphics2D g) {
        g.drawImage(creatingSimMenu[0], 0, 0, null); // background
        g.drawImage(creatingSimMenu[1], 120, 80, null); // title
        g.drawImage(creatingSimMenu[2], 220, 280, null); // input field
        g.drawImage(creatingSimMenu[3], 250, 350, null); // submit button
    }
}
