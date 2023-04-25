package src.entities.handlers;

import java.awt.Rectangle;
import java.util.ArrayList;

import src.entities.*;
import src.main.Consts;

public class CollisionHandler {
    private Entity entity;
    private Room currentRoom;
    
    public CollisionHandler(Entity entity, Room room) {
        this.entity = entity;
        this.currentRoom = room;
    }

    public boolean isOutsidePlayArea(int x, int y) {
        if (entity instanceof Sim) {
            return (x < Consts.PLAY_ARENA_X_LEFT) || (x > Consts.PLAY_ARENA_X_RIGHT) ||
                    (y < Consts.PLAY_ARENA_Y_UP) || (y > Consts.PLAY_ARENA_Y_DOWN);
        }
        else {
            return (x < Consts.PLAY_ARENA_X_LEFT) || (x + entity.getWidth() - Consts.SCALED_TILE > Consts.PLAY_ARENA_X_RIGHT) ||
                    (y < Consts.PLAY_ARENA_Y_UP) || (y + entity.getHeight() - (Consts.SCALED_TILE * 2) > Consts.PLAY_ARENA_Y_DOWN);
        }
    }
    
    public boolean isCollision(int x, int y) {
        Rectangle newEntity;
        if (entity instanceof Sim) { // Sim collision box
            newEntity = new Rectangle(x + 8, y + 15, entity.getWidth() - 16, entity.getHeight() - 16);  
            // Values subtracted to adjust the Sim collision box according to the image  
        }
        else { // Object collision box
            newEntity = new Rectangle(x, y, entity.getWidth(), entity.getHeight());
        }

        ArrayList<Interactables> listOfObjects = currentRoom.getListOfObjects();         
        for (Interactables object : listOfObjects) {
            if (newEntity.intersects(object.getBounds())) {
                return true;
            }
        }
        return false;
    }
}


