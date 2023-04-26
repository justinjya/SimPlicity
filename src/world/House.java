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

    public House(int x, int y, World world, Sim owner) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.owner = owner;
        world.setMap(x, y, 1);
    }
    
    public House(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        world.setMap(x, y, 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Sim getOwner(){
        return owner;
    }
}
