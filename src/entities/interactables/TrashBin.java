package src.entities.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

public class TrashBin extends Interactables {
    private int duration = Consts.ONE_SECOND * 2;

    // Images of the trashbin
    private BufferedImage image;

    // CONSTRUCTOR
    public TrashBin(int x, int y) {
        super (
            "Trash Bin",
            "kick the trash bin",
            0,
            x,
            y,
            1,
            1
        );

        // Load the image of the shower
        image = ImageLoader.readImage("tiles", "grass", 1, 1, true);
    }

    @Override
    public BufferedImage getIcon() {
        return image;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    // TO - DO!!! : Change interact depending on image index
    @Override
    public void interact (Sim sim){
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    sim.setStatus("Kicking The Bin");
                    // count the time
                    GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * duration);
                    Thread.sleep(Consts.THREAD_ONE_SECOND * duration);
                    changeOccupiedState();
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

        Thread cleaningTheBin = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    sim.setStatus("Cleaning The Bin");
                    GameTime.startDecrementTimeRemaining(10*Consts.ONE_SECOND);
                    Thread.sleep(10*Consts.THREAD_ONE_SECOND);
                    changeOccupiedState();
                    sim.setMood(sim.getMood() + 10);
                    sim.setHealth(sim.getHealth() + 10);
                    sim.setHunger(sim.getHunger() - 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        cleaningTheBin.start();
    }
}