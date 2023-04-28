package src.items.interactables;

import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.GameTime;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;

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
    private BufferedImage[] images = new BufferedImage[6]; // Will increase if more bed images are available

    // CONSTRUCTOR
    public Bed(int x, int y, int imageIndex, GameTime time) {
        super (
            names[imageIndex],
            "sleep",
            imageIndex,
            x,
            y,
            width[imageIndex],
            height[imageIndex],
            time
        );

        this.price = prices[imageIndex];
        this.duration = Consts.ONE_MINUTE / 4; // Change this to * 4 once the project is done

        // Load the image of the beds
        images = ImageLoader.loadBeds();
    }

    // ONLY FOR DEBUGGING
    public Bed(GameTime time) {
        super (
            names[0],
            "sleep",
            0,
            (Consts.CENTER_X / 2) + 76,
            Consts.CENTER_Y + 15,
            width[0],
            height[0],
            time
        );
        
        this.price = prices[0];
        this.duration = Consts.ONE_MINUTE / 4; // Change this to * 4 once the project is done

        // Load the image of the beds
        images = ImageLoader.loadBeds();
    }

    // GETTERS
    public int getPrice() {
        return price;
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void changeOccupied(Sim sim) {
        if (!isOccupied()) {
            changeOccupiedState();
            setImageIndex(getImageIndex() + 1);
        }
        else {
            changeOccupiedState();
            setImageIndex(getImageIndex() - 1);
        }
    }

    @Override
    public void interact(Sim sim) {
        Thread interacting = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupied(sim);
                    getTime().startDecrementTimeRemaining(duration);
                    sim.setStatus("Sleeping");

                    Thread.sleep(duration);
                    
                    changeOccupied(sim);
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