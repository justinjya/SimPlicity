package src.entities.sim.actions;

import src.entities.sim.Sim;
import src.main.ui.UserInterface;
import src.world.World;

public class AddSimAction {
    public static void viewListOfSims(World world) {
        
    }

    public static void addSim(UserInterface ui, World world) {
        Sim newSim = new Sim("nitsuJ", 3, 3);

        ui.changeIsViewingWorldState();
        world.addSim(newSim);
        world.changeIsAddingState();
    }
}
