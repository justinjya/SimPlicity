package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.GameTime;
import src.main.KeyHandler;
import src.items.foods.BakedFood;
import src.items.Item;

public class Stove extends Interactables{
    // Types of stove
    private static String[] names = {
        "Gas Stove",
        "Electric Stove"
    };
    private static int[] width = {
        2,
        1
    };
    private static int[] height = {
        1,
        1
    };
    private static int[] prices = {
        100,
        200
    };
    
    // Images of stove
    private BufferedImage[] icons   ;
    private BufferedImage[] images;

    // CONSTRUCTOR
    public Stove(int imageIndex) {
        super (
            names[imageIndex],
            "cook",
            imageIndex,
            2,
            2,
            width[imageIndex],
            height[imageIndex]
        );
        if (imageIndex == 1) {
            setPlayAreaX(2);
        }

        setPrice(prices[imageIndex]);

        // Load the icons and images of the stoves
        this.icons = ImageLoader.loadStovesIcons(); 
        this.images = ImageLoader.loadStoves();
    }

    public Stove(int x, int y, int imageIndex) {
        super (
            names[imageIndex],
            "cook",
            imageIndex,
            x,
            y,
            width[imageIndex],
            height[imageIndex]
        );

        setPrice(prices[imageIndex]);

        // Load the icons and images of the stoves
        this.icons = ImageLoader.loadStovesIcons(); 
        this.images = ImageLoader.loadStoves();
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getIcon() {
        return icons[getImageIndex()];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void interact(Sim sim) {
        // call ActiveAction.cook, but dont know how to insert the parameter required by cook since interact can only accept sim
        // my view: 
        // changeOccupied(sim);
        // ActiveAction.cook(bakedfood, null, sim, getTime()); but again how to insert bakedfood :v
        // changeOccupied(sim);
        Thread cooking = new Thread() {
            @Override
            public void run() {
                try {
                    // choose the food to be cooked
                    BakedFood bakedFood = null;
                    if (KeyHandler.isKeyPressed(KeyHandler.KEY_1)) {
                        bakedFood = new BakedFood(0);
                    }
                    else if(KeyHandler.isKeyPressed(KeyHandler.KEY_2)) {
                        bakedFood = new BakedFood(1);
                    }
                    else if(KeyHandler.isKeyPressed(KeyHandler.KEY_3)) {
                        bakedFood = new BakedFood(2);
                    }
                    else if(KeyHandler.isKeyPressed(KeyHandler.KEY_4)) {
                        bakedFood = new BakedFood(3);
                    }
                    else if(KeyHandler.isKeyPressed(KeyHandler.KEY_5)) {
                        bakedFood = new BakedFood(4);
                    }

                    if(bakedFood != null){
                        // check available ingredients first
                        boolean isAllIngredientAvailable = true;
                        for (String ingredient : bakedFood.getIngredients()) {
                            boolean isIngredientAvailable = false;
                            for (Item rawfood : sim.getInventory().getMapOfItems().keySet()) {
                                if (ingredient.equals(rawfood.getName())) {
                                    isIngredientAvailable = true;
                                    break;
                                }
                            }  
                            if (!isIngredientAvailable) {
                                isAllIngredientAvailable = false;
                                break;
                            }
                        }
                        if (isAllIngredientAvailable) {
                            changeOccupiedState();
                            double cookDuration = bakedFood.getHungerPoint() * 1.5;
                            GameTime.startDecrementTimeRemaining((int) cookDuration*Consts.THREAD_ONE_SECOND);
                            sim.setStatus("Cooking");

                            Thread.sleep((int) cookDuration*Consts.THREAD_ONE_SECOND);
                            
                            changeOccupiedState();
                            sim.setMood(sim.getMood() + 10);
                            // must add code to add to inventory
                            sim.getInventory().addItem(bakedFood);
                        }
                    }
                    
                    
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        cooking.start();
    } 
}
