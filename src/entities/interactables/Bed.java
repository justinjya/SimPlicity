package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.GameTime;
import src.assets.ImageLoader;
import src.entities.sim.Sim;

public class Bed extends Interactables{
    // Types of beds
    private static String[] names = {
        "Single Bed",
        "Queen Size Bed",
        "King Size Bed"
    };
    private static int[] width = {
        4,
        4,
        5
    };
    private static int[] height = {
        1,
        2,
        2
    };
    private static int[] prices = {
        50,
        100,
        150
    };

    // Attributes
    private int duration = Consts.ONE_MINUTE / 10; // CHANGE TO * 4 ONCE PROJECT IS DONE

    // Image of the beds
    private BufferedImage[] icons;
    private BufferedImage[] images;

    // CONSTRUCTOR
    public Bed(int imageIndex) {
        super (
            names[imageIndex],
            "sleep",
            imageIndex,
            1,
            2,
            width[imageIndex],
            height[imageIndex]
        );
        if (imageIndex == 1) {
            setPlayAreaY(1);
        }
        else if (imageIndex == 2) {
            setPlayAreaX(0);
            setPlayAreaY(2);
        }

        setPrice(prices[imageIndex]);
        setDuration(duration);

        // Load the image of the beds
        icons = ImageLoader.loadBedsIcons();
        images = ImageLoader.loadBeds();
    }

    public Bed(int x, int y, int imageIndex) {
        super (
            names[imageIndex],
            "sleep",
            imageIndex,
            x,
            y,
            width[imageIndex],
            height[imageIndex]
        );

        setPrice(prices[imageIndex]);
        setDuration(duration);

        // Load the image of the beds
        icons = ImageLoader.loadBedsIcons();
        images = ImageLoader.loadBeds();
    }

    @Override
    public void changeOccupiedState() {
        if (!isOccupied()) {
            setImageIndex(getImageIndex() + 3);
        }
        else {
            setImageIndex(getImageIndex() - 3);
        }

        this.occupied = !this.occupied;
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
        Thread interacting = new Thread() {
            @Override
            public void run() {
                changeOccupiedState();
                BufferedImage initialImage = images[getImageIndex()]; // TO-DO!!! : Fix color change
                images[getImageIndex()] = ImageLoader.changeSimColor(images[getImageIndex()], sim);
                
                sim.setStatus("Sleeping");
                Thread t = GameTime.startDecrementTimeRemaining(duration);

                while (t.isAlive()) {
                    continue;
                }

                images[getImageIndex()] = initialImage;
                
                changeOccupiedState();
                sim.resetStatus();
                sim.setHealth(sim.getHealth() + 30);
                sim.setMood(sim.getMood() + 20);
            }
        };
        interacting.start();
    }
}