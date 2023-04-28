package src.items.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Aquarium extends Interactables {
    private int duration = Consts.THREAD_ONE_SECOND * 5;

    // Images of the aquarium
    private BufferedImage image;

    // CONSTRUCTOR
    public Aquarium (int x, int y, GameTime time) {
        super (
            "Aquarium",
            0,
            x,
            y,
            2,
            1,
            time
        );

        // Load the image of the shower
        image = ImageLoader.readImage("tiles", "grass", 2, 1);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void changeOccupied(Sim sim) {
        if (!isOccupied()) {
            changeOccupiedState();
        }
        else {
            changeOccupiedState();
        }
    }

    @Override
    public void interact (Sim sim){
        Thread feedingfish = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Feeding Fish");
                    // count the time
                    time.startDecrementTimeRemaining(duration);
                    Thread.sleep(duration);
                    sim.resetStatus();
                    sim.setMood(sim.getMood() + 5); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        feedingfish.start();
    }
}