package src.items.foods;

import java.awt.image.BufferedImage;

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
        Thread eating = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Eating");
                    GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * 5);

                    Thread.sleep(Consts.THREAD_ONE_SECOND * 5);
                    
                    sim.resetStatus();
                    sim.setHunger(sim.getHunger() + hungerPoint);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        eating.start();
    }

    public abstract BufferedImage getIcon();
}
