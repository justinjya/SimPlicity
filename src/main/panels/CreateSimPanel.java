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
import src.main.GamePanel;

public class CreateSimPanel extends JPanel {
    private String[] textFields = { "", "" };
    private int selectedField = 0; // 0 to 3
    private int selectedColor = 2;

    private BufferedImage[] images = ImageLoader.loadCreateSimMenu();

    public CreateSimPanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusTraversalKeysEnabled(false);

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Check if the Enter key was pressed
                if (keyCode == KeyEvent.VK_ENTER && selectedField == 3) {
                    // The Done button was selected, switch panels
                    System.out.println("Switching panels");
                    // Here you can add the code to switch to another panel
                    JPanel parent = (JPanel) getParent();
                    GamePanel gamePanel = new GamePanel(textFields[0], textFields[1], ImageLoader.setColor(selectedColor));
                    parent.removeAll();
                    parent.add(gamePanel);
                    parent.revalidate();
                    parent.repaint();
                    gamePanel.requestFocusInWindow();
                    new Thread(gamePanel).start();
                }
                
                if (selectedField < 2) {
                    // Check if the Backspace key was pressed
                    if (keyCode == KeyEvent.VK_BACK_SPACE) {
                        // Remove the last character from the selected field
                        String text = textFields[selectedField];
                        if (text.length() > 0) {
                            textFields[selectedField] = text.substring(0, text.length() - 1);
                        }
                    }
                    // Check if the key is a letter or a number
                    if ((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) ||
                        (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) ||
                        (keyCode == KeyEvent.VK_SPACE)) {
                        // Append the character to the selected field
                        char c = e.getKeyChar();
                        textFields[selectedField] += c;
                    }
                }
                else {
                    if (keyCode == KeyEvent.VK_D) {
                        selectedColor++;
                    }
                    if (keyCode == KeyEvent.VK_A) {
                        selectedColor--;
                    }

                    if (selectedColor > 7) {
                        selectedColor = 0;
                    }
                    if (selectedColor < 0) {
                        selectedColor = 7;
                    }
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

        g2.drawImage(images[2], 264, 108, null); // sim preview box

        g2.drawImage(images[3], 270, 300, null); // name input box

        g2.drawImage(images[3], 270, 351, null); // room input box

        g2.drawImage(images[4], 318, 416, null); // color picker

        g2.drawImage(images[5], 324 + (selectedColor * 19), 434, null);

        g2.drawImage(images[6], 334, 466, null); // done button

        g2.drawImage(newSim, 336, 130, 128, 128, null);

        g2.setFont(new Font("Inter", Font.PLAIN, 12));
        g2.setColor(new Color(110, 54, 81));

        if (textFields[0] == "") {
            g2.drawString("Enter sim name...", 282, 321);
        }
        else {
            g2.drawString(textFields[0], 282, 321);
        }

        if (textFields[1] == "") {
            g2.drawString("Enter room name...", 282, 372);
        }
        else{
            g2.drawString(textFields[1], 282, 372);
        }

        g2.setFont(new Font("Inter", Font.BOLD, 9));
        g2.setColor(new Color(69, 34, 46));
        g2.drawString("Choose your sim's outfit color", 332, 408);

        g2.setFont(new Font("Inter", Font.PLAIN, 9));
        g2.drawString("press", 358, 514);
        g2.setFont(new Font("Inter", Font.BOLD, 9));
        g2.drawString("esc", 386, 514);
        g2.setFont(new Font("Inter", Font.PLAIN, 9));
        g2.drawString("to cancel", 405, 514);

        g2.dispose();
    }
}
