package src.entities.handlers;

import java.awt.Rectangle;

import src.entities.Interactables;
import src.entities.Entity;
import src.items.*;

public class CollisionHandler {
    private Entity entity;
    private Item[] solidObjects;
    
    public CollisionHandler(Entity entity, Item[] solidObjects) {
        this.entity = entity;
        this.solidObjects = solidObjects;
    }
    
    public boolean isCollision(int x, int y) {
        Rectangle newEntity = new Rectangle(x, y, entity.getWidth() - 8, entity.getHeight()); // entity collision entity
        // The width is subtracted by 8 to accomodate the clipping
        
        // Only checks for collision if the solid objects array is not empty
        try {
            if (solidObjects.length != 0) {
                for (Item solidObject : solidObjects) {
                    if (newEntity.intersects(((Interactables) solidObject).getBounds())) {
                        return true;
                    }
                }
            }
        } catch (NullPointerException e) {}

        return false;
    }
}


