package src.items.interactables;

import java.awt.Color;
import java.awt.image.BufferedImage;

import src.main.GameTime;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;

public class Placeholder extends Interactables {
    // Placeholder Image
    private BufferedImage image;

    public Placeholder(String name, String interaction, int imageIndex, int x, int y, int width, int height, Color color, GameTime time) {
        super(name, interaction, imageIndex, x, y, width, height, time);
        setColor(color);
        image = ImageLoader.loadWood();
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void changeOccupied(Sim sim) {}
    
    @Override
    public void interact(Sim sim) {}
}
