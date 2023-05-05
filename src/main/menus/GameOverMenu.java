package src.main.menus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.assets.ImageLoader;

public class GameOverMenu {
    private static BufferedImage[] images = ImageLoader.loadGameOverMenu();
    private static BufferedImage gameOverBox = images[0];
    private static BufferedImage escBox = images[1];
    private static BufferedImage continueableTitleBox = images[2];

    public static void draw(Graphics2D g) {
        BufferedImage mockup = ImageLoader.readImage("menus/game_over_menu", "game_over_layout", 1, 1, false);

        g.drawImage(mockup, 0, 0, null);

        g.drawImage(gameOverBox, 76, 108, null);
    }
}
