package src.entities.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Aquarium extends Interactables {
    private int duration = Consts.ONE_SECOND * 5;

    // Images of the aquarium
    private BufferedImage icon;
    private BufferedImage[] images;

    // CONSTRUCTOR
    public Aquarium () {
        super (
            "Aquarium",
            "feed the fish",
            0,
            2,
            3,
            2,
            1
        );

        // Load the icon and image of the aquarium
        icon = ImageLoader.loadAquariumIcon();
        images = ImageLoader.loadAquarium();
    }

    public Aquarium (int x, int y) {
        super (
            "Aquarium",
            "feed the fish",
            0,
            x,
            y,
            2,
            1
        );

        // Load the icon and image of the aquarium
        icon = ImageLoader.loadAquariumIcon();
        images = ImageLoader.loadAquarium();
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
        Thread feedingfish = new Thread() {
            @Override
            public void run() {
                changeOccupiedState();
                sim.setStatus("Feeding the fish");
                // count the time
                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * duration);
                
                while (t.isAlive()) {
                    continue;
                }
                
                changeOccupiedState();
                sim.resetStatus();
                sim.setMood(sim.getMood() + 5); // increase sim's mood
            }
        };
        feedingfish.start();
    }
}