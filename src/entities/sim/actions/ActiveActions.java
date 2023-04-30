package src.entities.sim.actions;

import src.main.Consts;
import src.main.GameTime;
import src.main.ui.UserInterface;
import src.entities.interactables.Interactables;
import src.entities.sim.Sim;

public class ActiveActions {
    public static void work (Sim sim, int duration){ // TO DO LIST: durasi validasi di dalam work atau diluar (main program)
        Thread working = new Thread() {
            @Override
            public void run() {
                int initialDurationWorked = sim.getDurationWorked();
                Thread t = GameTime.startDecrementTimeRemaining(duration * Consts.ONE_SECOND);
                
                sim.setStatus("Working");
                while (t.isAlive()) {
                    try {
                        int durationWorked = sim.getDurationWorked();
                        Thread.sleep(Consts.THREAD_ONE_SECOND);
                        
                        if (durationWorked <  initialDurationWorked + duration) {
                            sim.setDurationWorked(sim.getDurationWorked() + 1);
                        }

                        if (durationWorked == 0) continue;
                        
                        // decrease sim mood and hunger every 30 seconds
                        if (durationWorked % (Consts.ONE_SECOND * 3) == 0) { // TO - DO !!! : Change to specs
                            int simHunger = sim.getHunger();
                            int simMood = sim.getMood();
                            sim.setHunger(simHunger - 10);
                            sim.setMood(simMood - 10);
                        }

                        // increase sim salary every 4 minutes of work done
                        if (durationWorked == (Consts.ONE_SECOND * 5)) { // TO - DO !!! : Change to spec
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

    public static void exercise (Sim sim, int duration){
        Thread exercising = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Exercising");
                // count the time
                Thread t = GameTime.startDecrementTimeRemaining(duration*Consts.ONE_SECOND);
                int timeExercised = 0;
                while (t.isAlive()) {
                    try {
                        Thread.sleep(Consts.THREAD_ONE_SECOND);

                        timeExercised++;
                        if (timeExercised % 5 == 0) {
                            sim.setHealth(sim.getHealth() + 5); // increase sim's health by 5 every 20 seconds 
                            sim.setHunger(sim.getHunger() - 5); // decrease sim's hunger by 5 every 20 seconds
                            sim.setMood(sim.getMood() + 10); // increase sim's mood by 10 every 20 seconds
                        }
                    }
                    catch (InterruptedException e) {}
                }
                sim.resetStatus();
            }
        };
        exercising.start();
    }

    public void readABook(Sim sim) {
        Thread readingABook = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("ReadingABook");
                    GameTime.startDecrementTimeRemaining(10*Consts.ONE_SECOND);
                    Thread.sleep(10*Consts.THREAD_ONE_SECOND);
                    sim.setMood(sim.getMood() + 10);
                    sim.setHunger(sim.getHunger() - 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        readingABook.start();
    }

    public void karaoke(Sim sim) {
        Thread karaoke = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Karaoke");
                    GameTime.startDecrementTimeRemaining(10*Consts.ONE_SECOND);
                    Thread.sleep(10*Consts.THREAD_ONE_SECOND);
                    sim.setMood(sim.getMood() + 10);
                    sim.setHunger(sim.getHunger() - 10);
                }
                catch (InterruptedException e) {}
                sim.resetStatus();
            }
        };
        karaoke.start();
        
    }

    public void cleanTheBin(Sim sim) {
        Thread cleaningTheBin = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("CleaningTheBin");
                    GameTime.startDecrementTimeRemaining(10*Consts.ONE_SECOND);
                    Thread.sleep(10*Consts.THREAD_ONE_SECOND);
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
        Interactables object = sim.getInteractionHandler().getInteractableObject();
        
        if (object == null) {
            return;
        }
        
        if (object.isOccupied()) {
            return;
        }

        object.interact(sim);
    }

    public static void visitAnotherSim(UserInterface ui) {
        ui.changeIsViewingWorldState();
    }

    public void shower (Sim sim){
        Thread showering = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Showering");
                    // count the time
                    GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * 10);
                    Thread.sleep(Consts.THREAD_ONE_SECOND * 10);
                    sim.setHealth(sim.getHealth() + 10); // increase sim's health
                    sim.setMood(sim.getMood() + 15); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        showering.start();
    }

    public void feedingfish (Sim sim){
        Thread feedingfish = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Feeding Fish");
                    // count the time
                    GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * 5);
                    Thread.sleep(Consts.THREAD_ONE_SECOND * 5);
                    sim.setMood(sim.getMood() + 5); // increase sim's mood
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        feedingfish.start();
    }

    public void kickTheBin (Sim sim){
        Thread kickthebin = new Thread() {
            @Override
            public void run() {
                try {
                    sim.setStatus("Kicking The Bin");
                    // count the time
                    GameTime.startDecrementTimeRemaining(Consts.ONE_SECOND * 2);
                    Thread.sleep(Consts.THREAD_ONE_SECOND * 2);
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
