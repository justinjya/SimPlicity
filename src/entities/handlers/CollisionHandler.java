package src.entities.handlers;

import java.awt.Rectangle;
import java.util.ArrayList;

import src.entities.*;

public class CollisionHandler {
    private Entity entity;
    private Room currentRoom;
    
    public CollisionHandler(Entity entity, Room room) {
        this.entity = entity;
        this.currentRoom = room;
    }
    
    public boolean isCollision(int x, int y) {
        Rectangle newEntity;
        if (entity instanceof Sim) {
            newEntity = new Rectangle(x + 8, y + 15, entity.getWidth() - 16, entity.getHeight() - 16); // Sim collision box  
            // Values subtracted to adjust the Sim collision box according to the image  
        }
        else {
            newEntity = new Rectangle(x, y, entity.getWidth(), entity.getHeight()); // Object collision box
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


