package src.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.main.Consts;

public class Background {
    private int centerX = Consts.WIDTH / 2 - 3 * Consts.SCALED_TILE;
    private int centerY = Consts.HEIGHT / 2 - 3 * Consts.SCALED_TILE;
    private BufferedImage image;

    public Background() {
        try {
            image = ImageIO.read(new File("./src/assets/wood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int tileX = centerX + x * Consts.SCALED_TILE;
                int tileY = centerY + y * Consts.SCALED_TILE;
                g.drawImage(image, tileX, tileY, Consts.SCALED_TILE, Consts.SCALED_TILE, null);
            }
        }
    }

    public void drawGrid(Graphics2D g) {
        // Draw a drak gray and gray 6x6 grid
        int centerX = Consts.WIDTH / 2 - 3 * Consts.SCALED_TILE;
        int centerY = Consts.HEIGHT / 2 - 3 * Consts.SCALED_TILE;

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                int rectX = centerX + x * Consts.SCALED_TILE;
                int rectY = centerY + y * Consts.SCALED_TILE;
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(rectX, rectY, Consts.SCALED_TILE, Consts.SCALED_TILE);
            }
        }
    }
}

