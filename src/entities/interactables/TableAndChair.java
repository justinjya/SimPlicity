package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;
import src.main.KeyHandler;
import src.main.UserInterface;
import src.main.menus.InteractMenu;

public class TableAndChair extends Interactables {
    private BufferedImage icon = ImageLoader.loadTableAndChairIcon();
    private BufferedImage[] images = ImageLoader.loadTableAndChair();

    private int price = 50;
    private int duration = 10; // TO BE DETERMINED

    public TableAndChair() {
        super (
            "Table and Chair",
            "eat or read a book",
            0,
            1,
            2,
            1,
            3
        );

        setPrice(price);

        getBounds().setSize((Consts.SCALED_TILE * 3) - 48, (Consts.SCALED_TILE * 3) - 48);
        updateBounds();
    }

    public TableAndChair(int x, int y) {
        super (
            "Table and Chair",
            "eat or read a book",
            0,
            1,
            x,
            y,
            3
        );

        setPrice(price);

        getBounds().setSize(16, 16);
        updateBounds();
    }

    @Override
    public void updateBounds() {
        getBounds().setLocation(getX() + 8, getY() + 12);
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
    public void interact(Sim sim) {
        Thread choosingInteractionThread = new Thread() {
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
                else if (InteractMenu.slotSelected == 0) chooseFood(sim);
                else if (InteractMenu.slotSelected == 1) readABook(sim);
                InteractMenu.slotSelected = 0;
            }
        };
        choosingInteractionThread.start();
    }

    // TO - DO!!! : Add eat from inventory
    private void chooseFood(Sim sim) {
        Inventory simInventory = sim.getInventory();

        simInventory.changeIsOpen();
        simInventory.switchCategory();
    }
    
    private void readABook(Sim sim) {
        Thread readingABook = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Reading a Book");
                changeOccupiedState();
                Thread t = GameTime.startDecrementTimeRemaining(duration*Consts.ONE_SECOND);
                
                while (t.isAlive()) continue;

                changeOccupiedState();
                sim.resetStatus();
                sim.setMood(sim.getMood() + 10);
                sim.setHunger(sim.getHunger() - 10);
            }
        };
        readingABook.start();
    }
}
