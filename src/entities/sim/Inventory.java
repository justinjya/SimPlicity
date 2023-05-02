package src.entities.sim;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;

import src.assets.ImageLoader;
import src.entities.interactables.*;
import src.entities.interactables.Interactables;
import src.items.Item;
import src.items.foods.Food;
import src.items.foods.RawFood;
import src.main.KeyHandler;
import src.main.UserInterface;
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
    private int slotX = 621;
    private int slotY = 208;

    private BufferedImage[] images = ImageLoader.loadInventory();

    // constructor
    public Inventory() {
        // ONLY FOR DEBUGGING
        // objects
        addItem(new Shower(0, 0));
        addItem(new Stove(0, 1, 0));
        addItem(new Stove(0, 1, 1));
        addItem(new Television(0, 2));
        addItem(new Toilet(0, 0));
        addItem(new TrashBin(0, 4));
        addItem(new Bed(0));
        addItem(new Bed(1));
        addItem(new Bed(1));
        addItem(new Bed(2));
        
        // foods
        addItem(new RawFood(0));
        addItem(new RawFood(1));
        addItem(new RawFood(2));
        addItem(new RawFood(3));
        addItem(new RawFood(4));
        addItem(new RawFood(5));
        addItem(new RawFood(6));
        addItem(new RawFood(7));
        addItem(new RawFood(0));
        addItem(new RawFood(1));
        addItem(new RawFood(2));
        addItem(new RawFood(2));

        getItemsToShow();
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
        try {
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
        catch (ConcurrentModificationException cme) {}
    }

    public void interact() {
        if (slotSelected > itemNames.size()) return;

        try {
            String selectedItem = itemNames.get(slotSelected);
    
            for (Item item : mapOfItems.keySet()) {
                if (!item.getName().equals(selectedItem)) continue;
    
                Sim sim = UserInterface.getCurrentSim();
    
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
        catch (IndexOutOfBoundsException iobe) {}
        catch (ConcurrentModificationException cme) {}
    }

    // others
    private void getItemsToShow() {
        itemsToShow = new HashMap<>();
        itemNames = new ArrayList<>();

        try {
            for (Item item : mapOfItems.keySet()) {
                if (isObject){
                    if (item instanceof Interactables) {
                        itemsToShow.put(item, mapOfItems.get(item));
                    }
                }
                else {
                    if ((item instanceof Food)) {
                        itemsToShow.put(item, mapOfItems.get(item));
                    }
                }
            }
    
            for (Item item : itemsToShow.keySet()) {
                itemNames.add(item.getName());
            }
        }
        catch (ConcurrentModificationException cme) {}
    }

    public void update()
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
                interact();
            }
        }
    }

    public void draw(Graphics2D g)
    {
        if (isOpen) {
            g.drawImage(images[1], 605, 174, null); // inventory box
            g.drawImage(images[2], 614, 201, null); // inventory catalogue box
            g.drawImage(images[3], 615, 404, null); // interactables box
            g.drawImage(images[3], 700, 404, null); // foods box

            g.setColor(Color.WHITE);
            if (isObject) {
                g.drawImage(images[3], 614, 403, images[3].getWidth() + 4, images[3].getHeight() + 3, null); // if interactables
                g.setFont(new Font("Inter", Font.BOLD, 9));
                g.drawString("Interactables", 627, 419);
                g.setFont(new Font("Inter", Font.BOLD, 8));
                g.drawString("Foods", 729, 419);
            }
            else {
                g.drawImage(images[3], 699, 403, images[3].getWidth() + 4, images[3].getHeight() + 3, null); // if food
                g.setFont(new Font("Inter", Font.BOLD, 8));
                g.drawString("Interactables", 628, 419);
                g.setFont(new Font("Inter", Font.BOLD, 9));
                g.drawString("Foods", 728, 419);
            }

            g.drawImage(images[5], slotX + (slotSize * slotCol) + (slotCol * 7), slotY + (slotSize * slotRow), null); // selector
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Inter", Font.BOLD, 8));
            String itemName = "";
            try {
                itemName = itemNames.get(slotSelected);
                g.drawString(itemName, 669, 193);
            }
            catch (IndexOutOfBoundsException iobe) {}
            drawItems(g);
        }
        
        g.drawImage(images[0], 599, 146, null); // inventory title
    }

    private void drawItems(Graphics2D g)
    {
        try {
            int x = slotX + 2, y = slotY + 2; // Starting coordinates
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
    
                x += slotSize + 7; // Move to the next column
                if (i % cols == cols - 1) { // If we've filled up a row
                    x = slotX; // Reset to the first column
                    y += slotSize; // Move to the next row
                }
                i++;
            }
        }
        catch (NullPointerException npe) {}
        catch (ConcurrentModificationException cme) {}
    }
}