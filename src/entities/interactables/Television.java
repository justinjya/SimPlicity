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
    private int watchDuration = 40;
    private int karaokeDuration = 30;

    // Images of the television
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
        setDuration(watchDuration);

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
        setDuration(watchDuration);

        this.icon = ImageLoader.loadTelevisionIcon();
        this.images = ImageLoader.loadTelevision();
    }

    @Override
    public void changeOccupiedState() {
        this.occupied = !this.occupied;
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
                images[2] = ImageLoader.changeSimColor(images[2], sim);
                images[3] = ImageLoader.changeSimColor(images[3], sim);

                sim.setStatus("Karaoke-ing");
                changeOccupiedState();
                
                Thread t = GameTime.startDecrementTimeRemaining(karaokeDuration * Consts.ONE_SECOND);
                
                while (t.isAlive()) {
                    try {
                        setImageIndex(2);
                        Thread.sleep(Consts.THREAD_ONE_SECOND);
                        setImageIndex(3);
                        Thread.sleep(Consts.THREAD_ONE_SECOND);
                    }
                    catch (InterruptedException ie) {}
                }
                setImageIndex(0);

                changeOccupiedState();
                sim.setMood(sim.getMood() + 10);
                sim.setHunger(sim.getHunger() - 10);
                sim.resetStatus();

                // Reset the images
                images = ImageLoader.loadTelevision();
            }
        };
        karaoke.start();  
    }

    private void watch(Sim sim) {
        Thread watch = new Thread(){
            @Override
            public void run(){
                sim.setStatus("Watching The TV");
                changeOccupiedState();
                setImageIndex(1);
                images[getImageIndex()] = ImageLoader.changeSimColor(images[getImageIndex()], sim);

                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * watchDuration);
                
                while (t.isAlive()) continue;

                setImageIndex(0);
                changeOccupiedState();
                sim.resetStatus();
                sim.setMood(sim.getMood() + 10);
                sim.setHealth(sim.getHealth() - 10);
                sim.setHunger(sim.getHunger() - 5);

                // Reset the images
                images = ImageLoader.loadTelevision();
            }
        };
        watch.start();
    }
}
