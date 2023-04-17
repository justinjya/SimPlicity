package src.items.interactables;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.main.GameTime;
import src.main.Consts;
import src.entities.Sim;

public class Placeholder extends Interactables {
    // Placeholder Image
    private BufferedImage image;

    public Placeholder(String name, String interaction, int imageIndex, int x, int y, int width, int height, Color color, GameTime time) {
        super(name, interaction, imageIndex, x, y, Consts.SCALED_TILE * width, Consts.SCALED_TILE * height, time);
        setColor(color);
        try {
            image = ImageIO.read(new File("./src/assets/wood.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void changeOccupied(Sim sim) {}
    public void interact(Sim sim) {}
}
