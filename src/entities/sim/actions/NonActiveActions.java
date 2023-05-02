package src.entities.sim.actions;

import src.entities.sim.Sim;
import src.main.ui.UserInterface;
import src.world.House;
import src.world.Room;

public class NonActiveActions {
    public static void showInventory() {
        UserInterface.inventory();
    }

    public static void editRoom(Room room) {
        Sim currentSim = UserInterface.getCurrentSim();
        House currentHouse = currentSim.getCurrentHouse();
        Sim currentHouseOwner = currentHouse.getOwner();

        if (currentSim.getName().equals(currentHouseOwner.getName())) room.selectObject();
    }
}
