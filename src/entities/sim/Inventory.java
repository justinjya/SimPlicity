package src.entities.sim;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;

import src.entities.interactables.*;
import src.entities.interactables.Interactables;
import src.items.Item;
import src.items.foods.Food;
import src.items.foods.RawFood;
import src.main.KeyHandler;
import src.main.ui.UserInterface;
import src.world.House;
import src.world.Room;

public class Inventory {
    // attributes
    private HashMap<Item, Integer> mapOfItems = new HashMap<>();
    private HashMap<Item, Integer> itemsToShow = new HashMap<>();
    private ArrayList<String> itemNames = new ArrayList<>();
    private boolean isOpen = false; // true if the inventory is open, false if closed
    private boolean isObject = true;

    private int slotSelected = 0;
    
    // interface dimensions
    private int slotSize = 45;
    private int slotRow = 0;
    private int slotCol = 0;

    private int frameX = 607;
    private int frameY = 173;
    private int frameWidth = 182;
    private int frameHeight = 262;

    private int boxWidth = (slotSize * 3) + 20;
    private int boxHeight = (slotSize * 4) + 20;
    private int boxX = frameX + ((frameWidth - boxWidth) / 2);
    private int boxY = frameY + ((frameHeight - boxHeight) / 2);

    private int categoryWidth = boxWidth / 2;
    private int categoryHeight = 20;
    private int categoryX = boxX;
    private int categoryY = boxHeight + boxY;
    
    private int slotXstart = boxX + ((boxWidth - (slotSize * 3)) / 2);
    private int slotYstart = boxY + ((boxHeight - (slotSize * 4)) / 2);
    private int slotX = slotXstart;
    private int slotY = slotYstart;

    // constructor
    public Inventory() {
        // ONLY FOR DEBUGGING
        addItem(new Bed(0)); // single
        addItem(new Bed(1)); // queen
        addItem(new Bed(1)); // queen
        addItem(new Bed(2)); // king
        addItem(new RawFood(0)); // nasi
        addItem(new RawFood(2)); // ayam
        addItem(new RawFood(2)); // ayam
    }

    // getter
    public HashMap<Item, Integer> getMapOfItems()
    {
        return mapOfItems;
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    // setter
    public void changeIsOpen()
    {
        isOpen = !isOpen;
    }

    public void switchCategory()
    {
        isObject = !isObject;
    }

    public void addItem(Item item)
    {
        for (Item i : mapOfItems.keySet())
        {
            if (i.getName().equals(item.getName()))
            {
                int count = mapOfItems.get(i);
                mapOfItems.put(i, count + 1);
                return;
            }
        }
        mapOfItems.put(item, 1);
    }

    public void interact(UserInterface ui) {
        if (slotSelected > itemNames.size()) return;

        String selectedItem = itemNames.get(slotSelected);

        for (Item item : mapOfItems.keySet()) {
            if (!item.getName().equals(selectedItem)) continue;

            Sim sim = ui.getCurrentSim();

            if (item instanceof Interactables) {
                House currentHouse = sim.getCurrentHouse();
                Sim currentHouseOwner = currentHouse.getOwner();

                if (!sim.getName().equals(currentHouseOwner.getName())){
                    return;
                }

                Room currentRoom = sim.getCurrentRoom();
                Interactables newObject = (Interactables) item;

                changeIsOpen();
                currentRoom.addObject(newObject);
                sim.changeIsBusyState();
            }

            if (item instanceof Food) {
                Food food = (Food) item;

                food.eat(sim);
            }
            
            int count = mapOfItems.get(item);
            if (count > 1) {
                mapOfItems.put(item, count - 1);
            }
            else {
                mapOfItems.remove(item);
            }
            return;
        }
    }

    // others
    public void update(UserInterface ui)
    {
        getItemsToShow();

        if (KeyHandler.isKeyPressed(KeyEvent.VK_TAB)) {
            switchCategory();
        }

        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            if (slotCol > 0) {
                slotSelected--;
                slotCol--;
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            if (slotRow < 3) {
                slotSelected += 3;
                slotRow++;
            } 
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            if (slotCol < 2) {
                slotSelected++;
                slotCol++;
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
            if (slotRow > 0) {
                slotSelected -= 3;
                slotRow--;
            }
        }
        
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            if (slotSelected < itemNames.size()) {
                interact(ui);
            }
        }
    }

    public void draw(Graphics2D g)
    {
        drawFrame(g);

        drawCursor(g);
        
        drawItems(g);
    }

    private void getItemsToShow() {
        itemsToShow = new HashMap<>();
        itemNames = new ArrayList<>();

        for (Item item : mapOfItems.keySet()) {
            if (isObject){
                if (item instanceof Interactables) {
                    itemsToShow.put(item, mapOfItems.get(item));
                }
            }
            else {
                if (!(item instanceof Interactables)) {
                    itemsToShow.put(item, mapOfItems.get(item));
                }
            }
        }

        for (Item item : itemsToShow.keySet()) {
            itemNames.add(item.getName());
        }
    }

    private void drawFrame(Graphics2D g)
    {
        // Frame
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(frameX, frameY, frameWidth, frameHeight);

        // Box
        g.setColor(Color.GRAY);
        g.fillRect(boxX, boxY, boxWidth, boxHeight);

        if (isObject) {
            // Selected category = object
            g.fillRect(categoryX, categoryY, categoryWidth, categoryHeight);
            g.setColor(Color.WHITE);
            Font font = new Font("Inter", Font.BOLD, 10);
            g.setFont(font);
            g.drawString("Objects", categoryX + (categoryWidth / 4), categoryY + (categoryHeight / 2));
        }
        else {
            // Selected category = foods
            g.fillRect(categoryX + (categoryWidth + 1), categoryY, categoryWidth, categoryHeight);
            g.setColor(Color.WHITE);
            Font font = new Font("Inter", Font.BOLD, 10);
            g.setFont(font);
            g.drawString("Foods", categoryX + (categoryWidth + 23), categoryY + (categoryHeight / 2));
        }
    }

    private void drawItems(Graphics2D g)
    {
        try {
            int x = slotX, y = slotY; // Starting coordinates
            int cols = 3;
            int i = 0;
    
            for (Item item : itemsToShow.keySet()) {
                BufferedImage itemIcon = item.getIcon(); // Get the item image
    
                g.drawImage(itemIcon, x, y, null); // Draw the image
    
                if (mapOfItems.get(item) > 1) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x + 31, y + 31, 14, 14);
    
                    g.setColor(Color.BLACK);
                    Font font = new Font("Inter", Font.BOLD, 10);
                    g.setFont(font);
                    g.drawString(Integer.toString(mapOfItems.get(item)), x + 35, y + 41);
                }
    
                x += slotSize; // Move to the next column
                if (i % cols == cols - 1) { // If we've filled up a row
                    x = slotX; // Reset to the first column
                    y += slotSize; // Move to the next row
                }
                i++;
            }
        }
        catch (NullPointerException e) {}
    }

    private void drawCursor(Graphics2D g)
    {
        if (itemNames.isEmpty()) return;

        // Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = slotSize;
        int cursorHeight = slotSize;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(cursorX, cursorY, cursorWidth, cursorHeight);

        g.setColor(Color.WHITE);
        g.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);

        // Item selected
        g.setColor(Color.BLACK);
        Font font = new Font("Inter", Font.BOLD, 10);
        g.setFont(font);

        String itemSelected = null;
        try {
            itemSelected = itemNames.get(slotSelected);
            g.drawString(itemSelected, frameX + 50, frameY + 20);
        }
        catch (IndexOutOfBoundsException e) {}
    }
}