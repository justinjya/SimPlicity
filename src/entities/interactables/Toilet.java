package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Toilet extends Interactables{
    // Attributes
    private int price = 50;
    private int duration = 5;

    // Images of toilet
    private BufferedImage[] images;
    private BufferedImage[] icons;

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

        setPrice(price);
        setDuration(duration);

        // Load the image of the beds
        this.images = ImageLoader.loadToilet();
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getIcon() {
        return icons[getImageIndex()];
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
                    GameTime.startDecrementTimeRemaining(getDuration() * Consts.ONE_SECOND);
                    sim.setStatus("Taking a Leak");
                    Thread.sleep(getDuration() * Consts.THREAD_ONE_SECOND);
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
