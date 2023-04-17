package src.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.entities.*;
import src.items.interactables.*;

// x + 12 pas dikiri 6x6 grid
// y - 38 pas diatas 6x6 grid
// y + 283 pas dibawah 6x6 grid

public class GamePanel extends JPanel implements Runnable {
    private GameTime time;
    private Sim sim;
    private Background background;
    private UserInterface ui;
    private Interactables[] solidObjects;

    public GamePanel() {
        setPreferredSize(new Dimension(Consts.WIDTH, Consts.HEIGHT));
        setBackground(Color.WHITE);

        // Create game time
        time = new GameTime(1, 720, 720);

        // Create background
        background = new Background();

        // Create solid objects
        solidObjects = new Interactables[5];
        solidObjects[0] = new Bed((Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) - 38, 0, time);
        solidObjects[1] = new Placeholder("1", "2", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 26, 3, 3, Color.CYAN, time);
        solidObjects[2] = new Placeholder("3", "4", 0, (Consts.CENTER_X / 2) + 268, (Consts.CENTER_Y / 2) - 38, 2, 1, Color.ORANGE, time);
        solidObjects[3] = new Placeholder("5", "6", 0, (Consts.CENTER_X / 2) + 12, (Consts.CENTER_Y / 2) + 282, 1, 1, Color.MAGENTA, time);
        solidObjects[4] = new Placeholder("7", "8", 0, (Consts.CENTER_X / 2) + 332, (Consts.CENTER_Y / 2) + 154, 1, 1, Color.LIGHT_GRAY, time);

        // Create sim
        sim = new Sim("Justin", Consts.CENTER_X + 80, Consts.CENTER_Y, solidObjects); // Scaled

        // Create user interface
        ui = new UserInterface(sim, time);

        // Create a KeyAdapter and add it as a key listener to the panel
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                KeyInput.keyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                KeyInput.keyReleased(e.getKeyCode());
            }
        };
        addKeyListener(keyAdapter);
        setFocusable(true);
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
        sim.update();
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        // ONLY FOR DEBUGGING
        // background.drawGrid(g2);

        // Draw background
        background.draw(g2);

        // Draw bed test
        solidObjects[0].draw(g2, solidObjects[0]);

        // Draw placeholder solid objects
        for (int i = 1; i < 5; i++) {
            solidObjects[i].draw(g2);
        }

        // Draw sim
        sim.draw(g2);

        // Draw UI
        ui.draw(g2);

        g2.dispose();
    }
}