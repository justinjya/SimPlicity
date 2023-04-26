package src.Menu;

import java.awt.image.BufferedImage;

import src.main.Consts;
import src.main.GamePanel;
import src.assets.ImageLoader;

public class MenuButton {
    private int xPos, yPos, rowIndex;
    private int xOffetCenter = Consts.WIDTH / 2;
    private BufferedImage[] images;
    private GamePanel game;


    public MenuButton(int xPos, int yPos, int rowIndex, GamePanel game) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.game = game;
        
    }

    public void loadImages(){
        images = new BufferedImage[4];
        BufferedImage mockup = ImageLoader.loadMenuMockup();
        BufferedImage load = ImageLoader.loadLoadButton();
        BufferedImage start = ImageLoader.loadStartButton();
        BufferedImage help = ImageLoader.loadHelpButton();
        BufferedImage exit = ImageLoader.loadExitButton();
        for(int i = 0; i < images.length; i++){
            images[i] = mockup.getSubimage(i * Consts.WIDTH, rowIndex * Consts.HEIGHT, Consts.WIDTH, Consts.HEIGHT);
        }
    }

}
