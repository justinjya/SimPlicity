package src.main;

import java.awt.Color;

import src.assets.ImageLoader;
import src.entities.interactables.Door;
import src.entities.sim.Sim;
import src.main.panels.CreateSimPanel;
import src.main.panels.GamePanel;
import src.world.House;
import src.world.Room;
import src.world.World;

public class GameLoader {
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
        Room newRoom = new Room("First Room");
        newRoom.getListOfObjects().add(new Door(null));
        newRoom.getListOfObjects().get(0).setInteraction("view active actions");

        House newHouse = new House(18, 16, world, world.getListOfSim().get(1), newRoom);
        
        world.getListOfHouse().add(newHouse);
        newSim2.setCurrentHouse(newHouse);
        newSim2.setCurrentRoom(newRoom);
        
        world.addSim(newSim3);
        newRoom = new Room("First Room");
        newRoom.getListOfObjects().add(new Door(null));
        newRoom.getListOfObjects().get(0).setInteraction("view active actions");

        newHouse = new House(20, 16, world, world.getListOfSim().get(2), newRoom);
        
        world.getListOfHouse().add(newHouse);
        newSim3.setCurrentHouse(newHouse);
        newSim3.setCurrentRoom(newRoom);

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

        String simName = CreateSimPanel.simName;
        int selectedColor = CreateSimPanel.selectedColor;
        
        // Create the sim
        Color shirtColor = ImageLoader.setColor(selectedColor);
        Sim newSim = new Sim(simName, shirtColor);
        CreateSimPanel.currentSim = UserInterface.getCurrentSim();
        UserInterface.setCurrentSim(newSim);
        
        // Add the sim to the world and change the state of game into adding a house
        world.addSim(newSim);
        world.changeIsAddingState();
        UserInterface.viewWorld();
        UserInterface.viewListOfSims();
    }

    public static void loadGame() {

        // LOAD GAME HERE
        
    }
}
