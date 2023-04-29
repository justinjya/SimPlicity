package src.entities.sim.actions;

import src.main.ui.UserInterface;
import src.world.Room;

public class NonActiveActions {
    public static void showInventory(UserInterface ui) {
        ui.inventory();
    }

    public static void editRoom(Room room) {
        room.selectObject();
    }
}
