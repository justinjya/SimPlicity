package src.entities;

import src.entities.handlers.*;
import src.main.Consts;

public abstract class Entity {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed = 5;
    private int direction; // 0 = down, 1 = up, 2 = left, 3 = right
    private InteractionHandler interactionHandler;
    private CollisionHandler collisionHandler;
    private Interactables[] solidObjects;

    public Entity(int x, int y, int width, int height, Interactables[] solidObjects) {
        this.x = x;
        this.y = y;
        this.width = Consts.SCALED_TILE * width;
        this.height = Consts.SCALED_TILE * height;
        this.interactionHandler = new InteractionHandler(this, solidObjects);
        this.collisionHandler = new CollisionHandler(this, solidObjects);
        this.solidObjects = solidObjects;
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

    public int getDirection() {
        return direction;
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public Interactables[] getSolidObjects() {
        return solidObjects;
    }

    public boolean isMoving() {
        if (KeyHandler.isKeyDown(KeyHandler.KEY_A) || KeyHandler.isKeyDown(KeyHandler.KEY_D) || 
            KeyHandler.isKeyDown(KeyHandler.KEY_W) || KeyHandler.isKeyDown(KeyHandler.KEY_S)) {
            return true;
        }
        return false;
    }

    public boolean isMovingDiagonally() {
        if ((KeyHandler.isKeyDown(KeyHandler.KEY_W) && KeyHandler.isKeyDown(KeyHandler.KEY_A)) ||
            (KeyHandler.isKeyDown(KeyHandler.KEY_W) && KeyHandler.isKeyDown(KeyHandler.KEY_D)) ||
            (KeyHandler.isKeyDown(KeyHandler.KEY_S) && KeyHandler.isKeyDown(KeyHandler.KEY_A)) ||
            (KeyHandler.isKeyDown(KeyHandler.KEY_S) && KeyHandler.isKeyDown(KeyHandler.KEY_D))) {
            return true;
        }
        return false;
    }

    private void checkCollision(int newX, int newY) {
        if (!collisionHandler.isCollision(newX, newY)) {
            x = newX;
            y = newY;
            interactionHandler.setInRange(interactionHandler.isObjectInRange());
            // boolean currentlyInRange = interactionHandler.isInRange();

            // ONLY FOR DEBUGGING
            // if (interactionHandler.isInRange() && !currentlyInRange) {
            //     decrementHealth();
            // }
        }
    }

    public void move() {
        // Update the entity position when moving
        int newX = x;
        int newY = y;
        int initialSpeed = speed;

        if (isMovingDiagonally()) {
            speed *= 0.707;
        }

        if (isMoving()) {
            if (KeyHandler.isKeyDown(KeyHandler.KEY_A)) {
                newX -= speed;
                direction = 2;
                interactionHandler.moveLeft(newX, newY);
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_D)) {
                newX += speed;
                direction = 3;
                interactionHandler.moveRight(newX, newY);
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_W)) {
                newY -= speed;
                direction = 1;
                interactionHandler.moveUp(newX, newY);
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_S)) {
                newY += speed;
                direction = 0;
                interactionHandler.moveDown(newX, newY);
            }
            checkCollision(newX, newY);
        }
        speed = initialSpeed;

        // Keybinds for the entity to interact with the game or window
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_F)) { // check if the F key has been pressed since the last update
            System.out.println("press");
            interactionHandler.interact();
        }       
    }    
}
