package src.items.foods;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.items.Item;

public class BakedFood extends Food implements Item{
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
    private String[] ingredient;
    private int imageIndex;

    // Images of the raw foods
    private BufferedImage[] icons = new BufferedImage[5];

    // ONLY FOR DEBUGGING
    private BufferedImage placeholder = ImageLoader.readImage("inventory", "gas_stove", 1, 1, false);

    // Constructor
    public BakedFood (int imageIndex) {
        super (
            names[imageIndex],
            hungerPoints[imageIndex],
            imageIndex
        );
        this.ingredient = ingredients[imageIndex];
        this.imageIndex = imageIndex;
        // load the images here
        // this.icons = ImageLoader.loadBakedFood();
    }

    // Getters
    public String[] getIngredients() {
        return ingredient;
    }

    public BufferedImage getIcon() {
        // return images[imageIndex];
        
        // ONLY FOR DEBUGGING
        return placeholder;
    }
}
