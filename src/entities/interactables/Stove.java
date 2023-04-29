package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.entities.sim.Sim;
import src.main.GameTime;

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
    public Stove(int x, int y, int imageIndex) {
        super (
            names[imageIndex],
            "cook",
            imageIndex,
            x,
            y,
            width[imageIndex],
            height[imageIndex]
        );

        this.price = prices[imageIndex];
        // Load the image of the beds
        
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getIcon() {
        return images[getImageIndex()];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void interact(Sim sim, GameTime time) {
        // call ActiveAction.cook, but dont know how to insert the parameter required by cook since interact can only accept sim
        // my view: 
        // changeOccupied(sim);
        // ActiveAction.cook(bakedfood, null, sim, getTime()); but again how to insert bakedfood :v
        // changeOccupied(sim);
    } 
}
