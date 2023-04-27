package src.world;

import src.entities.Sim;

public class House {
    // Atributes
    private Sim owner;
    private Room roomWhenEntered;

    // Location inside of the world
    private int x;
    private int y;
    private World world;

    public House(int x, int y, World world, Sim sim) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.owner = sim;
        world.setMap(x, y, 1);
    }
    
    public House(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        world.setMap(x, y, 1);
    }

    public House(int x, int y, World world, Sim sim, Room room) {
        this.x = x;
        this.y = y;
        this.world = world;
        world.setMap(x, y, 1);

        this.owner = sim;
        this.roomWhenEntered = room;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sim getOwner() {
        return owner;
    }

    public Room getRoomWhenEntered() {
        return roomWhenEntered;
    }
}
