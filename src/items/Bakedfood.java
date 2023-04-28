package src.items;

import src.items.Item;

import java.awt.image.BufferedImage;


public class Bakedfood implements Item{
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
    public Bakedfood (int imageIndex) {
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

    public String[] getPrice() {
        return ingredient;
    }

    public BufferedImage getImage() {
        return images[imageIndex];
    }
    
}
