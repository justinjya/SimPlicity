package src.items.interactables;

import java.awt.Color;
import java.awt.image.BufferedImage;

import src.main.GameTime;
import src.main.Consts;
import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;

public class Placeholder extends Interactables {
    // Placeholder Image
    private BufferedImage image;

    public Placeholder(String name, String interaction, int imageIndex, int x, int y, int width, int height, Color color, GameTime time) {
        super(name, interaction, imageIndex, x, y, Consts.SCALED_TILE * width, Consts.SCALED_TILE * height, time);
        setColor(color);
        image = ImageLoader.loadRoom();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void changeOccupied(Sim sim) {}
    public void interact(Sim sim) {}
}
