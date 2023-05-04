package src.main.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.main.KeyHandler;
import src.assets.ImageLoader;
import src.main.UserInterface;
import src.entities.handlers.InteractionHandler;
import src.entities.interactables.Interactables;
import src.entities.interactables.Television;
import src.entities.sim.Sim;

public class InteractMenu {
    private static BufferedImage[] images = ImageLoader.loadInteractMenu();
    private static BufferedImage interactBox = images[0];
    private static BufferedImage highlight = images[1];

    public static int slotSelected = -1;

    public static void reset() {
        slotSelected = 0;
    }

    public static void update() {
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
            UserInterface.viewInteractions();
            slotSelected = -1;
        }
        
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            slotSelected--;
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            slotSelected++;
        }
        if (slotSelected < 0) slotSelected = 1;
        else if (slotSelected > 1) slotSelected = 0;
    }

    public static void draw(Graphics2D g) {
        g.drawImage(highlight, 205 + (slotSelected *  196), 445, null);
        g.drawImage(interactBox, 208, 448, null);
        g.drawImage(interactBox, 404, 448, null);

        Font font = new Font("Inter", Font.BOLD, 14);
        g.setFont(font);
        g.setColor(Color.WHITE);

        Sim currentSim = UserInterface.getCurrentSim();
        InteractionHandler currentSimInteract = currentSim.getInteractionHandler();
        Interactables interactedObject = currentSimInteract.getInteractableObject();

        if (interactedObject instanceof Television) {
            UserInterface.drawCenteredText(g, interactBox, 208, 470, "Karaoke", font);
            UserInterface.drawCenteredText(g, interactBox, 404, 470, "Watch TV", font);
        }
        // if (interactedObject instanceof TableAndChair) {
        //     UserInterface.drawCenteredText(g, interactBox, 208, 470, "Eat", font);
        //     UserInterface.drawCenteredText(g, interactBox, 404, 470, "Read a Book", font);
        // }
    }
}
