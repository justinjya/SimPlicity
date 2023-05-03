package src.entities.sim;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import src.items.Item;
import src.items.foods.RawFood;
import src.main.UserInterface;
import src.assets.ImageLoader;
import src.entities.interactables.*;
import src.main.GameTime;
import src.main.KeyHandler;

public class Store {
    private ArrayList<String> listOfItem;
    private BufferedImage[] storeLayout = ImageLoader.loadStore();
    private BufferedImage[] objectImages = new BufferedImage[12];
    private BufferedImage[] foodImages = new BufferedImage[8];
    private boolean isOpen = true;
    private boolean isObject = true;
    private int slotCol = 1;
    private int slotRow = 1;
    public boolean isChoosing = true;
    public boolean isSelecting = false;
    public boolean isBuySuccess = true;
    private int simNameX = 282;
    private int simNameY = 532;
    private int simMoneyX = 448;
    private int simMoneyY = 534;
    private int itemQuantity = 1;
    private int itemQuantityX = 240;
    private int itemQuantityY = 400;
    private int slotSize = 64;
    private int cursorX = 214;
    private int cursorY = 178;

    // CONSTRUCTOR
    public Store() {
        listOfItem = new ArrayList<>();
        listOfItem.add("Aquarium");
        listOfItem.add("Single Bed");
        listOfItem.add("Queen Size Bed");
        listOfItem.add("King Size Bed");
        listOfItem.add("Door");
        listOfItem.add("Shower");
        listOfItem.add("Gas Stove");
        listOfItem.add("Electric Stove");
        listOfItem.add("Toilet");
        listOfItem.add("Trash Bin");
    }

    
    public boolean getIsOpen() {
        return isOpen;
    }
    public void changeIsOpen() {
        isOpen = !isOpen;
        UserInterface.getCurrentSim().changeIsBusyState();
    }
    public void changeIsObject() {
        isObject = !isObject;
    }

    
    // update the state of the store
    public void update() {
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_W)) {
            if (isChoosing){
                if (isObject) {
                    if (slotRow == 3 || slotRow == 2) {
                        slotRow--;
                    }
                }
                else {
                    if (slotRow == 2) {
                        slotRow--;
                    }
                }
                
            }
            
            
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            if (isChoosing) {
                if (slotCol > 1) {
                    slotCol--;
                }
            }
            
            
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            if (isChoosing) {
                if (isObject) {
                    if (slotRow == 1) {
                        slotRow++;
                    }
                    else if (slotRow == 2) {
                        if (slotCol <= 2) {
                            slotRow++;
                        }
                    }
                }
                else {
                    if (slotRow == 1) {
                        if (slotCol <= 3) {
                            slotRow++;
                        }
                    }
                }               
            }
        
            
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            if (isChoosing) {
                if (isObject) {
                    if (slotRow <= 3) {
                        if (slotRow == 3) {
                            if (slotCol == 1) {
                                slotCol++;
                            }
                        }
                        else{
                            if (slotCol < 5) {
                                slotCol++;
                            }
                        }
                    }
                }
                else {
                    if (slotRow == 1) {
                        if (slotCol < 5) {
                            slotCol++;
                        }
                    }
                    else if (slotRow == 2) {
                        if (slotCol < 3) {
                            slotCol++;
                        }
                    }
                }
            }
            
            
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            if (isChoosing) {
                isChoosing = false;
                isSelecting = true;
            }
            else if (isSelecting) {
                interact();
            }
            
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_TAB)) {
            if (isChoosing) {
                changeIsObject();
                slotRow = 1;
                slotCol = 1;
            }
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_EQUALS)) {
            if (isSelecting) {
                itemQuantity++;
            }
            
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_MINUS)) {
            if (isSelecting) {
                if (itemQuantity > 1) {
                    itemQuantity--;
                }
                
            }
        }
        else if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
            // Restore the initial state for the next purchase
            isChoosing = true;
            isSelecting = false;
            isBuySuccess = true;
            isOpen = false; // to do : pas tab jadi true
            isObject = true;
            slotCol = 1;
            slotRow = 1;
            itemQuantity = 1;
        }
        
        
    }

    // draw the ui
    public void draw(Graphics2D g) {
        if (isOpen) {
            drawFrame(g);
            // drawItem(g);
            drawSimInfo(g);
            drawQuantity(g);
            drawCursor(g);
        }
    }

    
    // interact
    // jangan lupa reset state juga di sini kalau berhasil, selain esc
    BufferedImage[] bedImages = ImageLoader.loadBeds();
    BufferedImage[] toiletImage = ImageLoader.loadToilet();
    BufferedImage[] stoveIcon = ImageLoader.loadStovesIcons();
    BufferedImage[] tableAndChairIcon = ImageLoader.loadStovesIcons();
    BufferedImage[] clockIcon = ImageLoader.loadStovesIcons();
    BufferedImage binIcon = ImageLoader.loadTrashBinIcon();
    BufferedImage[] doorIcon = ImageLoader.loadDoor();
    BufferedImage[] showerIcon = ImageLoader.loadStovesIcons();
    BufferedImage[] aquariumIcon = ImageLoader.loadStovesIcons();


    public void interact() {
        int itemPrice;
        int timeUpperBound = 30;
        int timeLowerBound = 1;
        Item item=null;
        int deliveryTime = (int) Math.random()*(timeUpperBound-timeLowerBound) + timeLowerBound;
        if (isObject) {
            if (slotRow == 1) {
                if (slotCol == 1) {
                    item = new Bed(0,0,0);
                }
                else if(slotCol == 2) {
                    item = new Bed(0,0,1);
                }
                else if (slotCol == 3) {
                    item = new Bed(0,0,2);
                }
                else if (slotCol == 4) {
                    item = new Toilet(0, 0);
                }
                else if (slotCol == 5) {
                    item = new Stove(0,0,0);
                }
            }
            else if (slotRow == 2) {
                if (slotCol == 1) {
                    item = new Stove(0,0,1);
                }
                else if(slotCol == 2) {
                    item = new Stove(0,0,0);
                }
                else if (slotCol == 3) {
                    item = new Stove(0,0,0);
                }
                else if (slotCol == 4) {
                    item = new Aquarium(0, 0);
                }
                else if (slotCol == 5) {
                    item = new Door(null);
                }
            }
            else if (slotRow == 3) {
                if (slotCol == 1) {
                    item = new Shower(0, 0);
                }
                else if (slotCol == 2) {
                    item = new TrashBin(0, 0);
                }
            }
        }
        else {
            if (slotRow == 1) {
                if (slotCol == 1) {
                    item = new RawFood(0);
                }
                else if(slotCol == 2) {
                    item = new RawFood(1);
                }
                else if (slotCol == 3) {
                    item = new RawFood(2);
                }
                else if (slotCol == 4) {
                    item = new RawFood(3);
                }
                else if (slotCol == 5) {
                    item = new RawFood(4);
                }
            }
            else if (slotRow == 2) {
                if (slotCol == 1) {
                    item = new RawFood(5);
                }
                else if(slotCol == 2) {
                    item = new RawFood(6);
                }
                else if (slotCol == 3) {
                    item = new RawFood(7);
                }
            }
        }
        GameTime.startDecrementTimeRemaining(deliveryTime);
        if(UserInterface.getCurrentSim().getMoney() >= item.getPrice()){
            // must put the code to add to inventory
            UserInterface.getCurrentSim().getInventory().addItem(item);
            UserInterface.getCurrentSim().setMoney(UserInterface.getCurrentSim().getMoney() - item.getPrice());
        }

    }

    

    public void drawFrame(Graphics2D g) {
        if (isChoosing) {
            g.drawImage(storeLayout[0], 0, 0, null);
        }
        if (isSelecting) {
            g.drawImage(storeLayout[2], 0, 0, null);
        }
        // if (!isBuySuccess) {
        //     g.drawImage(storeLayout[1], 0, 0, null);
        // }
    }

    public void drawItem(Graphics2D g) {
        objectImages[0] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[1] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[2] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[3] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[4] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[5] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[6] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[7] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[8] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[9] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[10] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        objectImages[11] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        

        foodImages[0] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[1] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[2] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[3] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[4] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[5] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[6] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        foodImages[7] = ImageLoader.readImage("tiles", "grass", 1, 1, false);
        
        int imageX = 214;
        int imageY = 178;
        int i = 0;
        int upper_i = 0;
        if (isObject) {
            upper_i = 7;
        }
        else {
            upper_i = 8;
        }
        while (i < upper_i){
            g.drawImage(objectImages[i], imageX, imageY, null);
            imageX = imageX + slotSize + 13;
            if(i == 4) {
                imageX = 214;
                imageY = imageY + slotSize + 17;
            }
            i++;
        }
    }

    

    public void drawSimInfo(Graphics2D g) {
        g.drawString(UserInterface.getCurrentSim().getName(), simNameX, simNameY);
        g.drawString(Integer.toString(UserInterface.getCurrentSim().getMoney()), simMoneyX, simMoneyY);
    }
    
    
    public void drawQuantity(Graphics2D g) {
        if (isSelecting) {
            g.drawString(Integer.toString(itemQuantity), itemQuantityX, itemQuantityY);
        }
    }

    
    public void drawCursor(Graphics2D g) {
        if (isChoosing) {
            g.setColor(Color.BLUE);
            g.fillRect(cursorX + (slotSize * (slotCol - 1)) + (13 * (slotCol - 1)), cursorY + (slotSize * (slotRow - 1)) + (17 * (slotRow - 1)), slotSize, slotSize);
        }
    }
    
}   
