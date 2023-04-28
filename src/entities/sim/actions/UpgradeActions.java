package src.entities.sim.actions;
import src.entities.sim.Sim;
import src.items.foods.RawFood;
import src.main.GameTime;

public class UpgradeActions {
    public static void buyRawFood(RawFood rawfood, Sim sim, GameTime time) {
        Thread buying = new Thread(){
            @Override
            public void run() {
                int upperBound = 30;
                int lowerBound = 1;
                int deliveryTime = (int) Math.random()*(upperBound-lowerBound) + lowerBound;
                time.startDecrementTimeRemaining(deliveryTime);
                if(sim.getMoney() > rawfood.getPrice()){
                    // must put the code to add to inventory
                    sim.setMoney(sim.getMoney() - rawfood.getPrice());
                }
                

            }
        };
        buying.start();
    }
}
