package src.items.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Shower extends Interactables {
    private int duration = Consts.THREAD_ONE_SECOND * 10;

    // Images of the shower
    private BufferedImage image;


    // CONSTRUCTOR
    public Shower(int x, int y, GameTime time) {
        super (
            "Shower",
            "take a shower",
            0,
            x,
            y,
            1,
            1,
            time
        );

        // Load the image of the shower
        image = ImageLoader.readImage("tiles", "grass", 1, 1, true);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void interact (Sim sim){
        Thread showering = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupied();
                    sim.setStatus("Taking a shower");
                    // count the time
                    getTime().startDecrementTimeRemaining(duration);
                    Thread.sleep(duration);
                    changeOccupied();
                    sim.resetStatus();
                    sim.setHealth(sim.getHealth() + 10); // increase sim's health
                    sim.setMood(sim.getMood() + 15); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        showering.start();
    }
    
}