package src.Menu;
import java.awt.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    private static final int SCREEN_WIDTH = 50;
    private static final int SCREEN_HEIGHT = 500;
    private static final String GAME_TITLE = "Main Menu Example";
    
    private JButton playButton;
    private JButton optionsButton;
    private JButton quitButton;

    public MainMenu() {
        super(GAME_TITLE);
        
        // Create buttons
        playButton = new JButton("Play");
        optionsButton = new JButton("Options");
        quitButton = new JButton("Quit");
        
        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(playButton);
        buttonPanel.add(optionsButton);
        buttonPanel.add(quitButton);
        add(buttonPanel);
        
        // Set up window
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
