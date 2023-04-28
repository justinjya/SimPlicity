package src.entities.actions;

import src.entities.Sim;
import src.main.Consts;
import src.main.ui.UserInterface;

public class ActiveActions {
    public static void work(Sim sim) {
        // CODE HERE
        Thread workingThread = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Working");
                try {
                    Thread.sleep(Consts.THREAD_ONE_SECOND * 5);
                }
                catch (InterruptedException e) {}
                sim.resetStatus();
            }
        };

        workingThread.start();
    }

    public static void exercise(Sim sim) {
        // CODE HERE
        Thread workingThread = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Exercising");
                try {
                    Thread.sleep(Consts.THREAD_ONE_SECOND * 5);
                }
                catch (InterruptedException e) {}
                sim.resetStatus();
            }
        };
        
        workingThread.start();
    }

    public static void watchTV(Sim sim){
        // CODE HERE
        Television TV = Sim.
        Thread workingThread = new Thread() {
            @Override
            public void run() {
                sim.setStatus("Watch TV");
                try {
                    Thread.sleep(Consts.THREAD_ONE_SECOND * 5);
                }
                catch (InterruptedException e) {}

                sim.resetStatus();
            }
        };
        
        workingThread.start();
    }

    public static void interact(Sim sim) {
        sim.getInteractionHandler().interact();
    }

    public static void visitAnotherSim(UserInterface ui) {
        ui.changeIsViewingWorldState();
    }
}
