package src.entities.sim.actions;

import src.entities.interactables.Interactables;
import src.entities.sim.Sim;
import src.main.Consts;
import src.main.ui.UserInterface;
import src.world.World;

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
}
