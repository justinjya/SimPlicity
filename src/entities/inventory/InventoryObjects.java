package src.entities.inventory;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;

import java.awt.Color;
import java.awt.Font;

public class InventoryObjects {
    public int slotSize = 45;
    public int slotRow = 0;
    public int slotCol = 0;
    private boolean state = false; // true if the inventory is open, false if closed
    // private String[] ownedObjects;
    private static String[] objects =
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
        "Trash Bin"
    };

    private int[] quantityObjects =
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
        1
    };

    private static int[] priceObjects =
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
        10
    };

    // // create inventory
    // public InventoryObjects() {
        
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
    //     for (int i = 0; i < objects.length; i++) {
    //         if (objects[i].equals(object)) {
    //             return i;
    //         }
    //     }
    //     // if item not found in list, return -1 to indicate failure
    //     return -1;
    // }


    // // return item price
    // public int getItemPrice(String item) {
    //     return priceObjects[itemIndex(item)];
    // }
    
    // // increase item's quantity by 1
    // public void addItem(String item) {
    //     quantityObjects[itemIndex(item)] += 1;
    // } 
    
    // // increase item's quantity with spesific quantity
    // public void addItem(String item, int quantity) {
    //     quantityObjects[itemIndex(item)] += quantity;
    // }    

    // // reduce item's quantity by 1
    // public void reduceItem(String item) {
    //     quantityObjects[itemIndex(item)] += 1;
    // } 
    
    // // reduce item's quantity with spesific quantity
    // public void reduceItem(String item, int quantity) {
    //     quantityObjects[itemIndex(item)] += quantity;
    // } 

    // // check if the sim owns spesific item
    // public boolean isItemOwned(String item) {
    //     return quantityObjects[itemIndex(item)] > 0;
    // }

    // public int categoryWidth = boxWidth/2;
    // public int categoryHeight = 20;
    // public int categoryX = boxX;
    // public int categoryY = boxHeight + boxY;

    
    // public final int slotXstart = boxX + ((boxWidth-(slotSize*3))/2);
    // public final int slotYstart = boxY + ((boxHeight-(slotSize*4))/2);
    // public int slotX = slotXstart;
    // public int slotY = slotYstart;

    // public void draw(Graphics2D g) {
    //     super.draw(g);

        // // Selected category = item
        // g.fillRect(categoryX, categoryY, categoryWidth, categoryHeight);
        // g.setColor(Color.WHITE);
        // Font font = new Font("Inter", Font.BOLD, 10);
        // g.setFont(font);
        // g.drawString("Objects", categoryX+(categoryWidth/4), categoryY+(categoryHeight/2));
        
    //     int nextSlotX = slotX;
    //     int nextSlotY = slotY;
    //     int k = 0;

    //     // draw icons
    //     BufferedImage[] images = ImageLoader.loadObjectsIcon();
    //     for(int i = 0; i < 2; i++) {
    //         for (int j = 0; j < 3; j++) {
    //                 if (isItemOwned(objects[k])) {
    //                     g.drawImage(images[k], nextSlotX, nextSlotY, images[k].getWidth(), images[k].getHeight(), null);
    //                     if(quantityObjects[k] > 1) {
    //                         g.setColor(Color.WHITE);
    //                         g.fillRect(nextSlotX+31, nextSlotY+31, 14, 14);

    //                         g.setColor(Color.BLACK);
    //                         font = new Font("Inter", Font.BOLD, 10);
    //                         g.setFont(font);
    //                         g.drawString(Integer.toString(quantityObjects[k]), nextSlotX+35, nextSlotY+41);
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
