package src.entities.handlers;

import java.awt.event.KeyEvent;

import src.entities.Sim;
import src.main.UserInterface;

public class KeyHandler {
    public static final int KEY_A = KeyEvent.VK_A;
    public static final int KEY_W = KeyEvent.VK_W;
    public static final int KEY_D = KeyEvent.VK_D;
    public static final int KEY_S = KeyEvent.VK_S;
    public static final int KEY_F = KeyEvent.VK_F;
    public static final int KEY_R = KeyEvent.VK_R;
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

    public static void keyBinds(Sim sim, UserInterface ui) {
        if (KeyHandler.isKeyPressed(KeyEvent.VK_TAB)) {
            ui.tab();
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_EQUALS)) {
            ui.debug();
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_F)) {
            sim.interact();
        }
    }
}
