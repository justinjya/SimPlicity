package src.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.entities.handlers.KeyHandler;
import src.entities.sim.Sim;
import src.main.ui.ActiveActionsUserInterface;
import src.main.ui.UserInterface;
import src.assets.ImageLoader;
import src.world.Room;
import src.world.World;

// ini notes aja
// x + 12 pas dikiri 6x6 grid
// y - 38 pas diatas 6x6 grid
// y + 283 pas dibawah 6x6 grid

public class GamePanel extends JPanel implements Runnable {
    public static String gameState;
    public static GameTime time;

    private World world;
    private Sim sim;
    private UserInterface ui;

    private JTextField inputField;

    // testing sim color
    private float hue = 0.0f;
    private float sat = 1.0f;
    private float bri = 0.92f;

    public GamePanel() {
        setPreferredSize(new Dimension(Consts.WIDTH, Consts.HEIGHT));
        setBackground(new Color(44, 39, 35));
        setDoubleBuffered(true);

        gameState = "Starting a new game";
        
        // Create sim
        sim = new Sim("Justin", 3, 3);
        
        // create a new world
        world = new World(sim);
        
        // // Create user interface
        ui = new UserInterface(world, sim);

        // Create game time
        GameTime.init(1, Consts.ONE_MINUTE * 12);

        // Create a KeyAdapter and add it as a key listener to the panel
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                KeyHandler.keyPressed(e.getKeyCode());
                
                KeyHandler.keyBinds(ui.getCurrentSim(), world, ui);
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                KeyHandler.keyReleased(e.getKeyCode());
            }
        };
        addKeyListener(keyAdapter);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int fps = 0;
        double time = 0;
        final double TARGET_FPS = 60.0;
        final double OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        double accumulator = 0.0;
        while (true) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1000000000.0;
            lastTime = now;
            accumulator += deltaTime;
            while (accumulator >= OPTIMAL_TIME / 1000000000.0) {
                update();
                accumulator -= OPTIMAL_TIME / 1000000000.0;
            }
            revalidate();
            repaint();
            fps++;
            time += deltaTime;
            if (time > 1.0) {
                System.out.println("Game Window - FPS: " + fps);
                fps = 0;
                time = 0;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // GETTERS
    public static boolean isCurrentState(String state) {
        return gameState.equals(state); 
    }

    private void update() {
        if (isCurrentState("Starting a new game") || isCurrentState("Playing")) {
            ui.update();

            if (!ui.isViewingWorld()) {
                Sim currentSim = ui.getCurrentSim();
                Room currentRoom = currentSim.getCurrentRoom();
                
                currentSim.update();
                currentRoom.update();
            }
            else {
                world.update(ui);
            }
        }
        else if (isCurrentState("Viewing active actions")) {
            ActiveActionsUserInterface.update(sim, ui);
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // ONLY FOR DEBUGGING
        // ui.drawMockup(g2);

        if (isCurrentState("Starting a new game") || isCurrentState("Playing")) {
            if (!ui.isViewingWorld()) {
                try {
                    Sim currentSim = ui.getCurrentSim();
                    Room currentRoom = currentSim.getCurrentRoom();
                    currentRoom.draw(g2);
                }
                catch (NullPointerException e) { }
            }
            else {
                world.draw(g2);
            }
    
            ui.draw(g2);
        }
        else if (isCurrentState("Viewing active actions")) {
            ActiveActionsUserInterface.draw(this, g2);
        }

        // testing sim color
        // testingSimColor(g2);
       
        // To free resources
        g2.dispose();
    }

    private void testingSimColor(Graphics2D g) {
        Font font;
        g.setColor(Color.WHITE);

        font = new Font("Arial", Font.PLAIN, 15);

        g.setFont(font);
        g.drawString("hue: " + hue, 10, 30);
        g.drawString("sat: " + sat, 10, 50);
        g.drawString("bri: " + bri, 10, 70);

        g.drawImage(ImageLoader.testSimColor(hue), Consts.CENTER_X - (Consts.SCALED_TILE * 2), Consts.CENTER_Y - (Consts.SCALED_TILE * 2), Consts.SCALED_TILE * 4, Consts.SCALED_TILE * 4, null);

    }
}