package src.items.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Shower extends Interactables {

    public Shower (GameTime time)

    @Override
    public void shower (Sim sim, GameTime time){
        Thread showering = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Showering");
                    // count the time
                    time.startDecrementTimeRemaining(10000);
                    Thread.sleep(10000);
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