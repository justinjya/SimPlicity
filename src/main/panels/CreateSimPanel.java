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
    private static final int FIELD_WIDTH = 260;
    private static final int FIELD_HEIGHT = 32;

    private int colorBoxX = 274;
    private int colorBoxY = 394;

    private String[] fieldTexts = { "", "" };
    private int selectedField = 0; // 0 to 4
    private int selectedShirtColor = 2;
    private int selectedHairColor = 0;

    BufferedImage sim = ImageLoader.readImage("sim", "sim_down", 1, 1, true);
    BufferedImage mockup = ImageLoader.readImage("mockup", "createSimMockup", 1, 1, false);

    public CreateSimPanel() {
        setPreferredSize(new Dimension(800, 600));
        setFocusTraversalKeysEnabled(false);

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Check if the Enter key was pressed
                if (keyCode == KeyEvent.VK_ENTER && selectedField == 4) {
                    // The Done button was selected, switch panels
                    System.out.println("Switching panels");
                    // Here you can add the code to switch to another panel
                    JPanel parent = (JPanel) getParent();
                    GamePanel gamePanel = new GamePanel(fieldTexts[0], fieldTexts[1], ImageLoader.setColor(selectedShirtColor));
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
                        String text = fieldTexts[selectedField];
                        if (text.length() > 0) {
                            fieldTexts[selectedField] = text.substring(0, text.length() - 1);
                        }
                    }
                    // Check if the key is a letter or a number
                    if ((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) ||
                        (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9)) {
                        // Append the character to the selected field
                        char c = e.getKeyChar();
                        fieldTexts[selectedField] += c;
                    }
                }
                else {
                    if (keyCode == KeyEvent.VK_D) {
                        if (selectedField == 2) {
                            selectedShirtColor++;
                        }
                        if (selectedField == 3) {
                            selectedHairColor++;
                        }
                    }
                    if (keyCode == KeyEvent.VK_A) {
                        if (selectedField == 2) {
                            selectedShirtColor--;
                        }
                        if (selectedField == 3) {
                            selectedHairColor--;
                        }
                    }

                    if (selectedShirtColor > 7) {
                        selectedShirtColor = 0;
                    }
                    if (selectedShirtColor < 0) {
                        selectedShirtColor = 7;
                    }
                    if (selectedHairColor > 7) {
                        selectedHairColor = 0;
                    }
                    if (selectedHairColor < 0) {
                        selectedHairColor = 7;
                    }
                }
                // Check if the Tab key was pressed
                if (keyCode == KeyEvent.VK_TAB) {
                    // Move to the next field or the Done button
                    selectedField++;
                    if (selectedField > 4) {
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
        
        g2.drawImage(mockup, 0, 0, null);

        BufferedImage newSim = ImageLoader.simColorSelector(selectedShirtColor, selectedHairColor);

        g2.drawImage(newSim, 336, 102, 128, 128, null);

        // Draw the text fields
        for (int i = 0; i < 2; i++) {
            String text = fieldTexts[i];
            int x = 270;
            int y = 272 + i * (FIELD_HEIGHT + 18);
            boolean isSelected = i == selectedField;

            // Draw the field background
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, FIELD_WIDTH, FIELD_HEIGHT);

            // Draw the field border
            g2.setColor(isSelected ? Color.BLUE : Color.BLACK);
            g2.drawRect(x, y, FIELD_WIDTH, FIELD_HEIGHT);

            // Draw the text
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.drawString(text, x + 5, y + 20);
        }

        g2.setColor(Color.YELLOW);
        g2.drawRect(colorBoxX + (selectedShirtColor * 20), colorBoxY, 20, 20);

        g2.setColor(Color.YELLOW);
        g2.drawRect(colorBoxX + (selectedHairColor * 20), colorBoxY + 50, 20, 20);

        // Draw the "Done" button
        int buttonX = 320;
        int buttonY = 494;
        int buttonWidth = FIELD_WIDTH - 100;
        int buttonHeight = 29;
        boolean buttonSelected = selectedField == 4;
        g2.setColor(buttonSelected ? Color.BLUE : Color.GREEN);
        g2.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Done", buttonX + 60, buttonY + 20);
        g2.dispose();
    }
}
