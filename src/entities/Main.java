package src.entities;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import src.entities.House;
import src.entities.Sim;
import src.entities.World;
import src.entities.handlers.KeyHandler;
import src.main.GameTime;
import src.assets.ImageLoader;
import src.entities.Cursor;

public class Main extends Canvas implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;

    private boolean running;
    private JFrame frame;
    private World world;
    private House house;
    private Sim owner;
    private Cursor cursor;
    private GameTime gameTime;


    public Main() {
        // create game time
        gameTime = new GameTime(1, 720, 720);

        // create a new owner
        owner = new Sim("pbo",0,0,null,house);

        // create a new house at position (100,100)
        house = new House(0, 0, owner);

        // create a new world
        world = new World(gameTime);

        // create a new cursor
        cursor = new Cursor(0,0);
    }

    public void start() {
        // set up the JFrame
        frame = new JFrame();
        frame.setBackground(Color.GRAY);
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
            if(world.getIsAdding()){
                house.move(world);
            }
            else{
                cursor.move();
            }

            // clear the screen
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(2);
                continue;
            }
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.clearRect(0, 0, getWidth(), getHeight());

            // draw the grass and the occupied house
            world.draw(g, cursor, house);
            if(world.getIsAdding()){
                world.drawHouse(g, house);
            }
            else{
                world.drawCursor(g, cursor);
            }
            
            // show the buffer
            bs.show();
            g.dispose();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
            if(!world.getIsAdding()){
                if(world.isLocationOccupied(cursor.getX(), cursor.getY())){
                    if(world.findHouse(cursor.getX(), cursor.getY()).getOwner().getCurrentHouse() == owner.getCurrentHouse()){
                        System.out.println("BERHASIL VISIT");
                        House selectedHouse = world.findHouse(cursor.getX(), cursor.getY());
                        world.getTime().decreaseTimeRemaining((int) Math.sqrt((owner.getX()-selectedHouse.getX())*(owner.getX()-selectedHouse.getX())+(owner.getY()-selectedHouse.getY())*(owner.getY()-selectedHouse.getY())));
                        System.out.println(world.getTime().getDay()+" "+world.getTime().getTimeRemaining());
                    }
                }
            }
            else{
                world.addHouse(house);
            }
        } else {
            KeyHandler.keyPressed(e.getKeyCode());
        }
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
