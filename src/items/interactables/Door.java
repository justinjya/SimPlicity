package src.items.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;
import src.world.Room;

public class Door extends Interactables {
    private Room leadsIntoRoom;

    private BufferedImage[] images;

    public Door(int imageIndex, Room room, GameTime time) {
        super("Door", "Visit another room", imageIndex, 0, 0, 1, 1, time);

        this.leadsIntoRoom = room;
        this.images = ImageLoader.loadDoor();

        // Update the bounds based on the image index
        switch (imageIndex) {
            case 0:
                setX(Consts.PLAY_ARENA_X_LEFT);
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX(), getY(), Consts.SCALED_TILE, 24);
                break;
            case 1:
                setX(Consts.PLAY_ARENA_X_RIGHT);
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX() + (Consts.SCALED_TILE - 24), getY(), 24, Consts.SCALED_TILE);
                break;
            case 2:
                setX(Consts.PLAY_ARENA_X_RIGHT);
                setY(Consts.PLAY_ARENA_Y_DOWN);
                getBounds().setBounds(getX(), getY() + (Consts.SCALED_TILE - 24), Consts.SCALED_TILE, 24);
                break;
            case 3:
                setX(Consts.PLAY_ARENA_X_LEFT);
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX(), getY(), 24, Consts.SCALED_TILE);
                break;
            default:
                break;
        }
    }

    public Door(Door door, Room room, GameTime time) {
        super("Door", "Visit another room", 0, 0, 0, 1, 1, time);

        this.leadsIntoRoom = room;
        this.images = ImageLoader.loadDoor();

        // Update the bounds based on the image index
        switch (door.getImageIndex()) {
            case 0:
                setImageIndex(2);
                setX(Consts.PLAY_ARENA_X_RIGHT);
                setY(Consts.PLAY_ARENA_Y_DOWN);
                getBounds().setBounds(getX(), getY() + (Consts.SCALED_TILE - 24), Consts.SCALED_TILE, 24);
                break;
            case 1:
                setImageIndex(3);
                setX(Consts.PLAY_ARENA_X_LEFT);
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX(), getY(), 24, Consts.SCALED_TILE);
                break;
            case 2:
                setImageIndex(0);
                setX(Consts.PLAY_ARENA_X_LEFT);
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX(), getY(), Consts.SCALED_TILE, 24);
                break;
            case 3:
                setImageIndex(1);
                setX(Consts.PLAY_ARENA_X_RIGHT);
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX() + (Consts.SCALED_TILE - 24), getY(), 24, Consts.SCALED_TILE);
                break;
            default:
                break;
        }
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void changeOccupied(Sim sim) {
    }

    @Override
    public void interact(Sim sim) {
        System.out.println("Going into another room...");
        sim.setCurrentRoom(leadsIntoRoom);
    }
}
