package src.entities.handlers;

public class KeyHandler {
    public static final int KEY_A = 65;
    public static final int KEY_W = 87;
    public static final int KEY_D = 68;
    public static final int KEY_S = 83;
    public static final int KEY_F = 70;
    public static final int KEY_ENTER = 10;
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
}
