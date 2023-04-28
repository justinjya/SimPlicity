package src.entities.actions;

import src.entities.Sim;
import src.main.Consts;
import src.main.ui.UserInterface;
import src.world.World;

public class AddSimAction {
    public static void viewListOfSims(World world) {
        
    }

    public static void addSim(UserInterface ui, World world) {
        Sim newSim = new Sim("nitsuJ", Consts.CENTER_X, Consts.CENTER_Y);

        ui.changeIsViewingWorldState();
        world.addSim(newSim);
        world.changeIsAddingState();
    }
}
