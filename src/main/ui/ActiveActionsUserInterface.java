package src.main.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import src.entities.handlers.KeyHandler;
import src.entities.sim.Sim;
import src.entities.sim.actions.ActiveActions;
import src.main.Consts;
import src.main.GamePanel;
import src.main.GameTime;

public class ActiveActionsUserInterface {
    // Selection Box Attributes
    private static int selectedBox = 0; // Boxes starting from 0 to 3
    private static int selectedBoxX = 283;
    private static int selectedBoxY = 279;
    private static int selectedBoxWidth = Consts.SCALED_TILE + 8;
    private static int selectedBoxHeight = Consts.SCALED_TILE + 8;
    private static int boxStep = 81;

    private static void moveSelectedBox(String direction) {
        switch (direction)  {
            case "left":
                selectedBox--;
                if (selectedBox < 0) {
                    selectedBox = 2;
                    selectedBoxX += boxStep * 2;
                }
                else {
                    selectedBoxX -= boxStep;
                }
                break;
            case "right":
                selectedBox++;
                if (selectedBox > 2) {
                    selectedBox = 0;
                    selectedBoxX -= boxStep * 2;
                }
                else {
                    selectedBoxX += boxStep;
                }
                break;
            default:
                break;
        }
    }

    // TO - DO !!! : Integrate with Store
    private static void boxEntered(Sim sim, UserInterface ui, GamePanel gp, GameTime time) {
        switch (selectedBox) {
            case 0:
                ActiveActions.work(sim, time);
                break;
            case 1:
                ActiveActions.exercise(sim, time);
                break;
            case 2:
                ActiveActions.visitAnotherSim(ui);
                break;
            default:
                break;
        }
        gp.setState("Playing");
    }
    
    public static void update(Sim sim, UserInterface ui, GamePanel gp, GameTime time) {
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            moveSelectedBox("left");
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            moveSelectedBox("right");
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            boxEntered(sim, ui, gp, time);
        }
    }

    public static void draw(Graphics2D g) {
        g.fillRect(283, 283, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(364, 283, Consts.SCALED_TILE, Consts.SCALED_TILE);
        g.fillRect(445, 283, Consts.SCALED_TILE, Consts.SCALED_TILE);

        // Draw selected box
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(selectedBoxX, selectedBoxY, selectedBoxWidth, selectedBoxHeight);
        drawSelectedBoxText(g);
    }

    private static void drawSelectedBoxText(Graphics2D g) {
        Font font = new Font("Arial", Font.PLAIN, 12);

        g.setFont(font);
        switch (selectedBox) {
            case 0:
                g.drawString("Go To Work", Consts.CENTER_X - 27, Consts.CENTER_Y + 82);
                break;
            case 1:
                g.drawString("Start Exercising", Consts.CENTER_X - 35, Consts.CENTER_Y + 82);
                break;
            case 2:
                g.drawString("Visit Another Sim", Consts.CENTER_X - 45, Consts.CENTER_Y + 82);
                break;
            default:
                g.drawString("Lorem Ipsum", Consts.CENTER_X - 28, Consts.CENTER_Y + 82);
                break;
        }
    }

    // TO - DO !!! : Fix this
    public static void showActiveActions(GamePanel gp) {
        gp.setState("Viewing active actions");
    }
}