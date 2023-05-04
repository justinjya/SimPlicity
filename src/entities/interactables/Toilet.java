package src.entities.interactables;

import java.awt.Graphics;
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
    private BufferedImage icon;
    private BufferedImage[] images;

    // CONSTRUCTOR
    public Toilet() {
        super (
            "Toilet",
            "take a leak",
            0,
            2,
            2,
            1,
            1
        );

        setPrice(price);
        setDuration(duration);

        // Load the icons and image of the toilet
        this.icon = ImageLoader.loadToiletIcon();
        this.images = ImageLoader.loadToilet();
    }

    public Toilet(int x, int y) {
        super (
            "Toilet",
            "take a leak",
            0,
            x,
            y,
            1,
            1
        );

        setPrice(price);
        setDuration(duration);

        // Load the icons and image of the toilet
        this.icon = ImageLoader.loadToiletIcon();
        this.images = ImageLoader.loadToilet();
    }

    private static BufferedImage combine(BufferedImage image1, BufferedImage image2) {
        // Create the new image
        BufferedImage combined = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
    
        // Draw the first image
        g.drawImage(image1, 0, 0, null);
    
        // Draw the second image
        g.drawImage(image2, 0, 0, null);
    
        // Dispose of the graphics object
        g.dispose();
    
        return combined;
    }

    public void rotate() {
        images[0] = ImageLoader.rotate90Clockwise(images[0]);
        images[1] = ImageLoader.rotate90Clockwise(images[1]);
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public BufferedImage getImage() {
        BufferedImage toiletImage = combine(images[2], images[getImageIndex()]);
        return toiletImage;
    }

    @Override
    public void interact(Sim sim) {
        Thread takingALeak = new Thread() {
            @Override
            public void run() {
                changeOccupiedState();
                Thread t = GameTime.startDecrementTimeRemaining(getDuration() * Consts.ONE_SECOND);
                sim.setStatus("Taking a Leak");
                
                while (t.isAlive()) {
                    continue;
                }

                changeOccupiedState();
                sim.resetStatus();
                sim.setHunger(sim.getHunger() - 20);
                sim.setMood(sim.getMood() + 10);
            }
        };
        takingALeak.start();
    } 
}
