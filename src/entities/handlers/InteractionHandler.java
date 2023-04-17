package src.entities.handlers;

import java.awt.Rectangle;

import src.entities.Sim;
import src.items.interactables.*;

public class InteractionHandler {
    private Sim sim;
    private Rectangle interactionRange;
    private Interactables[] solidObjects;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean inRange = false;

    public InteractionHandler(Sim sim, Interactables[] solidObjects) {
        this.sim = sim;
        this.solidObjects = solidObjects;
        this.x = sim.getX();
        this.y = sim.getY() + sim.getHeight();
        this.width = sim.getWidth();
        this.height = sim.getHeight() / 3;
        interactionRange = new Rectangle(x, y, width, height);
    }

    public Sim getSim() {
        return sim;
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
        x = newX - (sim.getWidth()) / 3;
        y = newY;
        width = sim.getWidth() / 3;
        height = sim.getHeight();

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public void moveRight(int newX, int newY) {
        x = newX + (sim.getWidth() - 8); // The width is subtracted by 8 to accomodate for collision clipping
        y = newY;
        width = sim.getWidth() / 3;
        height = sim.getHeight();

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public void moveUp(int newX, int newY) {
        x = newX;
        y = newY - (sim.getHeight() / 3);
        width = sim.getWidth();
        height = sim.getHeight() / 3;

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public void moveDown(int newX, int newY) {
        x = newX;
        y = newY + sim.getHeight();
        width = sim.getWidth();
        height = sim.getHeight() / 3;

        interactionRange.setSize(width, height);
        interactionRange.setLocation(x, y);
    }

    public boolean isObjectInInteractionRange() {
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

    public String getInteractableObjectName() {
        String objectName = getInteractableObject().getName();
        return objectName;
    }

    public void interact() {
        Interactables object = getInteractableObject();
        if (!object.isOccupied()) {
            if (object instanceof Bed) {
                object.interact(sim);
            }
        }
    }
}
