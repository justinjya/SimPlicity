package src.items.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class TrashBin extends Interactables {
    private int duration = Consts.THREAD_ONE_SECOND * 2;

    // Images of the trashbin
    private BufferedImage image;

    // CONSTRUCTOR
    public TrashBin(int x, int y, GameTime time) {
        super (
            "Trash Bin",
            "kick the trash bin",
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
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupied();
                    sim.setStatus("Kicking the Bin");
                    // count the time
                    getTime().startDecrementTimeRemaining(duration);
                    Thread.sleep(duration);
                    changeOccupied();
                    sim.resetStatus();
                    sim.setHealth(sim.getHealth() - 2); // decrease sim's health
                    sim.setHunger(sim.getHunger() - 2); // decrease sim's hunger
                    sim.setMood(sim.getMood() + 5); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        kickthebin.start();
    }
}