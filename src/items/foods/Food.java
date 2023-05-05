package src.items.foods;

import java.awt.image.BufferedImage;

import src.entities.handlers.InteractionHandler;
import src.entities.interactables.Interactables;
import src.entities.interactables.TableAndChair;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;

public abstract class Food {
    private String name;
    private int hungerPoint;
    private int price;
    private int imageIndex;

    public Food(String name, int hungerPoint, int price, int imageIndex) {
        this.name = name;
        this.hungerPoint = hungerPoint;
        this.price = price;
        this.imageIndex = imageIndex;
    }

    public String getName() {
        return name;
    }

    public int getHungerPoint() {
        return hungerPoint;
    }

    public int getPrice() {
        return price;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void eat(Sim sim) {
        InteractionHandler simInteract = sim.getInteractionHandler();
        Interactables object = simInteract.getInteractableObject();

        if (!(object instanceof TableAndChair)) return;

        TableAndChair tableAndChair = (TableAndChair) object;

        Thread eating = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Eating");
                tableAndChair.changeOccupiedState(sim);
                Thread t = GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * 30);

                while (t.isAlive()) continue;
                
                sim.resetStatus();
                sim.setHunger(sim.getHunger() + hungerPoint);
                tableAndChair.changeOccupiedState(sim);
                tableAndChair.resetImages();
            }
        };
        eating.start();
    }

    public abstract BufferedImage getIcon();
}
