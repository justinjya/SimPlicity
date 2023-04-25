package src.entities;
import java.awt.Graphics2D;
import java.awt.Color;

public class Inventory {
    private boolean state = false;
    // true if the inventory is open, false if closed

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

    private static String[] foods =
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

    private int[] quantityFoods =
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

    private static int[] priceFoods =
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

    // create inventory
    public Inventory() {

    }

    public boolean isOpen() {
        return state;
    }

    public void changeState() {
        state = !state;
    }

    public String[] getObjects() {
        return objects;
    }

    public String[] getFoods() {
        return foods;
    }

    // return item index on list
    public int itemIndex(String[] category, String object) {
        for (int i = 0; i < category.length; i++) {
            if (category[i].equals(object)) {
                return i;
            }
        }
        // if item not found in list, return -1 to indicate failure
        return -1;
    }

    // return item price
    // TO-DO : handle not for sale items (price = 0)
    public int getItemPrice(String[] category, String item) {
        if (category.equals(objects)){
            return priceObjects[itemIndex(category, item)];
        } else {
            return priceFoods[itemIndex(category, item)];
        }
    }
    
    // increase item's quantity by 1
    public void addItem(String[] category, String item) {
        if (category.equals(objects)){
            quantityObjects[itemIndex(category, item)] += 1;
        } else {
            quantityFoods[itemIndex(category, item)] += 1;
        }
    } 
    
    // increase item's quantity with spesific quantity
    public void addItem(String[] category, String item, int quantity) {
        if (category.equals(objects)){
            quantityObjects[itemIndex(category, item)] += quantity;
        } else {
            quantityFoods[itemIndex(category, item)] += quantity;
        }
    }    

    // reduce item's quantity by 1
    public void reduceItem(String[] category, String item) {
        if (category.equals(objects)){
            quantityObjects[itemIndex(category, item)] += 1;
        } else {
            quantityFoods[itemIndex(category, item)] += 1;
        }
    } 
    
    // reduce item's quantity with spesific quantity
    public void reduceItem(String[] category, String item, int quantity) {
        if (category.equals(objects)){
            quantityObjects[itemIndex(category, item)] += quantity;
        } else {
            quantityFoods[itemIndex(category, item)] += quantity;
        }
    } 

    // check if the sim owns spesific item
    public boolean isItemOwned(String[] category, String item) {
        if (category.equals(objects)){
            return quantityObjects[itemIndex(category, item)] > 0;
        } else {
            return quantityObjects[itemIndex(category, item)] > 0;
        }
    }

    public void draw(Graphics2D g){
        
        // Frame
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(607, 173, 182, 262);

        // Box
        g.setColor(Color.GRAY);
        g.fillRect(607+11, 173+54-26, 160, 189);

        // Slot
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(607+11+10, 173+54-26+10, 32, 32);

    }

    // public void 

    // public void showOwnedItems() {
    //     for (int i = 0; i < objects.length; i++) {
    //         if (quantityObjects[i] > 0) {
    //             System.out.print(objects[i] + ": ");
    //             System.out.println(quantityObjects);
    //         }
    //     }
    // }
}
