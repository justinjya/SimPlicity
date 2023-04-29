package src.entities.sim;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;

import src.entities.handlers.KeyHandler;
import src.entities.interactables.Interactables;
import src.items.Item;

public class Inventory {
    private HashMap<Item, Integer> mapOfItems = new HashMap<>();
    private boolean isOpen = false; // true if the inventory is open, false if closed
    private boolean isObject = true;
    
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
    public Inventory() {}

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

    public void resetIsOpen()
    {
        isOpen = false;
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
                int count = mapOfItems.get(item);
                mapOfItems.put(item, count + 1);
                return;
            }
        }

        mapOfItems.put(item, 1);
    }

    public void removeItem(Item item)
    {
        for (Item i : mapOfItems.keySet())
        {
            if (i.getName().equals(item.getName()))
            {
                int count = mapOfItems.get(i);
                mapOfItems.put(i, count - 1);
                return;
            }
        }

        mapOfItems.remove(item);
    }

    public void update()
    {
        if (KeyHandler.isKeyPressed(KeyEvent.VK_TAB)) {
            switchCategory();
        }

        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            if (slotCol > 0) {
                slotCol--;
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            if (slotRow < 3) {
                slotRow++;
            } 
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            if (slotCol < 2) {
                slotCol++;
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
            if (slotRow > 0) {
                slotRow--;
            }
        }
    }

    public void draw(Graphics2D g)
    {
        drawFrame(g);

        drawCursor(g);
        
        drawItems(g);
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
        int x = slotX, y = slotY; // Starting coordinates
        int cols = 3, rows = 4; // Number of columns and rows
        int i = 0;

        for (Item item : mapOfItems.keySet()) {
            if (isObject && (item instanceof Interactables)){
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
            }

            if (!isObject && !(item instanceof Interactables)){
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
            }
            
            x += slotSize; // Move to the next column
            if (i % cols == cols - 1) { // If we've filled up a row
                x = slotX; // Reset to the first column
                y += slotSize; // Move to the next row
            }
            i++;
        }
    }

    private void drawCursor(Graphics2D g)
    {
        // Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = slotSize;
        int cursorHeight = slotSize;

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(cursorX, cursorY, cursorWidth, cursorHeight);

        g.setColor(Color.WHITE);
        g.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);
    }
}