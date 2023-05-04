package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.main.KeyHandler;
import src.main.GameTime;
import src.main.Consts;
import src.entities.sim.Sim;
import src.main.UserInterface;
import src.main.menus.InteractMenu;

public class Television extends Interactables{
    // Attributes
    private int price = 100;
    private int duration = 10; // TO BE DETERMINED

    private BufferedImage icon;
    private BufferedImage[] images;

    public Television() {
        super (
            "Television",
            "start karaoke-ing or watch the TV",
            0,
            2,
            2,
            2,
            1
        );

        setPrice(price);
        setDuration(duration);

        this.icon = ImageLoader.loadTelevisionIcon();
        this.images = ImageLoader.loadTelevision();
    }

    public Television(int x, int y) {
        super (
            "Television",
            "start karaoke-ing or watch the TV",
            0,
            x,
            y,
            2,
            1
        );

        setPrice(price);
        setDuration(duration);

        this.icon = ImageLoader.loadTelevisionIcon();
        this.images = ImageLoader.loadTelevision();
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
    public void interact(Sim sim){
        Thread choosingInteract = new Thread() {
            @Override
            public void run() {
                UserInterface.viewInteractions();

                while (UserInterface.isViewingInteractions()) {
                    if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
                        UserInterface.viewInteractions();
                        break;
                    }
                }

                if (InteractMenu.slotSelected == -1) return;
                else if (InteractMenu.slotSelected == 0) karaoke(sim);
                else if (InteractMenu.slotSelected == 1) watch(sim);
                InteractMenu.slotSelected = 0;
            }
        };
        choosingInteract.start();
    }

    private void karaoke(Sim sim) {
        Thread karaoke = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Karaoke-ing");
                changeOccupiedState();
                Thread t = GameTime.startDecrementTimeRemaining(duration * Consts.ONE_SECOND);
                
                while (t.isAlive()) continue;

                changeOccupiedState();
                sim.setMood(sim.getMood() + 10);
                sim.setHunger(sim.getHunger() - 10);
                sim.resetStatus();
            }
        };
        karaoke.start();  
    }

    private void watch(Sim sim) {
        Thread watch = new Thread(){
            @Override
            public void run(){
                sim.setStatus("Watching the TV");
                changeOccupiedState();
                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * duration);
                
                while (t.isAlive()) continue;

                changeOccupiedState();
                sim.resetStatus();
                sim.setMood(sim.getMood() + 10);
                sim.setHealth(sim.getHealth() - 10);
                sim.setHunger(sim.getHunger() - 5);
            }
        };
        watch.start();
    }
}
