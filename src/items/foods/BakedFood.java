package src.items.foods;

import java.awt.image.BufferedImage;

import src.items.Item;

public class BakedFood implements Item{
    // Types of baked food
    private static String[] names = {
        "Nasi Ayam",
        "Nasi Kari",
        "Susu Kacang",
        "Tumis Sayur",
        "Bistik"
    };
    private static int[] hungerPoints = {
        16,
        30,
        5,
        5,
        22
    };
    private static String[][] ingredients = {
        {"Nasi", "Ayam"},
        {"Nasi", "Kentang", "Wortel", "Sapi"},
        {"Susu", "Kacang"},
        {"Wortel", "Bayam"},
        {"Kentang", "Sapi"}
    };

    // Atributes
    private String name;
    private int hungerPoint;
    private String[] ingredient;
    private int imageIndex;

    // Images of the raw foods
    private BufferedImage[] images = new BufferedImage[5];

    // Constructor
    public BakedFood (int imageIndex) {
        this.name = names[imageIndex];
        this.hungerPoint = hungerPoints[imageIndex];
        this.ingredient = ingredients[imageIndex];
        this.imageIndex = imageIndex;
        // load the images here
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getHungerPoint() {
        return hungerPoint;
    }

    public String[] getIngredients() {
        return ingredient;
    }

    public BufferedImage getIcon() {
        return images[imageIndex];
    }
    
}
