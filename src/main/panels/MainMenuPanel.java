package src.main.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import javax.swing.JPanel;

import src.assets.ImageLoader;

public class MainMenuPanel extends JPanel {
    private int selectedBox = 0; // 0 to 3

    private BufferedImage[] images = ImageLoader.loadMainMenu();

    public MainMenuPanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusTraversalKeysEnabled(false);

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Check if the Enter key was pressed
                if (keyCode == KeyEvent.VK_ENTER) {
                    if (selectedBox == 0) {
                        System.out.println("Switching panels");
                        
                        JPanel parent = (JPanel) getParent();
                        CreateSimPanel createSimPanel = new CreateSimPanel();
                        parent.removeAll();
                        parent.add(createSimPanel);
                        parent.revalidate();
                        parent.repaint();
                        createSimPanel.requestFocusInWindow();
                    }
                    if (selectedBox == 3) {
                        System.exit(0);
                    }
                }
                
                int newBox = selectedBox;

                if (keyCode == KeyEvent.VK_W) {
                    newBox -= 2;
                }
                if (keyCode == KeyEvent.VK_A) {
                    newBox--;
                }
                if (keyCode == KeyEvent.VK_S) {
                    newBox += 2;
                }
                if (keyCode == KeyEvent.VK_D) {
                    newBox++;
                }

                if (newBox >= 0 && newBox <= 3) {
                    selectedBox = newBox;
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

        // TO - DO!!! : Draw selector

        g2.drawImage(images[0], 0, 0, null); // background

        g2.drawImage(images[1], 201, 102, null); // title

        g2.drawImage(images[2], 132, 304, null); // start

        g2.drawImage(images[3], 417, 304, null); // load

        g2.drawImage(images[4], 132, 392, null); // about

        g2.drawImage(images[5], 417, 392, null); // exit
        
        g2.dispose();
    }
}

