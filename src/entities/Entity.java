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

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = Consts.SCALED_TILE * width;
        this.height = Consts.SCALED_TILE * height;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    private void checkCollision(CollisionHandler collisionHandler, int newX, int newY) {
        if (!collisionHandler.isCollision(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    // FOR SIM
    public void move(CollisionHandler collisionHandler, InteractionHandler interactionHandler) {
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
            checkCollision(collisionHandler, newX, newY);
        }
        speed = initialSpeed;

        // Keybinds for the entity to interact with the game or window
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_F)) { // check if the F key has been pressed since the last update
            System.out.println("press");
            interactionHandler.interact();
        }
    }

    // FOR ADDING OBJECTS
    public void move(CollisionHandler collisionHandler) {
        // Update the entity position when moving
        int newX = x;
        int newY = y;
        double speed = this.speed * 12.8;
        double initialSpeed = speed;
        int delay = 100;
 
        if (isMoving()) {
            if (KeyHandler.isKeyDown(KeyHandler.KEY_A)) {
                newX -= speed; // move left by one tile
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_D)) {
                newX += speed; // move right by one tile
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_W)) {
                newY -= speed; // move up by one tile
            }
            if (KeyHandler.isKeyDown(KeyHandler.KEY_S)) {
                newY += speed; // move down by one tile
            }
            checkCollision(collisionHandler, newX, newY);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        speed = initialSpeed;
    }
}
