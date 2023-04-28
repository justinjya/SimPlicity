package src.entities.inventory;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;

import java.awt.Color;
import java.awt.Font;

public class InventoryFoods {
    public int slotSize = 45;
    public int slotRow = 0;
    public int slotCol = 0;
    private boolean state = false; // true if the inventory is open, false if closed
    // private String[] ownedFoods;

    protected static String[] foods =
    {
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

    protected int[] quantityFoods =
    {
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

    protected static int[] priceFoods =
    {
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

    // // create inventory
    // public InventoryFoods() {
        
    // }

    // public boolean isOpen() {
    //     return state;
    // }

    // public void changeState() {
    //     state = !state;
    // }

    // public void resetState() {
    //     state = false;
    // }

    // // return item index on list
    // public int itemIndex(String object) {
    //     for (int i = 0; i < foods.length; i++) {
    //         if (foods[i].equals(object)) {
    //             return i;
    //         }
    //     }
    //     // if item not found in list, return -1 to indicate failure
    //     return -1;
    // }

    // public int itemOwned() {
    //     int count = 0;
    //     for (int i = 0; i < quantityFoods.length; i++) {
    //         if (quantityFoods[i] > 0) {
    //             count++;
    //         }
    //     }
    //     return count;
    // }

    // // return item price
    // public int getItemPrice(String item) {
    //     return priceFoods[itemIndex(item)];
    // }
    
    // // increase item's quantity by 1
    // public void addItem(String item) {
    //     quantityFoods[itemIndex(item)] += 1;
    // } 
    
    // // increase item's quantity with spesific quantity
    // public void addItem(String item, int quantity) {
    //     quantityFoods[itemIndex(item)] += quantity;
    // }    

    // // reduce item's quantity by 1
    // public void reduceItem(String item) {
    //     quantityFoods[itemIndex(item)] += 1;
    // } 
    
    // // reduce item's quantity with spesific quantity
    // public void reduceItem(String item, int quantity) {
    //     quantityFoods[itemIndex(item)] += quantity;
    // } 

    // // check if the sim owns spesific item
    // public boolean isItemOwned(String item) {
    //     return quantityFoods[itemIndex(item)] > 0;
    // }

    // public void draw(Graphics2D g) {
    //     super.draw(g);

        // // Selected category = item
        // int categoryWidth = boxWidth/2;
        // int categoryHeight = 20;
        // int categoryX = boxX;
        // int categoryY = boxHeight + boxY + categoryWidth;
        // g.fillRect(categoryX, categoryY, categoryWidth, categoryHeight);
        // g.setColor(Color.WHITE);
        // Font font = new Font("Inter", Font.BOLD, 10);
        // g.setFont(font);
        // g.drawString("Foods", categoryX+categoryWidth, categoryY+(categoryHeight/2));
        
    //     // Slot
    //     final int slotXstart = boxX + ((boxWidth-(slotSize*3))/2);
    //     final int slotYstart = boxY + ((boxHeight-(slotSize*4))/2);
    //     int slotX = slotXstart;
    //     int slotY = slotYstart;

    //     // cursor
    //     int cursorX = slotXstart + (slotSize * slotCol);
    //     int cursorY = slotYstart + (slotSize * slotRow);
    //     int cursorWidth = slotSize;
    //     int cursorHeight = slotSize;
    //     g.setColor(Color.LIGHT_GRAY);
    //     g.fillRect(cursorX, cursorY, cursorWidth, cursorHeight);
    //     g.setColor(Color.WHITE);
    //     g.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);
        
    //     int nextSlotX = slotX;
    //     int nextSlotY = slotY;
    //     int k = 0;

    //     // draw icons
    //     BufferedImage[] images = ImageLoader.loadFoodsIcon();
    //     for(int i = 0; i < 2; i++) {
    //         for (int j = 0; j < 3; j++) {
    //                 if (isItemOwned(foods[k])) {
    //                     g.drawImage(images[k], nextSlotX, nextSlotY, images[k].getWidth(), images[k].getHeight(), null);
    //                     if(quantityFoods[k] > 1) {
    //                         g.setColor(Color.WHITE);
    //                         g.fillRect(nextSlotX+31, nextSlotY+31, 14, 14);

    //                         g.setColor(Color.BLACK);
    //                         font = new Font("Inter", Font.BOLD, 10);
    //                         g.setFont(font);
    //                         g.drawString(Integer.toString(quantityFoods[k]), nextSlotX+35, nextSlotY+41);
    //                     }
                        
    //                     if (nextSlotX == slotX+(2*slotSize)) {
    //                         nextSlotX = slotX;
    //                         nextSlotY += slotSize;
    //                     }

    //                     else { 
    //                         nextSlotX += slotSize;
    //                     }
    //                     k++;
    //             }
    //         }
    //     } 
    // }
}
