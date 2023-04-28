package src.items.interactables;

import java.awt.image.BufferedImage;

import src.entities.Interactables;
import src.main.GameTime;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;

public class Television extends Interactables {
    private static String names = TV;
    private static int width = 2;
    private static int height = 2;
    private int price;
    private int duration;
    private BufferedImage [] images = new BufferedImage[4];

    public Television(int x, int y, int imageIndex, GameTime time){
        super(
            names[imageIndex],
            "watching TV", imageIndex,
            x,
            y,
            width,
            height,
            time
        )
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void changeOccupied(Sim sim) {
        if (!isOccupied()) {
            changeOccupiedState();
            setImageIndex(getImageIndex() + 1);
        }
        else {
            changeOccupiedState();
            setImageIndex(getImageIndex() - 1);
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
                    sim.setStatus("Watching TV");

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
