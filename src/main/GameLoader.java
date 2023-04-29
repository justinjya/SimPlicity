package src.main;

import src.entities.Sim;
import src.main.ui.UserInterface;
import src.world.World;

public class GameLoader {
    public static void newGame(GamePanel gp) {
        gp.gameState = "Starting a new game";
        
        // Create game time
        gp.time = new GameTime(1, 720, 720);

        // Create sim
        gp.sim = new Sim("Justin", Consts.CENTER_X + 80, Consts.CENTER_Y);

        // create a new world
        gp.world = new World(gp.sim, gp, gp.time);

        // // Create user interface
        gp.ui = new UserInterface(gp.world, gp.sim, gp.time);
    }
}
