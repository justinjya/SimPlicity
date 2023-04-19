package src.entities.handlers;

import java.awt.Rectangle;

import src.entities.Interactables;
import src.entities.Entity;
import src.entities.Sim;
import src.items.interactables.*;

public class InteractionHandler {
    private Entity entity;
    private Rectangle interactionRange;
    private Interactables[] solidObjects;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean inRange = false;

    public InteractionHandler(Entity entity, Interactables[] solidObjects) {
        this.entity = entity;
        this.solidObjects = solidObjects;
        this.x = entity.getX();
        this.y = entity.getY() + entity.getHeight();
        this.width = entity.getWidth();
        this.height = entity.getHeight() / 3;
        interactionRange = new Rectangle(x, y, width, height);
    }

    public Entity getentity() {
        return entity;
    }

    public Rectangle getInteractionRange() {
        return interactionRange;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public void moveLeft(int newX, int newY) {
        x = newX - (entity.getWidth()) / 3;
        y = newY;
        width = entity.getWidth() / 3;
        height = entity.getHeight();

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public void moveRight(int newX, int newY) {
        x = newX + (entity.getWidth() - 8); // The width is subtracted by 8 to accomodate for collision clipping
        y = newY;
        width = entity.getWidth() / 3;
        height = entity.getHeight();

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public void moveUp(int newX, int newY) {
        x = newX;
        y = newY - (entity.getHeight() / 3);
        width = entity.getWidth();
        height = entity.getHeight() / 3;

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public void moveDown(int newX, int newY) {
        x = newX;
        y = newY + entity.getHeight();
        width = entity.getWidth();
        height = entity.getHeight() / 3;

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public boolean isObjectInRange() {
        try {
            if (solidObjects.length != 0) {
                for (Interactables solidObject : solidObjects) {
                    if (solidObject instanceof Interactables) {
                        if (getInteractionRange().intersects(solidObject.getBounds())) {
                            return true;
                        }
                    }
                }
            }
        }
        catch (NullPointerException e) {}

        return false;
    }

    public Interactables getInteractableObject() {
        // To - do fix
        // Placeholer placeholder = new Placeholder(getInteractableObjectName(), getInteractableObjectName(), x, x, y, width, height, null, null);
        try {
            if (solidObjects.length != 0) {
                for (Interactables solidObject : solidObjects) {
                    if (getInteractionRange().intersects(solidObject.getBounds())) {
                        return solidObject;
                    }
                }
            }
        }
        catch (NullPointerException e) {}
        
        return null;
    }

    public void interact() {
        Interactables object = getInteractableObject();
        if (object == null) {
            return;
        }

        if (object.isOccupied()) {
            return;
        }

        if (object instanceof Bed) {
            object.interact((Sim) entity);
        }
    }
}
