package src.entities.handlers;

import java.awt.Rectangle;

import src.entities.Sim;
import src.items.*;
import src.items.interactables.Interactables;

public class CollisionHandler {
    private Sim sim;
    private Item[] solidObjects;
    
    public CollisionHandler(Sim sim, Item[] solidObjects) {
        this.sim = sim;
        this.solidObjects = solidObjects;
    }
    
    public boolean isCollision(int x, int y) {
        Rectangle newsim = new Rectangle(x, y, sim.getWidth() - 8, sim.getHeight()); // Sim collision sim
        // The width is subtracted by 8 to accomodate the clipping
        
        // Only checks for collision if the solid objects array is not empty
        try {
            if (solidObjects.length != 0) {
                for (Item solidObject : solidObjects) {
                    if (newsim.intersects(((Interactables) solidObject).getBounds())) {
                        return true;
                    }
                }
            }
        } catch (NullPointerException e) {}

        return false;
    }
}


