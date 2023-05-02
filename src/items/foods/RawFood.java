package src.items.foods;

import java.awt.image.BufferedImage;

import src.items.Item;
import src.assets.ImageLoader;

public class RawFood extends Food implements Item{
    // Types of raw food
    private static String[] names = {
        "Nasi",
        "Kentang",
        "Ayam",
        "Sapi",
        "Wortel",
        "Bayam",
        "Kacang",
        "Susu"
    };
    private static int[] hungerPoints = {
        5,
        3,
        10,
        12,
        3,
        3,
        2,
        2
    };
    private static int[] prices = {
        5,
        4,
        8,
        15,
        2,
        2,
        2,
        1
    };

    // Attributes
    private int price;

    // Images of the raw foods
    private BufferedImage[] icons = new BufferedImage[8];

    private BufferedImage placeholder = ImageLoader.readImage("inventory", "gas_stove", 1, 1, false);

    // Constructor
    public RawFood (int imageIndex) {
        super (
            names[imageIndex],
            hungerPoints[imageIndex],
            imageIndex
        );
        this.price = prices[imageIndex];
        
        // load the images
        this.icons = ImageLoader.loadRawFood();
    }

    // Getters
    public int getPrice() {
        return price;
    }

    // Implementation of abstract methods
    @Override
    public BufferedImage getIcon() {
        return icons[getImageIndex()];

        // ONLY FOR DEBUGGING
        // return placeholder;
    }
}
