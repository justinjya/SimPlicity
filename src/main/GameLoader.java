package src.main;

import java.awt.Color;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.panels.CreateSimPanel;
import src.main.panels.GamePanel;
import src.world.World;

public class GameLoader {
    public static String simName = CreateSimPanel.simName;
    public static String roomName = CreateSimPanel.roomName;
    public static int selectedColor = CreateSimPanel.selectedColor;

    // ONLY FOE DEBUGGING
    public static void startNewGameDebug() {
        GameTime.init(1, Consts.ONE_MINUTE * 12);
        World world = GamePanel.world = new World();
        GamePanel.gameState = "Playing";
        
        // Create the new sim
        Sim newSim = new Sim("Justin", Color.BLUE);
        Sim newSim2 = new Sim("nitsuj", Color.MAGENTA);
        Sim newSim3 = new Sim("Abi", Color.ORANGE);
        newSim.setDurationWorked(720);
        
        // Add the new sim to the world
        world.addSim(newSim);
        world.addSim(newSim2);
        world.addSim(newSim3);
        
        // Actually start the game by changing the state into adding a house
        UserInterface.initDebug(world);
    }

    public static void startNewGame() {
        GameTime.init(1, Consts.ONE_MINUTE * 12);
        World world = GamePanel.world = new World();
        
        String simName = CreateSimPanel.simName;
        int selectedColor = CreateSimPanel.selectedColor;
        
        // Create the new sim
        Color shirtColor = ImageLoader.setColor(selectedColor);
        Sim newSim = new Sim(simName, shirtColor);
        
        // Add the new sim to the world
        world.addSim(newSim);
        
        // Actually start the game by changing the state into adding a house
        UserInterface.init(world);
    }

    public static void addSim() {
        World world = GamePanel.world;
        String simName = CreateSimPanel.textFields[0];
        int selectedColor = CreateSimPanel.selectedColor;
        
        // Create the sim
        Color shirtColor = ImageLoader.setColor(selectedColor);
        Sim newSim = new Sim(simName, shirtColor);
        
        // Add the sim to the world and change the state of game into adding a house
        world.addSim(newSim);
        world.changeIsAddingState();
        UserInterface.viewWorld();
    }

    public static void loadGame() {

        // LOAD GAME HERE
        
    }
}
