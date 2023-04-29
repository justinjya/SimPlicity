package src.entities.sim;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

import src.assets.ImageLoader;
import src.entities.Entity;
import src.entities.handlers.CollisionHandler;
import src.entities.handlers.InteractionHandler;
import src.main.GameTime;
import src.world.Room;
import src.world.House;

public class Sim extends Entity{
    // Atributes
    private String name;
    private int health;
    private int hunger;
    private int mood;
    private int money;
    private String status;
    private boolean isBusy;
    private Room currentRoom;
    private House currentHouse;
    private Profession profession;
    private int durationOfWork;
    private int durationofWork1;
    private boolean hasChangedProfession;
    private GameTime changeProfessionTime;
    private GameTime timeNotDumped;

    
    // Image of the sim
    private BufferedImage[] images = new BufferedImage[12];

    // Collisions and interactions
    private CollisionHandler collisionHandler;
    private InteractionHandler interactionHandler;

    // CONSTRUCTOR
    public Sim(String name, int x, int y, Room currentRoom, House currentHouse) {
        // Atributes
        super (
            x,
            y,
            1,
            1
        );
        
        this.name = name;
        this.health = 80;
        this.hunger = 80;
        this.mood = 80;
        this.money = 100;
        this.status = "Idle";
        this.isBusy = false;
        this.currentRoom = currentRoom;
        this.profession = new Profession();
        this.durationOfWork = 0;
        this.durationofWork1 = 0;
        this.hasChangedProfession = false;
        this.changeProfessionTime = new GameTime(1, 720, 720);
        this.timeNotDumped = new GameTime(1, 720, 720);

        // Place the sim inside of the current room
        currentRoom.addSim(this);
        this.currentHouse = currentHouse;

        // Load the image of the sim
        images = ImageLoader.loadSim();

        // Collisions and interactions
        collisionHandler = new CollisionHandler(this, currentRoom);
        interactionHandler = new InteractionHandler(this, currentRoom);
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getHunger() {
        return hunger;
    }

    public int getMood() {
        return mood;
    }
    
    public int getMoney() {
        return money;
    }

    public String getStatus() {
        return status;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public House getCurrentHouse() {
        return currentHouse;
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public boolean isStatusCurrently(String status) {
        return this.status.equals(status);
    }

    public boolean isBusy() {
        return isBusy;
    }

    public Profession getProfession() {
        return profession;
    }

    public int getDurationOfWork() {
        return durationOfWork;
    }

    public int getDurationOfWork1() {
        return durationofWork1;
    }

    public boolean getHasChangedProfession() {
        return hasChangedProfession;
    }

    public GameTime getChangeProfessionTime() {
        return changeProfessionTime;
    }

    public GameTime getTimeNotDumped() {
        return timeNotDumped;
    }

    // SETTERS
    public void setHealth(int health) {
        this.health = health;
        if (this.health > 100) this.health = 100;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
        if (this.hunger > 100) this.hunger = 100;
    }

    public void setMood(int mood) {
        this.mood = mood;
        if (this.mood > 100) this.mood = 100;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void resetStatus() {
        setStatus("Idle");
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom.removeSim(this);
        this.currentRoom = room;
        this.currentRoom.addSim(this);
        collisionHandler = new CollisionHandler(this, room);
        interactionHandler = new InteractionHandler(this, room);
    }

    public void changeIsBusyState() {
        this.isBusy = !this.isBusy;
    }

    public void interact() {
        interactionHandler.interact();
    }

    public void setMoney (int money) {
        this.money = money;
    }

    public void setDurationOfWork(int duration) {
        durationOfWork = duration;
    }

    public void setDurationOfWork1(int duration) {
        durationofWork1 = duration;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setChangeProfessionTime(GameTime time) {
        changeProfessionTime = time;
    }

    public void setTimeNotDumped(GameTime time) {
        timeNotDumped = time;
    }

    // OTHERS
    public void changeProfession(Profession profession, GameTime time) {
        if (durationOfWork >= 720) {
            if (profession.getName().equals("Clown")) {
                int changeProfessionCharge = 1/2 * 15;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Chef")) {
                int changeProfessionCharge = 1/2 * 30;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Police")) {
                int changeProfessionCharge = 1/2 * 35;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Programmer")) {
                int changeProfessionCharge = 1/2 * 45;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Doctor")) {
                int changeProfessionCharge = 1/2 * 50;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Barista")) {
                int changeProfessionCharge = 1/2 * 20;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Model")) {
                int changeProfessionCharge = 1/2 * 45;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Dentist")) {
                int changeProfessionCharge = 1/2 * 40;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            else if (profession.getName().equals("Security")) {
                int changeProfessionCharge = 1/2 * 15;
                if(getMoney() >= changeProfessionCharge) {
                    this.profession = profession;
                    setMoney(getMoney() - changeProfessionCharge);

                }
            }
            setProfession(profession);
            hasChangedProfession = true;
            setChangeProfessionTime(time);
        }
        
    }

    

    
    public void draw(Graphics2D g) {
        // Draw the appropriate image based on the direction the sim is facing
        int imageIndex = getDirection();
        if (!isBusy && isMoving() && !currentRoom.isEditingRoom()) {
            imageIndex += (int) ((getDirection() + (System.currentTimeMillis() / 250) % 2) + 4);
        }

        if (isStatusCurrently("Idle")) {
            g.drawImage(images[imageIndex], getX(), getY(), null);
        }
    }

    public void update() {
        if (!isStatusCurrently("Idle") || currentRoom.isEditingRoom()) {
            return;
        }
        
        if (isMoving() && !isBusy) {
            move(collisionHandler, interactionHandler);
        }
    }

    // ONLY FOR DEBUGGING
    public void drawCollisionBox(Graphics2D g) {
        // Draw the sim collision area as a red rectangle
        g.setColor(new Color(255, 0, 0, 64)); // Transparent red color
        g.fillRect(getX() + 8, getY() + 15, getWidth() - 16, getHeight() - 16);
    }

    public void drawInteractionRange(Graphics2D g) {
        // Draw the interaction range as a yellow rectangle
        g.setColor(new Color(255, 255, 0, 128)); // Transparent yellow color
        g.fillRect(interactionHandler.getX(), interactionHandler.getY(), interactionHandler.getWidth(), interactionHandler.getHeight());
    }
}