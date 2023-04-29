package src.entities.sim.actions;

import src.main.Consts;
import src.main.GameTime;
import src.main.ui.UserInterface;
import src.world.World;
import src.entities.interactables.Interactables;
import src.entities.interactables.Stove;
import src.entities.sim.Sim;
import src.items.foods.BakedFood;
import src.entities.sim.Inventory;
import src.items.Item;

public class ActiveActions {
    public static void work (Sim sim, GameTime time, int duration){ // TO DO LIST: durasi validasi di dalam work atau diluar (main program)
        // Thread working = new Thread() {
        //     @Override
        //     public void run() {
        //         // if it has not ever changed profession or it's been one day since it changed its profession
        //         if (!sim.getHasChangedProfession() || 
        //         ((((time.getDay() - 1) * 720 + 720 - time.getTimeRemaining())) 
        //         - ((sim.getChangeProfessionTime().getDay() - 1) * 720 + 720 - sim.getChangeProfessionTime().getTimeRemaining())) >= 720) { 
        //             try {
        //                 sim.setStatus("Working");
        //                 time.startDecrementTimeRemaining(duration*1000);
        //                 Thread.sleep(duration*1000);

        //                 // sim's duration of work + duration
        //                 sim.setDurationOfWork(sim.getDurationOfWork() + duration); // for subtracting mood and hunger
        //                 sim.setDurationOfWork1(sim.getDurationOfWork1() + duration); // for adding salary

        //                 // check if the duration work is enough to be subtracted for mood and hunger
        //                 int moodAndHungerSubtractedAmount = 10 * sim.getDurationOfWork() / 30; // 30 seconds for every 10 mood and hunger decreased
        //                 sim.setMood(sim.getMood() -  moodAndHungerSubtractedAmount);
        //                 sim.setHunger(sim.getHunger() - moodAndHungerSubtractedAmount);
        //                 sim.setDurationOfWork(sim.getDurationOfWork() % 30);

        //                 // check if the mood and hunger is negative after subtraction
        //                 if (sim.getMood() < 0) {
        //                     sim.setMood(0);
        //                 }

        //                 if (sim.getHunger() < 0) {
        //                     sim.setHunger(0);
        //                 }
                        
        //                 // check if the duration work is enough to be added for money
        //                 // add salary according to profession
        //                 int salaryMultiplier = sim.getDurationOfWork1() / 240; // 4 minutes or 240 seconds for every salary received
        //                 if (sim.getProfession().getName().equals("Clone")) {
        //                     sim.setMoney(sim.getMoney() + 15 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Chef")) {
        //                     sim.setMoney(sim.getMoney() + 30 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Police")) {
        //                     sim.setMoney(sim.getMoney() + 35 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Programmer")) {
        //                     sim.setMoney(sim.getMoney() + 45 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Doctor")) {
        //                     sim.setMoney(sim.getMoney() + 50 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Barista")) {
        //                     sim.setMoney(sim.getMoney() + 20 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Model")) {
        //                     sim.setMoney(sim.getMoney() + 45 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Dentist")) {
        //                     sim.setMoney(sim.getMoney() + 40 * salaryMultiplier);
        //                 }
        //                 else if (sim.getProfession().getName().equals("Security")) {
        //                     sim.setMoney(sim.getMoney() + 15 * salaryMultiplier);
        //                 }
        //                 sim.setDurationOfWork1(sim.getDurationOfWork1() % 240);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
                    
        //         }
        //     }
        // };
        // working.start();
    }

    public static void exercise (Sim sim, GameTime time, int duration){
        Thread exercising = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Exercising");
                    // count the time
                    time.startDecrementTimeRemaining(duration*1000);
                    Thread.sleep(duration*1000);

                    int multiplier20Secs = duration / 20; 
                    sim.setHealth(sim.getHealth() + 5 * multiplier20Secs); // increase sim's health by 5 every 20 seconds 
                    sim.setHunger(sim.getHunger() - 5 * multiplier20Secs); // decrease sim's hunger by 5 every 20 seconds
                    sim.setMood(sim.getMood() + 10 * multiplier20Secs); // increase sim's mood by 10 every 20 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        exercising.start();
    }

    public void cook (Inventory inventory, BakedFood bakedfood, Stove stove, Sim sim, GameTime time){
        Thread cooking = new Thread() {
            @Override
            public void run() {
                try {
                    // check available ingredients first
                    boolean isAllIngredientAvailable = true;
                    for (String ingredient : bakedfood.getIngredients()) {
                        boolean isIngredientAvailable = false;
                        for (Item rawfood : inventory.getMapOfItems().keySet()) {
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
                        stove.changeOccupiedState();
                        double cookDuration = bakedfood.getHungerPoint() * 1.5;
                        time.startDecrementTimeRemaining((int) cookDuration*1000);
                        sim.setStatus("Cooking");

                        Thread.sleep((int) cookDuration*1000);
                        
                        stove.changeOccupiedState();
                        sim.setMood(sim.getMood() + 10);
                        // must add code to add to inventory
                        inventory.addItem(bakedfood);
                    }
                    
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        cooking.start();
    }

    public static void takeALeak(Sim sim, GameTime time) {
        Thread takingALeak = new Thread() {
            @Override
            public void run() {
                try {
            
                    time.startDecrementTimeRemaining(10*1000);
                    sim.setStatus("TakingALeak");
                    Thread.sleep(10*1000);
                    
                    sim.setHunger(sim.getHunger() - 20);
                    sim.setMood(sim.getMood() + 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        };
        takingALeak.start();
        
    }

    public void readABook(Sim sim, GameTime time) {
        Thread readingABook = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("ReadingABook");
                    time.startDecrementTimeRemaining(10*1000);
                    Thread.sleep(10*1000);
                    sim.setMood(sim.getMood() + 10);
                    sim.setHunger(sim.getHunger() - 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        readingABook.start();
    }

    public void karaoke(Sim sim, GameTime time) {
        Thread karaoke = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Karaoke");
                    time.startDecrementTimeRemaining(10*1000);
                    Thread.sleep(10*1000);
                    sim.setMood(sim.getMood() + 10);
                    sim.setHunger(sim.getHunger() - 10);
                }
                catch (InterruptedException e) {}
                sim.resetStatus();
            }
        };
        karaoke.start();
        
    }

    public void cleanTheBin(Sim sim, GameTime time) {
        Thread cleaningTheBin = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("CleaningTheBin");
                    time.startDecrementTimeRemaining(10*1000);
                    Thread.sleep(10*1000);
                    sim.setMood(sim.getMood() + 10);
                    sim.setHealth(sim.getHealth() + 10);
                    sim.setHunger(sim.getHunger() - 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        cleaningTheBin.start();
    }

    public static void interact(UserInterface ui) {
        Sim sim = ui.getCurrentSim();
        World world = ui.getWorld();
        Interactables object = sim.getInteractionHandler().getInteractableObject();
        
        if (object == null) {
            return;
        }
        
        if (object.isOccupied()) {
            return;
        }

        object.interact(sim, world.getTime());
    }

    public static void visitAnotherSim(UserInterface ui) {
        ui.changeIsViewingWorldState();
    }

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

    public void feedingfish (Sim sim, GameTime time){
        Thread feedingfish = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Feeding Fish");
                    // count the time
                    time.startDecrementTimeRemaining(5000);
                    Thread.sleep(5000);
                    sim.setMood(sim.getMood() + 5); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        feedingfish.start();
    }

    public void kickTheBin (Sim sim, GameTime time){
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
