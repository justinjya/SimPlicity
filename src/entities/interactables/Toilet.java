package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.entities.sim.Sim;
import src.main.GameTime;
import src.entities.sim.actions.ActiveActions;

public class Toilet extends Interactables{
    // Images of toilet
    BufferedImage[] images = new BufferedImage[1]; // will be adding more if available

    // Attributes
    private int price;
    private int duration;

    // CONSTRUCTOR
    public Toilet(int x, int y, int imageIndex) {
        super (
            "Toilet",
            "takeALeak",
            imageIndex,
            x,
            y,
            1,
            1
        );

        this.price = 50;
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
        changeOccupiedState();
        ActiveActions.takeALeak(sim, time);
        changeOccupiedState();
    } 
}
