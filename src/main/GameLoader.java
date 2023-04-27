package src.main;

import src.entities.*;
import src.entities.handlers.KeyHandler;
import src.world.Room;
import src.world.World;

public class GameLoader {
    private static GameTime time;
    private static World world;

    public static World loadWorld() {
        time = new GameTime(1, 720, 720);
        world = new World(time);
        return world;
    }
}
