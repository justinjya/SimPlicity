package src.entities.sim;

import java.lang.reflect.Array;
import java.util.ArrayList;


import src.items.Item;
import src.main.ui.UserInterface;
import src.entities.interactables.*;

import src.entities.handlers.KeyHandler;

public class Store {
    private ArrayList<String> listOfItem;
    private int slotX = 0;
    private int slotY = 0;
    private int slotSize = 45;


    // CONSTRUCTOR
    public Store() {
        listOfItem = new ArrayList<>();
        listOfItem.add("Aquarium");
        listOfItem.add("Single Bed");
        listOfItem.add("Queen Size Bed");
        listOfItem.add("King Size Bed");
        listOfItem.add("Door");
        listOfItem.add("Shower");
        listOfItem.add("Gas Stove");
        listOfItem.add("Electric Stove");
        listOfItem.add("Toilet");
        listOfItem.add("Trash Bin");
    }

    // update the state of the store
    public void update(UserInterface ui) {
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
            slotY--;
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            slotX--;
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            slotY++;
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            slotX++;
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            interact(ui);
        }
    }

    // draw the ui
    public void draw() {
        drawFrame();
        drawItem();
        drawCursor();
    }

    // interact
    public void interact(UserInterface ui) {
        
    }

    public void drawFrame() {

    }

    public void drawItem() {

    }

    public void drawCursor() {

    }
 




    
}   
