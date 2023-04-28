package src.items.interactables;

import java.io.BufferedInputStream;
import java.awt.image.BufferedImage;

import src.entities.Interactables;

import src.main.GameTime;
import src.entities.Sim;

public class Stove extends Interactables{
    // Types of stove
    private static String[] names = {
        "Kompor Gas",
        "Kompor Listrik"
    };
    private static int[] width = {
        2,
        1
    };
    private static int[] height = {
        1,
        1
    };
    private static int[] prices = {
        100,
        200
    };
    // Images of stove
    BufferedImage[] images = new BufferedImage[2];

    // Attributes
    private int price;
    private int duration;

    // CONSTRUCTOR
    public Stove(int x, int y, int imageIndex, GameTime time) {
        super (
            names[imageIndex],
            "cook",
            imageIndex,
            x,
            y,
            width[imageIndex],
            height[imageIndex],
            time
        );

        this.price = prices[imageIndex];
        // Load the image of the beds
        
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void changeOccupied(Sim sim) {
        changeOccupiedState();
        if (!isOccupied()) {
            //setImageIndex(getImageIndex() + 1);
        }
        else {
            //setImageIndex(getImageIndex() - 1);
        }
    }

    @Override
    public void interact(Sim sim) {
        // call ActiveAction.cook, but dont know how to insert the parameter required by cook since interact can only accept sim
        // my view: 
        // changeOccupied(sim);
        // ActiveAction.cook(bakedfood, null, sim, getTime()); but again how to insert bakedfood :v
        // changeOccupied(sim);
    } 
}
