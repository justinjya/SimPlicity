package src.main;

import java.awt.*;
import javax.swing.*;

import src.main.panels.CreateSimPanel;

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
        
        CreateSimPanel createSimPanel = new CreateSimPanel();
        frame.add(createSimPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
