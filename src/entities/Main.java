package src.entities;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import src.entities.House;
import src.entities.Sim;
import src.entities.World;
import src.entities.handlers.KeyHandler;
import src.assets.ImageLoader;

public class Main extends Canvas implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;

    private boolean running;
    private JFrame frame;
    private World world;
    private House house;
    private Sim owner;

    public Main() {

        // create a new house at position (100,100)
        house = new House(0, 0);

        // create a new world
        world = new World();
    }

    public void start() {
        // set up the JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.add(this);

        // start the game loop
        running = true;
        new Thread(this).start();
    }

    public void run() {
        while (running) {
            // move the house
            house.move(world);

            // clear the screen
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(2);
                continue;
            }
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, getWidth(), getHeight());

            // draw the world and the house
            world.draw(g);
            world.drawHouse(g, house);

            // show the buffer
            bs.show();
            g.dispose();
        }
    }

    public void keyPressed(KeyEvent e) {
        KeyHandler.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        KeyHandler.keyReleased(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {

        // create and start the game
        Main game = new Main();
        game.start();
    }
}
