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
    private int price;
    private int duration = Consts.ONE_SECOND * 10; // Change this to * 4 once the project is done

    // Image of the beds
    private BufferedImage[] icons = new BufferedImage[3];
    private BufferedImage[] images = new BufferedImage[6];

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

        // Load the image of the beds
        icons = ImageLoader.loadBedsIcons();
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

        this.price = prices[imageIndex];

        // Load the image of the beds
        icons = ImageLoader.loadBedsIcons();
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

        // Load the image of the beds
        icons = ImageLoader.loadBedsIcons();
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
                try {
                    changeOccupiedState();
                    BufferedImage initialImage = images[getImageIndex()];
                    images[getImageIndex()] = ImageLoader.changeSimColor(images[getImageIndex()], sim);
                    
                    sim.setStatus("Sleeping");
                    GameTime.startDecrementTimeRemaining(duration);

                    Thread.sleep(Consts.THREAD_ONE_SECOND * duration);

                    images[getImageIndex()] = initialImage;
                    
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