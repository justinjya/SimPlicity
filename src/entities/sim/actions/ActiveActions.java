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

    public static void interact() {
        Sim sim = UserInterface.getCurrentSim();
        Interactables object = sim.getInteractionHandler().getInteractableObject();
        
        if (object == null) {
            return;
        }
        
        if (object.isOccupied()) {
            return;
        }

        object.interact(sim);
    }

    public static void visitAnotherSim() {
        UserInterface.changeIsViewingWorldState();
    }
}
