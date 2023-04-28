package src.entities.sim.actions;

import src.main.Consts;
import src.main.GameTime;
import src.entities.interactables.Stove;
import src.entities.sim.Sim;
import src.items.foods.BakedFood;

public class ActiveActions {
    public void work (Sim sim, GameTime time, int duration){ // TO DO LIST: durasi validasi di dalam work atau diluar (main program)
        Thread working = new Thread() {
            @Override
            public void run() {
                // if it has not ever changed profession or it's been one day since it changed its profession
                if (!sim.getHasChangedProfession() || 
                ((((time.getDay() - 1) * 720 + 720 - time.getTimeRemaining())) 
                - ((sim.getChangeProfessionTime().getDay() - 1) * 720 + 720 - sim.getChangeProfessionTime().getTimeRemaining())) >= 720) { 
                    try {
                        sim.setStatus("Working");
                        time.startDecrementTimeRemaining(duration*1000);
                        Thread.sleep(duration*1000);

                        // sim's duration of work + duration
                        sim.setDurationOfWork(sim.getDurationOfWork() + duration); // for subtracting mood and hunger
                        sim.setDurationOfWork1(sim.getDurationOfWork1() + duration); // for adding salary

                        // check if the duration work is enough to be subtracted for mood and hunger
                        int moodAndHungerSubtractedAmount = 10 * sim.getDurationOfWork() / 30; // 30 seconds for every 10 mood and hunger decreased
                        sim.setMood(sim.getMood() -  moodAndHungerSubtractedAmount);
                        sim.setHunger(sim.getHunger() - moodAndHungerSubtractedAmount);
                        sim.setDurationOfWork(sim.getDurationOfWork() % 30);

                        // check if the mood and hunger is negative after subtraction
                        if (sim.getMood() < 0) {
                            sim.setMood(0);
                        }

                        if (sim.getHunger() < 0) {
                            sim.setHunger(0);
                        }
                        
                        // check if the duration work is enough to be added for money
                        // add salary according to profession
                        int salaryMultiplier = sim.getDurationOfWork1() / 240; // 4 minutes or 240 seconds for every salary received
                        if (sim.getProfessionId() == 1) {
                            sim.setMoney(sim.getMoney() + 15 * salaryMultiplier);
                        }
                        else if (sim.getProfessionId() == 2) {
                            sim.setMoney(sim.getMoney() + 30 * salaryMultiplier);
                        }
                        else if (sim.getProfessionId() == 3) {
                            sim.setMoney(sim.getMoney() + 35 * salaryMultiplier);
                        }
                        else if (sim.getProfessionId() == 4) {
                            sim.setMoney(sim.getMoney() + 45 * salaryMultiplier);
                        }
                        else if (sim.getProfessionId() == 5) {
                            sim.setMoney(sim.getMoney() + 50 * salaryMultiplier);
                        }
                        sim.setDurationOfWork1(sim.getDurationOfWork1() % 240);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                }
            }
        };
        working.start();
    }

    

    public void exercise (Sim sim, GameTime time, int duration){
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

    public void cook (BakedFood bakedfood, Stove stove, Sim sim, GameTime time){
        Thread cooking = new Thread() {
            @Override
            public void run() {
                try {
                    // check inventory first
                    // nunggu ucha
                    // pseudocode: 
                    // isAllIngredientAvailable = true
                    // for (String ingredient : bakedfood.getIngredient()) {
                    //      boolean isIngredientAvailable = false;
                    //      for (String rawfood : Inventory.getRawFood()) {
                    //          if (ingredient.equals(rawfood)) {
                    //              isIngredientAvailable = true;
                    //              break;
                    //          }
                    //      }  
                    //      if (!isIngredientAvailable) {
                    //          isAllIngredientAvailable = false;
                    //          break;
                    //      }
                    // }
                    // if (isAllIngredientAvailable) then do the code below
                    stove.changeOccupied(sim);
                    double cookDuration = bakedfood.getHungerPoint() * 1.5;
                    time.startDecrementTimeRemaining((int) cookDuration*1000);
                    sim.setStatus("Cooking");

                    Thread.sleep((int) cookDuration*1000);
                    
                    stove.changeOccupied(sim);
                    sim.setMood(sim.getMood() + 10);
                    // must add code to add to inventory
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        cooking.start();
    }

    // public void takeALeak(Toilet toilet, Sim sim, GameTime time) {
    //     try {
    //         toilet.changeOccupied(sim);
    //         time.startDecrementTimeRemaining(10*1000);
    //         sim.setStatus("TakingALeak");
    //         Thread.sleep(10*1000);
    //         toilet.changeOccupied(sim);
    //         sim.setHunger(sim.getHunger() - 20);
    //         sim.setMood(sim.getMood() + 10);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void readABook(Sim sim, GameTime time) {
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

    public void karaoke(Sim sim, GameTime time) {
        try {
            sim.setStatus("Karaoke");
            time.startDecrementTimeRemaining(10*1000);
            Thread.sleep(10*1000);
            sim.setMood(sim.getMood() + 10);
            sim.setHunger(sim.getHunger() - 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanTheBin(Sim sim, GameTime time) {
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

}
