package src.entities.sim;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.OffsetDateTime;

import src.items.Item;
import src.items.foods.RawFood;
import src.main.UserInterface;
import src.assets.ImageLoader;
import src.entities.interactables.*;
import src.main.GameTime;
import src.main.KeyHandler;
import src.entities.interactables.Clock;
import src.entities.interactables.TrashBin;

public class Store {
    private ArrayList<Item> listOfItem;
    private BufferedImage[] storeLayout = ImageLoader.loadStore();
    // private BufferedImage[] objectImages = new BufferedImage[12];
    // private BufferedImage[] foodImages = new BufferedImage[8];
    private static boolean isOpen = false;
    private boolean isObject = true;
    private int slotCol = 1;
    private int slotRow = 1;
    private int slotSelected = 0;
    public boolean isChoosing = true;
    public boolean isSelecting = false;
    public boolean isBuySuccess = true;
    private int simNameX = 307;
    private int simNameY = 532;
    private int simMoneyX = 468;
    private int simMoneyY = 534;
    private int itemQuantity = 1;
    private int itemQuantityX = 393;
    private int itemQuantityY = 462;
    private int slotSize = 64;
    private int cursorX = 214;
    private int cursorY = 178;

    // CONSTRUCTOR
    public Store() {
        listOfItem = new ArrayList<>();
        listOfItem.add(new Bed(0,0,0));
        listOfItem.add(new Bed(0,0,1));
        listOfItem.add(new Bed(0,0,2));
        listOfItem.add(new Toilet(0, 0));
        listOfItem.add(new Stove(0,0,0));
        listOfItem.add(new Stove(0, 0, 1));
        listOfItem.add(new TableAndChair(0, 0));
        listOfItem.add(new Clock(0,0));
        listOfItem.add(new Shower(0, 0));
        listOfItem.add(new Aquarium(0, 0));
        listOfItem.add(new Television(0, 0));
        listOfItem.add(new TrashBin(0, 0,0));
        listOfItem.add(new RawFood(0));
        listOfItem.add(new RawFood(1));
        listOfItem.add(new RawFood(2));
        listOfItem.add(new RawFood(3));
        listOfItem.add(new RawFood(4));
        listOfItem.add(new RawFood(5));
        listOfItem.add(new RawFood(6));
        listOfItem.add(new RawFood(7));
    }
    
