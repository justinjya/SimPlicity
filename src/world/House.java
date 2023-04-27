package src.world;

import src.entities.Sim;

public class House {
    // Atributes
    private String name;
    private Sim owner;
    private Room roomWhenEntered;

    // Location inside of the world
    private int x;
    private int y;
    private World world;

    public House(int x, int y, World world, Sim sim, Room room) {
        // Atributes
        this.name = sim.getName() + "'s House";
        this.owner = sim;
        this.roomWhenEntered = room;

        // Locatoin inside of the world
        this.x = x;
        this.y = y;
        this.world = world;
        world.setMap(x, y, 1);

    }

    public String getName() {
        return name;
    }

    public Sim getOwner() {
        return owner;
    }

    public Room getRoomWhenEntered() {
        return roomWhenEntered;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
