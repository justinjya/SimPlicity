package src.items;

import javax.imageio.ImageTypeSpecifier;

import src.items.Item;
import src.main.GameTime;

import java.awt.image.BufferedImage;

import java.lang.Math;

import src.actions.UpgradeActions;
import src.entities.Sim;

public class Rawfood implements Item{
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


    // Atributes
    private String name;
    private int hungerPoint;
    private int price;
    private int imageIndex;

    // Images of the raw foods
    private BufferedImage[] images = new BufferedImage[8];

    // Constructor
    public Rawfood (int imageIndex) {
        this.name = names[imageIndex];
        this.hungerPoint = hungerPoints[imageIndex];
        this.price = prices[imageIndex];
        this.imageIndex = imageIndex;
        // load the images
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getHungerPoint() {
        return hungerPoint;
    }

    public int getPrice() {
        return price;
    }

    public BufferedImage getImage() {
        return images[imageIndex];
    }

    public void buy(Sim sim, GameTime time) {
        UpgradeActions.buyRawFood(this, sim, time);
    }
}
