package src.entities;

public class Inventory {
    
    private static String[] items =
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

    private int[] quantityItems =
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

    public String[] getItems() {
        return items;
    }

    public String[] getFoods() {
        return foods;
    }

    public int objectIndex(String[] category, String object) {
        for (int i = 0; i < items.length; i++) {
            if (category[i].equals(object)) {
                return i;
            }
        }
        // if item not found in list, return -1 to indicate failure
        return -1;
    }
    
    public void addItem(String[] category, String object) {
        if (category.equals(items)){
            quantityItems[objectIndex(category, object)] += 1;
        } else {
            quantityFoods[objectIndex(category, object)] += 1;
        }
    } 
    
    public void addItem(String[] category, String object, int quantity) {
        if (category.equals(items)){
            quantityItems[objectIndex(category, object)] += quantity;
        } else {
            quantityFoods[objectIndex(category, object)] += quantity;
        }
    }    

    public void reduceItem(String[] category, String object) {
        if (category.equals(items)){
            quantityItems[objectIndex(category, object)] += 1;
        } else {
            quantityFoods[objectIndex(category, object)] += 1;
        }
    } 
    
    public void reduceItem(String[] category, String object, int quantity) {
        if (category.equals(items)){
            quantityItems[objectIndex(category, object)] += quantity;
        } else {
            quantityFoods[objectIndex(category, object)] += quantity;
        }
    } 


}
