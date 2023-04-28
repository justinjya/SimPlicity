package src.entities;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import src.assets.ImageLoader;

import java.awt.Color;
import java.awt.Font;

public class Inventory {
    public int slotSize = 45;
    public int slotRow = 0;
    public int slotCol = 0;
    private boolean state = false; // true if the inventory is open, false if closed
    public boolean isObject = true;
    public ArrayList<Integer> ownedObjectsIndex = new ArrayList<Integer>();
    public ArrayList<Integer> ownedFoodsIndex = new ArrayList<Integer>();
    
    // objects = 0-11
    // foods = 12-24
    public static String[] items =
    {
        "Single Bed",
        "Queen Size Bed",
        "King Size Bed",
        "Toilet",
        "Gas Stove",
        "Electric Stove",
        "Table and Chair",
        "Clock",
        "Television",
        "Shower",
        "Aquarium",
        "Trash Bin",
        "Rice",
        "Potato",
        "Chicken",
        "Meat",
        "Carrot",
        "Spinach",
        "Peanuts",
        "Milk",
        "Chicken and Rice",
        "Curry and Rice",
        "Peanut and Milk",
        "Cut Vegetables",
        "Steak"
    };

    private int[] quantity =
    {
        1,
        2,
        3,
        1,
        4,
        0,
        0,
        0,
        0,
        0,
        0,
        1,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
    };

    private static int[] price =
    {
        50,
        100,
        150,
        50,
        100,
        200,
        50,
        10,
        200,
        100,
        20,
        10,
        5,
        3,
        10,
        12,
        3,
        3,
        2,
        2,
        0,
        0,
        0,
        0,
        0
    };    

    public Inventory() {

    }

    public boolean isOpen() {
        return state;
    }

    public void changeState() {
        state = !state;
    }

    public void resetState() {
        state = false;
    }

    public void switchCategory() {
        isObject = !isObject;
    }

    public String[] getItemList() {
        return items;
    }

    // return item index on list
    public int itemIndex(String item)
    {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        // if item not found in list, return -1 to indicate failure
        return -1;
    }

    // return item price
    public int getItemPrice(String item)
    {
        return price[itemIndex(item)];
    }
    
    // increase item's quantity by 1
    public void addItem(String item)
     {
        quantity[itemIndex(item)] += 1;
    } 
    
    // increase item's quantity with spesific quantity
    public void addItem(String item, int quantity)
    {
        this.quantity[itemIndex(item)] += quantity;
    }    

    // reduce item's quantity by 1
    public void reduceItem(String item)
    {
        this.quantity[itemIndex(item)] += 1;
    } 
    
    // reduce item's quantity with spesific quantity
    public void reduceItem(String item, int quantity)
    {
        this.quantity[itemIndex(item)] += quantity;
    } 

    // check if the sim owns spesific item
    public boolean isItemOwned(String item)
    {
        return quantity[itemIndex(item)] > 0;
    }

    public void addOwnedObjects() {
        for (int i = 0; i < 11; i++) {
            if (isItemOwned(items[i])) {
                ownedObjectsIndex.add(i);
            }
        }
    }

    public void addOwnedFoods() {
        for (int i = 12; i < 24; i++) {
            if (isItemOwned(items[i])) {
                ownedFoodsIndex.add(i);
            }
        }
    }

    public int frameX = 607;
    public int frameY = 173;
    public int frameWidth = 182;
    public int frameHeight = 262;

    public int boxWidth = (slotSize*3) + 20;
    public int boxHeight = (slotSize*4) + 20;
    public int boxX = frameX+((frameWidth-boxWidth)/2);
    public int boxY = frameY+((frameHeight-boxHeight)/2);

    public int categoryWidth = boxWidth/2;
    public int categoryHeight = 20;
    public int categoryX = boxX;
    public int categoryY = boxHeight + boxY;

    
    public int slotXstart = boxX + ((boxWidth-(slotSize*3))/2);
    public int slotYstart = boxY + ((boxHeight-(slotSize*4))/2);
    public int slotX = slotXstart;
    public int slotY = slotYstart;
    public final int slotXnext = slotXstart;
    public final int slotYnext = slotYstart;
    
    public void drawItem(Graphics2D g) {
        int nextSlotX = slotX;
        int nextSlotY = slotY;
        int next, end;

        if (isObject) {
            next = 0; end = 11;
        }

        else {
            next = 12; end = 24;
        }

        BufferedImage[] images = ImageLoader.loadItemsIcon();
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                    if (next < end) {
                        if (isItemOwned(items[next])) {
                            g.drawImage(images[next], nextSlotX, nextSlotY, images[next].getWidth(), images[next].getHeight(), null);
                            if(quantity[next] > 1) {
                                g.setColor(Color.WHITE);
                                g.fillRect(nextSlotX+31, nextSlotY+31, 14, 14);

                                g.setColor(Color.BLACK);
                                Font font = new Font("Inter", Font.BOLD, 10);
                                g.setFont(font);
                                g.drawString(Integer.toString(quantity[next]), nextSlotX+35, nextSlotY+41);
                            }
                            
                            if (nextSlotX == slotX+(2*slotSize)) {
                                nextSlotX = slotX;
                                nextSlotY += slotSize;
                            }

                            else { 
                                nextSlotX += slotSize;
                            } 
                    }
                    next++;
                }
            }
        } 
    }

    public void draw(Graphics2D g){

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
            g.drawString("Objects", categoryX+(categoryWidth/4), categoryY+(categoryHeight/2));
        }

        else {
            // Selected category = foods
            int categoryWidth = boxWidth/2;
            int categoryHeight = 20;
            int categoryX = boxX;
            int categoryY = boxHeight + boxY + categoryWidth;
            g.fillRect(categoryX, categoryY, categoryWidth, categoryHeight);
            g.setColor(Color.WHITE);
            Font font = new Font("Inter", Font.BOLD, 10);
            g.setFont(font);
            g.drawString("Foods", categoryX+categoryWidth, categoryY+(categoryHeight/2));
        }

    }
}

