package src.main;

import java.awt.event.KeyEvent;

import src.entities.sim.Inventory;
import src.entities.sim.Sim;
import src.entities.sim.actions.ActiveActions;
import src.entities.sim.actions.NonActiveActions;
import src.main.ui.UserInterface;
import src.world.Room;
import src.world.World;

public class KeyHandler {
    public static final int KEY_A = KeyEvent.VK_A;
    public static final int KEY_W = KeyEvent.VK_W;
    public static final int KEY_D = KeyEvent.VK_D;
    public static final int KEY_S = KeyEvent.VK_S;
    public static final int KEY_F = KeyEvent.VK_F;
    public static final int KEY_I = KeyEvent.VK_I;
    public static final int KEY_R = KeyEvent.VK_R;
    public static final int KEY_1 = KeyEvent.VK_1;
    public static final int KEY_2 = KeyEvent.VK_2;
    public static final int KEY_3 = KeyEvent.VK_3;
    public static final int KEY_4 = KeyEvent.VK_4;
    public static final int KEY_5 = KeyEvent.VK_5;
    public static final int KEY_6 = KeyEvent.VK_6;
    public static final int KEY_7 = KeyEvent.VK_7;
    public static final int KEY_8 = KeyEvent.VK_8;
    public static final int KEY_SPACE = KeyEvent.VK_SPACE;
    public static final int KEY_ENTER = KeyEvent.VK_ENTER;
    public static final int KEY_TAB = KeyEvent.VK_TAB;
    public static final int KEY_EQUALS = KeyEvent.VK_EQUALS;
    public static final int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
    private static boolean[] keys = new boolean[256];
    private static boolean[] prevKeys = new boolean[256];
    
    public static void keyPressed(int keyCode) {
        keys[keyCode] = true;
    }

    public static void keyReleased(int keyCode) {
        keys[keyCode] = false;
        prevKeys[keyCode] = false;
    }

    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    public static boolean isKeyPressed(int keyCode) {
        boolean pressed = keys[keyCode] && !prevKeys[keyCode];
        prevKeys[keyCode] = keys[keyCode];
        return pressed;
    }

    // public static void keyBinds(Sim sim, UserInterface ui) {
    public static void keyBinds(UserInterface ui) {
        World world = ui.getWorld();
        Sim currentSim = ui.getCurrentSim();
        Inventory currentSimInventory = currentSim.getInventory();
        if (!ui.isViewingWorld() && !currentSimInventory.isOpen() && KeyHandler.isKeyPressed(KeyHandler.KEY_TAB)) {
            ui.tab();
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_EQUALS)) {
            ui.debug();
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_F)) {
            ActiveActions.interact(ui);
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_I)) {
            NonActiveActions.showInventory(ui);
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
            ui.pause();
        }

        // testing adding and switching sim
        try {
            Room currentRoom = currentSim.getCurrentRoom();
            boolean simControllable = !ui.isViewingWorld() && !ui.isTabbed() && !currentRoom.isEditingRoom();
            if (KeyHandler.isKeyPressed(KeyEvent.VK_M) && simControllable) {
                if (ui.getCurrentSim() == world.getSim(1)) {
                    ui.setCurrentSim(world.getSim(0));
                }
                else {
                    ui.setCurrentSim(world.getSim(1));
                }
            }
        }
        catch (Exception e) {
            System.out.println("No sim found!");
        }
    }

    public static String receiveStringInput(KeyEvent e, String input) {
        int keyCode = e.getKeyCode();

        // Check if the key is a letter or a number
        if ((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) ||
            (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9)) {
            // Append the character to the input string
            char c = e.getKeyChar();
            input += c;
        }
        // Check if the key is the backspace key
        else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            // Remove the last character from the input string
            if (input.length() > 0) {
                input = input.substring(0, input.length() - 1);
            }
        }
        return input;
    }
}
