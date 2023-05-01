package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

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
            "take a leak",
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
    public void interact(Sim sim) {
        Thread takingALeak = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    GameTime.startDecrementTimeRemaining(10 * Consts.ONE_SECOND);
                    sim.setStatus("Taking a Leak");
                    Thread.sleep(10 * Consts.THREAD_ONE_SECOND);
                    changeOccupiedState();
                    sim.resetStatus();
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
