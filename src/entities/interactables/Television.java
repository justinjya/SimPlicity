package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.entities.handlers.KeyHandler;

import src.assets.ImageLoader;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Television extends Interactables{
    private static String[] names = {
        "Television"
    };
    private static int[] width = {
        2
    };
    private static int[] height = {
        1
    };
    private static int[] prices = {
        100
    };

    BufferedImage[] images = new BufferedImage[2];

    private int price;
    private int duration;

    public Television(int x, int y, int imageIndex) {
        super (
            names[imageIndex],
            "watch",
            imageIndex,
            x,
            y,
            width[imageIndex],
            height[imageIndex]
        );

        this.price = prices[imageIndex];
        this.images = ImageLoader.loadTelevision();
    }
    public int getPrice() {
        return price;
    }

    @Override
    public BufferedImage getIcon() {
        return images[getImageIndex()];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void interact(Sim sim){
        Thread watch = new Thread(){
            @Override
            public void run(){
                try {
                    sim.setStatus("watching");
                    GameTime.startDecrementTimeRemaining(duration);
                    
                    Thread.sleep(Consts.THREAD_ONE_SECOND * duration);
                    sim.resetStatus();
                    sim.setMood(sim.getMood() + 10);
                    sim.setHealth(sim.getHealth() - 10);
                    sim.setHunger(sim.getHunger() - 5);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
    };
    watch.start();
    }
}
