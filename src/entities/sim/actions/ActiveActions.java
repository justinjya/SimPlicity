package src.entities.sim.actions;

import src.main.Consts;
import src.main.GameTime;
import src.main.ui.UserInterface;
import src.world.World;
import src.entities.interactables.Interactables;
import src.entities.sim.Sim;

public class ActiveActions {
    public static void work (Sim sim, GameTime time, int duration){ // TO DO LIST: durasi validasi di dalam work atau diluar (main program)
        Thread working = new Thread() {
            @Override
            public void run() {
                int initialDurationWorked = sim.getDurationWorked();
                Thread t = time.startDecrementTimeRemaining(duration);
                
                sim.setStatus("Working");
                while (t.isAlive()) {
                    try {
                        int durationWorked = sim.getDurationWorked();
                        Thread.sleep(Consts.THREAD_ONE_SECOND);
                        
                        if (durationWorked <  initialDurationWorked + duration) {
                            sim.setDurationWorked(sim.getDurationWorked() + 1);
                        }

                        if (durationWorked == 0) continue;
                        
                        if (durationWorked % (Consts.ONE_SECOND * 3) == 0) {
                            int simHunger = sim.getHunger();
                            int simMood = sim.getMood();
                            sim.setHunger(simHunger - 10);
                            sim.setMood(simMood - 10);
                        }

                        if (durationWorked == (Consts.ONE_SECOND * 5)) {
                            int simMoney = sim.getMoney();
                            int salary = sim.getProfession().getSalary();
                            sim.setMoney(simMoney + salary);
                            sim.setDurationWorked(0);
                        }
                    } catch (InterruptedException e) {}
                }
                sim.resetStatus();
            }
        };
        working.start();
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
