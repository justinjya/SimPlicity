package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.GameTime;
import src.assets.ImageLoader;
import src.entities.sim.Sim;

public class Bed extends Interactables{
    // Types of beds
    private static String[] names = {
        "Kasur Single",
        "Kasur Queen Size",
        "Kasur King Size"
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

    // Atributes
    private int price;
    private int duration;

    // Image of the beds
    private BufferedImage[] images = new BufferedImage[9];

    // CONSTRUCTOR
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

        this.price = prices[imageIndex];
        this.duration = Consts.THREAD_ONE_MINUTE / 4; // Change this to * 4 once the project is done

        // Load the image of the beds
        images = ImageLoader.loadBeds();
    }

    public Bed(int imageIndex) {
        super (
            names[imageIndex],
            "sleep",
            imageIndex,
            0,
            3,
            width[imageIndex],
            height[imageIndex]
        );

        // Load the image of the beds
        images = ImageLoader.loadBeds();
    }

    // ONLY FOR DEBUGGING
    public Bed() {
        super (
            names[1],
            "sleep",
            1,
            0,
            3,
            width[1],
            height[1]
        );
        
        this.price = prices[1];
        this.duration = Consts.THREAD_ONE_MINUTE / 4; // Change this to * 4 once the project is done

        // Load the image of the beds
        images = ImageLoader.loadBeds();
    }

    // GETTERS
    public int getPrice() {
        return price;
    }

    @Override
    public void changeOccupiedState() {
        if (!isOccupied()) {
            setImageIndex(getImageIndex() + 3);
        }
        else {
            setImageIndex(getImageIndex() - 3);
        }
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getIcon() {
        return images[getImageIndex() + 6];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void interact(Sim sim, GameTime time) {
        Thread interacting = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    time.startDecrementTimeRemaining(duration);
                    sim.setStatus("Sleeping");

                    Thread.sleep(duration);
                    
                    changeOccupiedState();
                    sim.resetStatus();
                    sim.setHealth(sim.getHealth() + 30);
                    sim.setMood(sim.getMood() + 20);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        interacting.start();
    }
}