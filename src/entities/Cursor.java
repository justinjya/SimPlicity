package src.entities;

import src.entities.handlers.KeyHandler;

public class Cursor {
    private int x;
    private int y;

    public Cursor(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void move(){
        if (KeyHandler.isKeyDown(KeyHandler.KEY_A) || KeyHandler.isKeyDown(KeyHandler.KEY_D) || 
            KeyHandler.isKeyDown(KeyHandler.KEY_W) || KeyHandler.isKeyDown(KeyHandler.KEY_S)){
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
                x -= 17;
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
                x += 17;
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
                y -= 17;
                
            }
            if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
                y += 17;  
            }
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
