package src.main.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import javax.swing.JPanel;

import src.assets.ImageLoader;
import src.main.GameLoader;
import src.main.KeyHandler;
import src.main.ui.UserInterface;

public class CreateSimPanel extends JPanel {
    public static CreateSimPanel csp = new CreateSimPanel();

    public static String[] textFields = { "", "" };
    public static String simName = textFields[0];
    public static String roomName = textFields[1];
    public static int selectedColor = 2;
    private static int selectedField = 0; // 0 to 3

    private BufferedImage[] images = ImageLoader.loadCreateSimMenu();

    private CreateSimPanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusTraversalKeysEnabled(false);

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Check if the Enter key was pressed on the done button
                if (keyCode == KeyEvent.VK_ENTER && selectedField == 3) {
                    if (GamePanel.isCurrentState("Starting a new game: Creating a new sim")) {
                        GamePanel.gameState = "Starting a new game: Placing a new house";
                        GameLoader.startNewGame();
                    }
                    if (GamePanel.isCurrentState("Creating a new sim")) {
                        GamePanel.gameState = "Placing a new house";
                        GameLoader.addSim();
                    }
                    
                    PanelHandler.switchPanel(CreateSimPanel.getInstance(), GamePanel.getInstance());
                }

                if (keyCode == KeyEvent.VK_ESCAPE) {
                    if (GamePanel.isCurrentState("Starting a new game: Creating a new sim")) {
                        GamePanel.gameState = "Main menu";
                        PanelHandler.switchPanel(CreateSimPanel.getInstance(), MainMenuPanel.getInstance());
                    }
                    if (GamePanel.isCurrentState("Creating a new sim")) {
                        GamePanel.gameState = "Playing";
                        PanelHandler.switchPanel(CreateSimPanel.getInstance(), GamePanel.getInstance());
                    } 
                }
                
                // names text feild
                if (selectedField < 2) {
                    textFields[selectedField] = KeyHandler.receiveStringInput(e, textFields[selectedField]);
                    simName = textFields[0];
                    roomName = textFields[1];
                }
                // color selector
                if (selectedField  == 2) {
                    if (keyCode == KeyEvent.VK_D) selectedColor++;
                    if (keyCode == KeyEvent.VK_A) selectedColor--;
                    if (selectedColor > 7) selectedColor = 0;
                    if (selectedColor < 0) selectedColor = 7;
                }

                // Check if the Tab key was pressed
                if (keyCode == KeyEvent.VK_TAB) {
                    // Move to the next field or the Done button
                    selectedField++;
                    if (selectedField > 3) {
                        selectedField = 0;
                    }
                }
                repaint();
            }
        };
        addKeyListener(keyAdapter);
        setFocusable(true);
    }

    public static CreateSimPanel getInstance() {
        return csp;
    }

    public static void init() {
        csp = new CreateSimPanel();
        reset();
    }

    public static void reset() {
        textFields[0] = "";
        textFields[1] = "";
        selectedField = 0;
        selectedColor = 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        BufferedImage newSim = ImageLoader.simColorSelector(selectedColor);

        g2.setColor(new Color(110, 196, 213));
        g2.fillRect(0, 0, 800, 600);

        g2.drawImage(images[0], 239, 60, null); // background box

        g2.drawImage(images[1], 225, 60, null); // title box
        g2.setFont(new Font("Inter", Font.BOLD, 12));
        g2.setColor(Color.WHITE);
        g2.drawString("Create New Sim", 352, 82);

        // draw boxes
        g2.drawImage(images[2], 264, 98, null); // sim preview box
        g2.drawImage(images[3], 270, 290, null); // name input box
        g2.drawImage(images[3], 270, 331, null); // room input box
        g2.drawImage(images[4], 318, 391, null); // color picker
        g2.drawImage(images[6], 334, 436, null); // done button

        // draw highlighted boxes and color picker cursor
        if (selectedField == 0) g2.drawImage(images[7], 267, 287, null);
        if (selectedField == 1) g2.drawImage(images[7], 267, 328, null);
        if (selectedField == 2) g2.drawImage(images[8], 315, 388, null);
        if (selectedField == 3) g2.drawImage(images[9], 337, 435, null);

        g2.drawImage(images[5], 324 + (selectedColor * 19), 410, null); // color picker cursor
        g2.drawImage(newSim, 336, 130, 128, 128, null); // sim preview image

        g2.setFont(new Font("Inter", Font.PLAIN, 12));
        g2.setColor(new Color(110, 54, 81));

        if (textFields[0].equals("")) {
            g2.drawString("Enter sim name...", 282, 311);
        }
        else {
            g2.drawString(textFields[0], 282, 311);
        }

        if (textFields[1].equals("")) {
            g2.drawString("Enter room name...", 282, 352);
        }
        else{
            g2.drawString(textFields[1], 282, 352);
        }

        g2.setFont(new Font("Inter", Font.BOLD, 9));
        g2.setColor(new Color(69, 34, 46));
        g2.drawString("Choose your sim's outfit color", 331, 385);

        g2.setFont(new Font("Inter", Font.PLAIN, 9));
        g2.drawString("press", 358, 484);
        g2.setFont(new Font("Inter", Font.BOLD, 9));
        g2.drawString("esc", 386, 484);
        g2.setFont(new Font("Inter", Font.PLAIN, 9));
        g2.drawString("to cancel", 405, 484);

        g2.dispose();
    }
}
