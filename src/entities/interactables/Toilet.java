package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.GameTime;
import src.entities.sim.actions.ActiveActions;

public class Toilet extends Interactables{
    // Images of toilet
    BufferedImage[] images = new BufferedImage[2]; // for idle toilet and occupied toilet

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
        this.images = ImageLoader.loadToilet();
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
        Thread takingALeak = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    time.startDecrementTimeRemaining(10*1000);
                    sim.setStatus("TakingALeak");
                    Thread.sleep(10*1000);
                    changeOccupiedState();
                    sim.setHunger(sim.getHunger() - 20);
                    sim.setMood(sim.getMood() + 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        };
        takingALeak.start();
    } 
}
