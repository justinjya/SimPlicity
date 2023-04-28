package src.items.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Aquarium extends Interactables {

    @Override
    public void feedingfish (Sim sim, GameTime time){
        Thread feedingfish = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Feeding Fish");
                    // count the time
                    time.startDecrementTimeRemaining(5000);
                    Thread.sleep(5000);
                    sim.setMood(sim.getMood() + 5); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        feedingfish.start();
    }
}