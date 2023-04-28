package src.entities.actions;

import src.entities.Sim;
import src.main.UserInterface;

public class ActiveActions {
    public static void work(Sim sim) {
        // CODE HERE
    }

    public static void exercise(Sim sim) {
        // CODE HERE
    }

    public static void interact(Sim sim) {
        sim.getInteractionHandler().interact();
    }

    public static void visitAnotherSim(UserInterface ui) {
        ui.changeIsViewingWorldState();
    }
}
