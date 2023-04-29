package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.entities.handlers.KeyHandler;

import src.assets.ImageLoader;
import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.main.GameTime;
import src.items.foods.BakedFood;
import src.items.Item;

public class Stove extends Interactables{
    // Types of stove
    private static String[] names = {
        "Kompor Gas",
        "Kompor Listrik"
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
    BufferedImage[] images = new BufferedImage[4]; // buat kompor gas dan listrik (pas idle sama occupied)

    // Attributes
    private int price;
    private int duration;

    // CONSTRUCTOR
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

        this.price = prices[imageIndex];
        // Load the image of the beds
        this.images = ImageLoader.loadStove();
        
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public BufferedImage getIcon() {
        return images[getImageIndex()];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void interact(Sim sim, GameTime time) {
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
                            time.startDecrementTimeRemaining((int) cookDuration*1000);
                            sim.setStatus("Cooking");

                            Thread.sleep((int) cookDuration*1000);
                            
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
