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

    public Bed(int x, int y, int imageIndex, GameTime time) {
        super(names[imageIndex], "Sleep", imageIndex, x, y,
            Consts.SCALED_TILE * width[imageIndex], Consts.SCALED_TILE * height[imageIndex], time);
        this.price = prices[imageIndex];
        this.duration = Consts.ONE_MINUTE / 4; // Change this to * 4 once the project is done

        // Load the image of the beds
        images = ImageLoader.loadBeds();
    }

    public int getPrice() {
        return price;
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void changeOccupied(Sim sim) {
        if (!isOccupied()) {
            setOccupied(true);
            setImageIndex(getImageIndex() + 1);
            sim.setBusy(true);
        }
        else {
            setOccupied(false);
            setImageIndex(getImageIndex() - 1);
            sim.setBusy(false);
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