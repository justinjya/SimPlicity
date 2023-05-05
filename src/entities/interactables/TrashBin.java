package src.entities.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

// TO - DO!!! : Add kicking the bin interval
public class TrashBin extends Interactables {
    // Attributes
    private int price = 10;
    private int cleaningDuration = 15;
    private Thread animateInteractThread;

    // Images of the trash bin
    private BufferedImage icon;
    private BufferedImage[] images;

    // CONSTRUCTOR
    public TrashBin() {
        super (
            "Trash Bin",
            "kick the trash bin",
            0,
            2,
            2,
            1,
            1
        );
        
        setPrice(price);

        // Since the toilet has a smaller image than normal objects
        getBounds().setSize(16, 16);
        updateBounds();

        // Load the icon and images of the trash bin
        icon = ImageLoader.loadTrashBinIcon();
        images = ImageLoader.loadTrashBin();
    }

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

        // Load the icon and images of the trash bin
        icon = ImageLoader.loadTrashBinIcon();
        images = ImageLoader.loadTrashBin();
    }

    private void animateInteract(Sim sim) {
        animateInteractThread = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Kicking the bin");
                if (getImageIndex() == 0) { // empty standing
                    try {
                        // setImageIndex(3);
                        // Thread.sleep(333);
                        setImageIndex(4);
                        Thread.sleep(333);
                        // setImageIndex(5);
                        // Thread.sleep(333);
                        sim.resetStatus();
                        setImageIndex(2); // empty on floor
                    }
                    catch (InterruptedException ie) {}
                } 
            }
        };
        animateInteractThread.start();
    }

    @Override
    public void changeOccupiedState() {
        this.occupied = !this.occupied;
    }

    @Override
    public void updateBounds() {
        getBounds().setLocation(getX() + 36, getY() + 40);
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    // TO - DO!!! : Change interact depending on image index
    @Override
    public void interact (Sim sim){
        animateInteract(sim);

        // ADD CLEAN THE BIN HERE
    }

    private void kickTheBin(Sim sim) {
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                changeOccupiedState();
                sim.setStatus("Kicking The Bin");

                // count the time
                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * getDuration());
                while (t.isAlive()) {
                    continue;
                }

                changeOccupiedState();
                sim.resetStatus();
                sim.setHealth(sim.getHealth() - 2); // decrease sim's health
                sim.setHunger(sim.getHunger() - 2); // decrease sim's hunger
                sim.setMood(sim.getMood() + 5); // increase sim's mood
            }
        };
        kickthebin.start();
    }

    private void cleanTheBin(Sim sim) {
        Thread cleaningTheBin = new Thread() {
            @Override
            public void run() {
                changeOccupiedState();
                sim.setStatus("Cleaning The Bin");

                Thread t =GameTime.startDecrementTimeRemaining(cleaningDuration * Consts.ONE_SECOND);
                
                while (t.isAlive()) {
                    continue;
                }
                
                changeOccupiedState();
                sim.setMood(sim.getMood() + 10);
                sim.setHealth(sim.getHealth() + 10);
                sim.setHunger(sim.getHunger() - 10);
            }
        };
        cleaningTheBin.start();
    }
}