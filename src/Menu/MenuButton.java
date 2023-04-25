package src.Menu;

import javax.swing.*;
import java.awt.*;
import src.assets.ImageLoader;
import src.entities.Interactables;
import java.awt.image.BufferedImage;


public class MenuButton {
    private Interactables objectInteractables;
    private int xPos, yPos, rowIndex;
    private BufferedImage mockup;
    public MenuButton(int xPos, int yPos, int rowIndex, Interactables obejctInteractables ) {
        this.mockup = ImageLoader.loadMenuMockup();
    }
    
}
