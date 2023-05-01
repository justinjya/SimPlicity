package src.main;

import java.awt.Color;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.panels.CreateSimPanel;
import src.main.panels.GamePanel;
import src.main.ui.UserInterface;
import src.world.World;

public class GameLoader {
    public static String simName = CreateSimPanel.simName;
    public static String roomName = CreateSimPanel.roomName;
    public static int selectedColor = CreateSimPanel.selectedColor;

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
        world.changeIsAddingState();
        
        // Actually start the game by changing the state into adding a house
        UserInterface ui = GamePanel.ui = new UserInterface(GamePanel.world);
        ui.changeIsViewingWorldState();
    }

    public static void addSim() {
        UserInterface ui = GamePanel.ui;
        World world = GamePanel.world;
        String simName = CreateSimPanel.textFields[0];
        int selectedColor = CreateSimPanel.selectedColor;
        
        // Create the sim
        Color shirtColor = ImageLoader.setColor(selectedColor);
        Sim newSim = new Sim(simName, shirtColor);
        
        // Add the sim to the world and change the state of game into adding a house
        world.addSim(newSim);
        world.changeIsAddingState();
        ui.changeIsViewingWorldState();
    }

    public static void loadGame() {
        // LOAD GAME HERE
    }
}
