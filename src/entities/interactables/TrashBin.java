package src.entities.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

public class TrashBin extends Interactables {
    // Attributes
    private int price = 0; // TO BE DETERMINED
    private int cleaningDuration = 5; // TO BE DETERMINED

    // Images of the trash bin
    private BufferedImage[] images;
    private BufferedImage[] icons;

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
        
        setPrice(price);

        // Since the toilet has a smaller image than normal objects
        getBounds().setSize(16, 16);
        updateBounds();

        // Load the images and icons of the shower
        images = ImageLoader.loadTrashBin();
    }

    @Override
    public void changeOccupiedState() {
        this.occupied = !this.occupied;
    }

    @Override
    public void updateBounds() {
        getBounds().setLocation(getX() + 6, getY() + 40);
    }

    @Override
    public BufferedImage getIcon() {
        return icons[getImageIndex()];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    // TO - DO!!! : Change interact depending on image index
    @Override
    public void interact (Sim sim){
        if (getImageIndex() == 0) {
            setImageIndex(2);
            kickTheBin(sim);
        }
        else if (getImageIndex() == 2) {
            setImageIndex(0);
        }

        // ADD CLEAN THE BIN HERE
    }

    private void kickTheBin(Sim sim) {
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    sim.setStatus("Kicking The Bin");

                    // count the time
                    GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * getDuration());
                    Thread.sleep(Consts.THREAD_ONE_SECOND * getDuration());

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
    }

    private void cleanTheBin(Sim sim) {
        Thread cleaningTheBin = new Thread() {
            @Override
            public void run() {
                try {
                    changeOccupiedState();
                    sim.setStatus("Cleaning The Bin");

                    GameTime.startDecrementTimeRemaining(cleaningDuration * Consts.ONE_SECOND);
                    Thread.sleep(cleaningDuration * Consts.THREAD_ONE_SECOND);
                    
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