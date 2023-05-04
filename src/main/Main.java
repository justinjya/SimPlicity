package src.main;

import java.awt.*;
import javax.swing.*;

import src.main.panels.GamePanel;
import src.main.panels.MainMenuPanel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("SimPlicity");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);

        // Load your image file and create an ImageIcon object
        ImageIcon icon = new ImageIcon("src/assets/icon.png");

        // Set the icon of the JFrame
        frame.setIconImage(icon.getImage());


        // ONLY FOR DEBUGGING
        frame.add(GamePanel.getInstance());
        GameLoader.startNewGameDebug();

        // frame.add(MainMenuPanel.getInstance());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
