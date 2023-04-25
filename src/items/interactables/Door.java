package src.items.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Room;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Door extends Interactables {
    private Room leadsIntoRoom;

    private BufferedImage[] images;

    public Door(int imageIndex, GameTime time) {
        super("Door", "Visit another room", imageIndex, Consts.PLAY_ARENA_X_LEFT + (Consts.SCALED_TILE * 4), Consts.PLAY_ARENA_Y_UP, 1, 1, time);

        this.images = ImageLoader.loadDoor();

        // Update the bounds based on the image index
        switch (imageIndex) {
            case 0:
                getBounds().setBounds(getX(), getY(), Consts.SCALED_TILE, 24);
                break;
            case 1:
                getBounds().setBounds(getX() + (Consts.SCALED_TILE - 24), getY(), 24, Consts.SCALED_TILE);
                break;
            case 2:
                getBounds().setBounds(getX(), getY() + (Consts.SCALED_TILE - 24), Consts.SCALED_TILE, 24);
                break;
            case 3:
                getBounds().setBounds(getX(), getY(), 24, Consts.SCALED_TILE);
                break;
            default:
                break;
        }
    }

    public Door(int imageIndex, Room room, GameTime time, boolean flip) {
        super("Door", "Visit another room", imageIndex, Consts.PLAY_ARENA_X_LEFT + (Consts.SCALED_TILE * 4), Consts.PLAY_ARENA_Y_UP, 1, 1, time);
        getBounds().setSize(64, 24);

        this.leadsIntoRoom = room;
        this.images = ImageLoader.loadDoor();

        if (flip) {
            flip();
        }
    }

    public void flip() {
        switch (getImageIndex()) {
            case 0:
                setImageIndex(2);
                setY(Consts.PLAY_ARENA_Y_DOWN);
                getBounds().setBounds(getX(), getY() + (Consts.SCALED_TILE - 24), Consts.SCALED_TILE, 24);
                break;
            case 1:
                setImageIndex(3);
                getBounds().setBounds(getX(), getY(), 24, Consts.SCALED_TILE);
                break;
            case 2:
                setImageIndex(0);
                getBounds().setBounds(getX(), getY(), Consts.SCALED_TILE, 24);
                break;
            case 3:
                setImageIndex(1);
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
    public void changeOccupied(Sim sim) {}

    @Override
    public void interact(Sim sim) {
        System.out.println("Going into another room...");
        sim.setCurrentRoom(leadsIntoRoom);
    }
}