    public static boolean getIsOpen() {
        return isOpen;
    }
    public static void changeIsOpen() {
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
                        slotSelected-=5;
                    }
                }
                else {
                    if (slotRow == 2) {
                        slotRow--;
                        slotSelected-=5;
                    }
                }
                System.out.println("slotSelected:" + slotSelected);  
            } 
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_A)) {
            if (isChoosing) {
                if (slotCol > 1) {
                    slotCol--;
                    slotSelected--;
                }
                System.out.println("slotSelected:" + slotSelected);
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_S)) {
            if (isChoosing) {
                if (isObject) {
                    if (slotRow == 1) {
                        slotRow++;
                        slotSelected+=5;
                    }
                    else if (slotRow == 2) {
                        if (slotCol <= 2) {
                            slotRow++;
                            slotSelected+=5;
                        }
                    }
                }
                else {
                    if (slotRow == 1) {
                        if (slotCol <= 3) {
                            slotRow++;
                            slotSelected+=5;
                        }
                    }
                } 
                System.out.println("slotSelected:" + slotSelected);              
            } 
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_D)) {
            if (isChoosing) {
                if (isObject) {
                    if (slotRow <= 3) {
                        if (slotRow == 3) {
                            if (slotCol == 1) {
                                slotCol++;
                                slotSelected++;
                            }
                        }
                        else{
                            if (slotCol < 5) {
                                slotCol++;
                                slotSelected++;
                            }
                        }
                    }
                }
                else {
                    if (slotRow == 1) {
                        if (slotCol < 5) {
                            slotCol++;
                            slotSelected++;
                        }
                    }
                    else if (slotRow == 2) {
                        if (slotCol < 3) {
                            slotCol++;
                            slotSelected++;
                        }
                    }
                }
                System.out.println("slotSelected:" + slotSelected);
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ENTER)) {
            if (isChoosing) {
                isChoosing = false;
                isSelecting = true;
                System.out.println("MASUK ");
                return;
            }
            if (isSelecting) {
                isChoosing = false;
                isSelecting = false;
                System.out.println("berhasil interact");
                interact();
            }  
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_TAB)) {
            System.out.println("MASUK");
            if (isChoosing) {
                changeIsObject();
                slotRow = 1;
                slotCol = 1;
                slotSelected = 12;
                System.out.println("MASUKTAB");
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_EQUALS)) {
            if (isSelecting) {
                itemQuantity++;
            } 
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_MINUS)) {
            if (isSelecting) {
                if (itemQuantity > 1) {
                    itemQuantity--;
                }
            }
        }
        if (KeyHandler.isKeyPressed(KeyHandler.KEY_ESCAPE)) {
            // Restore the initial state for the next purchase

            if (isChoosing) {
                changeIsOpen(); // to do : pas tab jadi true
                isChoosing = true;
                isSelecting = false;
                isBuySuccess = true;
                isObject = true;
                slotCol = 1;
                slotRow = 1;
                slotSelected = 0;
                itemQuantity = 1;
            }

            if (isSelecting) {
                isChoosing = true;
                isSelecting = false;
                itemQuantity = 1;
                System.out.println("msuk");
            }
            
            if (!isBuySuccess) {
                isSelecting = true;
                isBuySuccess = true;
            }
            
        } 
    }

    // draw the ui
    public void draw(Graphics2D g) {
        if (isOpen) {
            drawFrame(g);
            drawItem(g);
            drawSimInfo(g);
            drawQuantity(g);
            drawCursor(g);
        }
    }

    public void interact() {
        Thread buying = new Thread(new Runnable() {
            @Override
            public void run() {
                // if(UserInterface.getCurrentSim().getMoney() >= listOfItem.get(slotSelected).getPrice()){
                if(UserInterface.getCurrentSim().getMoney() >= 11000){
                      
                    changeIsOpen();
                    isChoosing = true;
                    isSelecting = false;
                    isBuySuccess = true;
                    isObject = true;
                    slotCol = 1;
                    slotRow = 1;
                    
                    itemQuantity = 1;
                    int timeUpperBound = 150;
                    int timeLowerBound = 30;
                    int deliveryTime = (int) Math.random()*(timeUpperBound-timeLowerBound) + timeLowerBound;
                    
                    System.out.println("item:"+listOfItem.get(slotSelected).getName());
                    UserInterface.getCurrentSim().setMoney(UserInterface.getCurrentSim().getMoney() - listOfItem.get(slotSelected).getPrice());
                    Thread t = GameTime.startDecrementTimeRemaining(deliveryTime);
                    
                    System.out.println("deliveryTimeee:"+deliveryTime);

                    while (t.isAlive()) continue;

                    UserInterface.getCurrentSim().getInventory().addItem(listOfItem.get(slotSelected));
                    System.out.println("Berhasil masak");   
                    slotSelected = 0;    
                }
                else {
                    isBuySuccess = false;
                }
            }
        });
        buying.start(); 
    }
    int offSetY = 20;
    public void drawFrame(Graphics2D g) {
        
        g.setFont(new Font("Inter", Font.PLAIN, 10));
        g.setColor(Color.decode("#6EC4D5"));
        g.fillRect(0, 0, 800, 600);
        g.drawImage(storeLayout[6], 150, 72-offSetY, null);
        g.drawImage(storeLayout[7], 294, 47-offSetY, null);
        g.drawImage(storeLayout[1], 188, 112-offSetY, null);
        g.drawImage(storeLayout[1], 407, 112-offSetY, null);
        g.drawImage(storeLayout[0], 187, 156-offSetY, null);
        g.drawImage(storeLayout[5], 261, 529-offSetY, null);
        g.setColor(Color.BLACK);
        g.drawString("press esc to return", 364, 575-offSetY);
        int x = 214;
        int y = 178;
        int countItemPlaceHolder = 0;

        // while (countItemPlaceHolder < 15) {
        //     // g.drawImage(storeLayout[8], x, y-offSetY, null);
        //     x += 77;
        //     if (countItemPlaceHolder == 4 || countItemPlaceHolder == 9) {
        //         x = 214;
        //         y += 81;
        //     }
        //     countItemPlaceHolder++;
        // }
        
        
        
        
        
        if (isChoosing) {
            //g.drawImage(storeLayout[0], 0, 0, null);
            g.drawString("choose item to buy", 366, 463-offSetY);
        }
        if (isSelecting) {
            // g.drawImage(storeLayout[2], 0, 0, null);
            g.drawImage(storeLayout[3], 331, 462-offSetY, null);
            g.drawImage(storeLayout[4], 445, 464-offSetY, null);
            g.drawImage(storeLayout[2], 369, 462-offSetY, null);
            
        }
        if (!isBuySuccess) {
            // g.drawImage(storeLayout[1], 0, 0, null);
            g.drawString("you don't have enough money", 346, 453-offSetY);
            g.drawImage(storeLayout[3], 331, 462-offSetY, null);
            g.drawImage(storeLayout[4], 445, 464-offSetY, null);
            g.drawImage(storeLayout[2], 369, 462-offSetY, null);
        }

        g.setFont(new Font("Inter", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString("Objects", 272, 133-offSetY);
        g.drawString("Foods", 491, 133-offSetY);
        g.setFont(new Font("Inter", Font.BOLD, 20));
        g.drawString("Store", 380, 74-offSetY);
    }

    public void drawItem(Graphics2D g) {
        int imageX = 214;
        int imageY = 178;
        int i = 0;
        int upper_i = 0;
        if (isObject) {
            upper_i = 12;
        }
        else {
            upper_i = 20;
            i = 12;
        }
        while (i < upper_i){
            g.drawImage(listOfItem.get(i).getIcon(), imageX, imageY-offSetY, null);
            imageX = imageX + slotSize + 13;
            if (isObject) {
                if(i % 5 == 4) {
                    imageX = 214;
                    imageY = imageY + slotSize + 17;
                }
            }
            else {
                if (i == 16) {
                    imageX = 214;
                    imageY = imageY + slotSize + 17;
                }
            }
            i++;
        }
    }

    public void drawSimInfo(Graphics2D g) {
        g.drawString(UserInterface.getCurrentSim().getName(), simNameX, simNameY);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(UserInterface.getCurrentSim().getMoney()), simMoneyX, simMoneyY);
    }
    
    
    public void drawQuantity(Graphics2D g) {
        if (isSelecting) {
            g.drawString(Integer.toString(itemQuantity), itemQuantityX, itemQuantityY);
        }
    }

    public void drawCursor(Graphics2D g) {
        if (isChoosing) {
            g.setStroke(new BasicStroke(6f));
            g.setColor(Color.WHITE);
            g.drawRect(cursorX + (slotSize * (slotCol - 1)) + (13 * (slotCol - 1)), cursorY + (slotSize * (slotRow - 1)) + (17 * (slotRow - 1)) - offSetY, slotSize, slotSize);
        }
    }
}   