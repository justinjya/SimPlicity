package src.items.interactables;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.main.Consts;
import src.main.GameTime;
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

    // Image of the beds
    private BufferedImage[] images = new BufferedImage[2]; // Will increase if more bed images are available

    public Bed(int x, int y, int imageIndex, GameTime time) {
        super(names[imageIndex], "Sleep", imageIndex, x, y, Consts.SCALED_TILE * width[imageIndex], Consts.SCALED_TILE * height[imageIndex], time);
        this.price = prices[imageIndex];

        // Load the image of the beds
        try {
            this.images[0] = ImageIO.read(new File("./src/assets/bed_idle.png"));
            this.images[1] = ImageIO.read(new File("./src/assets/bed_occupied.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupied(sim);
                    getTime().startDecrementTimeRemaining(Consts.ONE_MINUTE / 4);
                    sim.setStatus("Sleeping");

                    Thread.sleep(Consts.ONE_MINUTE / 4);
                    
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
        thread.start();
    }
}