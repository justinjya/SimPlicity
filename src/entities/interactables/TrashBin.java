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
    private int kickingDuration = 0;
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
            2,
            1
        );
        
        setDuration(kickingDuration);
        setPrice(price);

        // Since the trash bin has a smaller image than normal objects
        getBounds().setSize(16, 16);
        updateBounds();

        // Load the icon and images of the trash bin
        icon = ImageLoader.loadTrashBinIcon();
        images = ImageLoader.loadTrashBin();
    }

    public TrashBin(int imageIndex, int x, int y) {
        super (
            "Trash Bin",
            "kick the trash bin",
            imageIndex,
            x,
            y,
            2,
            1
        );
        
        setDuration(kickingDuration);
        setPrice(price);

        // Since the trash bin has a smaller image than normal objects
        getBounds().setSize(16, 16);
        updateBounds();

        // Load the icon and images of the trash bin
        icon = ImageLoader.loadTrashBinIcon();
        images = ImageLoader.loadTrashBin();
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

    @Override
    public void interact (Sim sim){
        images = ImageLoader.loadTrashBin();

        // change the color of the sim for all the animation images
        for (int i = 4; i < 9; i++) {
            images[i] = ImageLoader.changeSimColor(images[i], sim);
        }

        if (getImageIndex() == 0 || getImageIndex() == 1) {
            kickTheBin(sim);
        }
        if (getImageIndex() == 2 || getImageIndex() == 3) {
            cleanTheBin(sim);
        }
    }

    private void kickTheBin(Sim sim) {
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Kicking The Bin");

                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * kickingDuration);
                animateInteract(sim, t);

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
                sim.setStatus("Cleaning The Bin");

                if (getImageIndex() == 2) { // empty on floor
                    cleaningDuration = 0;
                    Thread t = GameTime.startDecrementTimeRemaining(cleaningDuration * Consts.ONE_SECOND);
                    animateInteract(sim, t);
                    return;
                }

                cleaningDuration = 15;

                Thread t = GameTime.startDecrementTimeRemaining(cleaningDuration * Consts.ONE_SECOND);
                animateInteract(sim, t);
                
                while (t.isAlive()) continue;
                
                sim.setMood(sim.getMood() + 10);
                sim.setHealth(sim.getHealth() + 10);
                sim.setHunger(sim.getHunger() - 10);
            }
        };
        cleaningTheBin.start();
    }

    private void animateInteract(Sim sim, Thread t) {
        animateInteractThread = new Thread() {
            @Override
            public void run() {
                if (getImageIndex() == 0) { // empty standing
                    try {
                        setImageIndex(4);
                        Thread.sleep(333);
                        sim.resetStatus();
                        setImageIndex(2); // empty on floor
                    }
                    catch (InterruptedException ie) {}
                }
                else if (getImageIndex() == 2) { // empty on floor
                    try {
                        setImageIndex(5);
                        Thread.sleep(333);
                        sim.resetStatus();
                        setImageIndex(0); // empty standing
                    }
                    catch (InterruptedException ie) {}
                }
                else if (getImageIndex() == 1) { // filled standing
                    try {
                        setImageIndex(6);
                        Thread.sleep(333);
                        sim.resetStatus();
                        setImageIndex(3); // filled on floor
                    }
                    catch (InterruptedException ie) {}
                }
                else if (getImageIndex() == 3) { // filled on floor
                    try {
                        changeOccupiedState();
                        while (t.isAlive()) {
                            setImageIndex(7);
                            Thread.sleep(333);
                            setImageIndex(8);
                            Thread.sleep(333);
                        }
                        changeOccupiedState();
                        sim.resetStatus();
                        setImageIndex(0); // empty standing
                    }
                    catch (InterruptedException ie) {}
                }
            }
        };
        animateInteractThread.start();
    }
}