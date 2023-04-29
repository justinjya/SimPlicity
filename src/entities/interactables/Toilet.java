package src.entities.interactables;

import java.io.BufferedInputStream;
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
    public Toilet(int x, int y, int imageIndex, GameTime time) {
        super (
            "Toilet",
            "takeALeak",
            imageIndex,
            x,
            y,
            1,
            1,
            time
        );

        this.price = 50;
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
        changeOccupied(sim);
        ActiveActions.takeALeak(sim, getTime());
        changeOccupied(sim);
    } 
}
