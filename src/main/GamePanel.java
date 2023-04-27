package src.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.assets.ImageLoader;
import src.entities.*;
import src.entities.handlers.KeyHandler;
import src.world.Room;
import src.world.World;

// ini notes aja
// x + 12 pas dikiri 6x6 grid
// y - 38 pas diatas 6x6 grid
// y + 283 pas dibawah 6x6 grid

public class GamePanel extends JPanel implements Runnable {
    private GameTime time;
    private World world;
    private Sim currentSim;
    private Sim sim, sim2;
    private Room room;
    private UserInterface ui;

    // testing sim color
    private float hue = 0.0f;
    private float sat = 1.0f;
    private float bri = 0.92f;

    public GamePanel() {
        setPreferredSize(new Dimension(Consts.WIDTH, Consts.HEIGHT));
        setBackground(new Color(44, 39, 35));
        
        // Create game time
        time = new GameTime(1, 720, 720);

        // create a new world
        world = new World(time);

        // Create room
        room = new Room("First Room", time);
        
        // Create sim
        sim = new Sim("Justin", Consts.CENTER_X + 80, Consts.CENTER_Y, room, null);

        sim2 = new Sim("Nitsuj", Consts.CENTER_X, Consts.CENTER_Y, room, null);
        sim2.changeIsBusyState();

        currentSim = sim;
        
        // Create user interface
        ui = new UserInterface(currentSim, time);

        // Create a KeyAdapter and add it as a key listener to the panel
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                KeyHandler.keyPressed(e.getKeyCode());
                KeyHandler.keyBinds(currentSim, ui);

                // testing sim color
                // if (KeyHandler.isKeyDown(KeyHandler.KEY_D)) {
                //     hue += 1 / 180.0f;
                // }
                // if (KeyHandler.isKeyDown(KeyHandler.KEY_A)) {
                //     hue -= 1 / 180.0f;
                // }

                // testing adding sand switching sim
                if (KeyHandler.isKeyPressed(KeyEvent.VK_SHIFT)) {
                    currentSim.changeIsBusyState();
                    if (currentSim == sim) {
                        currentSim = sim2;
                    }
                    else {
                        currentSim = sim;
                    }
                    currentSim.changeIsBusyState();
                    ui.setCurrentSim(currentSim);
                }
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

    private void update() {
        currentSim.update();

        currentSim.getCurrentRoom().update();

        ui.update();

        if (!ui.isViewingWorld()) {
            sim.update();
            
            sim.getCurrentRoom().update();
        }
        else {
            world.update();
        }
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        // ONLY FOR DEBUGGING
        // ui.drawMockup(g2);

        if (!ui.isViewingWorld()) {
            // Draw room
            currentSim.getCurrentRoom().draw(g2);
            for (Sim s : currentSim.getCurrentRoom().getListOfSims()) {
                // if (s == currentSim) continue;
                // s.drawSimStanding(g2);
                s.draw(g2);
        }

            // Draw sim
            currentSim.draw(g2);
        
            // Draw UI
            ui.draw(g2);
        }
        else {
            // Draw the world
            world.draw(g2);
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