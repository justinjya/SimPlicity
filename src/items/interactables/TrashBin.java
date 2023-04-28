package src.items.interactables;

import java.awt.image.BufferedImage;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class TrashBin extends Interactables {

    @Override
    public void kickthebin (Sim sim, GameTime time){
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Kicking The Bin");
                    // count the time
                    time.startDecrementTimeRemaining(2000);
                    Thread.sleep(2000);
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