package src.main;

public abstract class Consts {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int TILE_SIZE = 16;
    public static final int CENTER_X = (WIDTH - TILE_SIZE) / 2;
    public static final int CENTER_Y = (HEIGHT - TILE_SIZE) / 2;
    public static final int SCALE = 4;
    public static final int SCALED_TILE = TILE_SIZE * SCALE;
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
}
