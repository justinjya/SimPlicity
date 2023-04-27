package src.gamestates;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import src.main.Consts;
import src.main.GamePanel;
import src.assets.ImageLoader;
import java.awt.Rectangle;

public class Menu {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = Consts.WIDTH / 2;
    private BufferedImage[] images;
    private Gamestate state;
    private int HEIGHT = Consts.HEIGHT;
    private int WIDTH = Consts.WIDTH;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public Menu(int xPos, int yPos, int rowIndex, GamePanel game) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        initBounds();
    }

    public void initBounds(){
        bounds = new Rectangle(xPos-xOffsetCenter, yPos, WIDTH, HEIGHT );
    }

    public void loadImages(){
        images = new BufferedImage[4];
        BufferedImage mockup = ImageLoader.loadMenuMockup();
        BufferedImage load = ImageLoader.loadLoadButton();
        BufferedImage start = ImageLoader.loadStartButton();
        BufferedImage help = ImageLoader.loadHelpButton();
        BufferedImage exit = ImageLoader.loadExitButton();
        for(int i = 0; i < images.length; i++){
            images[i] = mockup.getSubimage(i * WIDTH, rowIndex * HEIGHT, WIDTH, HEIGHT);
        }
    }

    public void drawButton(Graphics g){
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, WIDTH, HEIGHT, null);
    }

    public void update(){
        index = 0;
        if(mouseOver){
            index = 1;
        }
        if(mousePressed){
            index = 2;
        }
    }

    public boolean isMouseOver(){
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed(){
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
