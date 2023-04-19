package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.entities.Sim;
import src.entities.Interactables;

public class UserInterface {
    BufferedImage[] images;
    Sim sim;
    GameTime time;

    public UserInterface(Sim sim, GameTime time) {
        this.sim = sim;
        this.time = time;
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 16);
        g.setFont(font);

        g.drawString("Day: " + time.getDay(), 605, 20);
        g.drawString("Time Remaining: " + time.getTimeRemaining(), 605, 40);
        
        // ONLY FOR DEBUGGING
        // g.drawString("Duration: " + time.getDecrements(), 605, 60);

        g.drawString("Name: " + sim.getName(), 5, 20);
        g.drawString("Health: " + sim.getHealth(), 5, 40);
        g.drawString("Hunger: " + sim.getHunger(), 5, 60);
        g.drawString("Mood: " + sim.getMood(), 5, 80);
        g.drawString("Money: " + sim.getMoney(), 5, 100);
        g.drawString("Status: " + sim.getStatus(), 5, 120);
        g.drawString("InRange: " + sim.getInteractionHandler().isInRange(), 5, 590);
        g.drawString("isWalking: " + sim.isMoving(), 5, 570);

        if (sim.getInteractionHandler().isInRange()) {
            Interactables object = sim.getInteractionHandler().getInteractableObject();
            if (object != null) {
                g.drawString("Press F to Interact with " + object.getName(), Consts.WIDTH / 2 - 100, Consts.HEIGHT - 80);
                g.drawString("isOccupied: " + object.isOccupied(), Consts.WIDTH / 2 - 100, Consts.HEIGHT - 60);
                g.drawString("imageIndex: " + object.getImageIndex(), Consts.WIDTH / 2 - 100, Consts.HEIGHT - 40);
            }
        }

        if (sim.isBusy()) {
            sim.getInteractionHandler().getInteractableObject().drawTimer(g);
        }
    }
}
