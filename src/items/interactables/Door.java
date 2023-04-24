package src.items.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.Interactables;
import src.entities.Room;
import src.entities.Sim;
import src.main.Consts;
import src.main.GameTime;

public class Door extends Interactables{
    private Room leadsIntoRoom;

    private BufferedImage[] images;

    public Door(int imageIndex, GameTime time) {
        super("Door", "Visit another room", imageIndex, Consts.PLAY_ARENA_X_LEFT + (Consts.SCALED_TILE * 4), Consts.PLAY_ARENA_Y_UP, 1, 1, time);
        getBounds().setSize(64, 24);

        this.images = ImageLoader.loadDoor();
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
                getBounds().setLocation(getX(), getY() + (Consts.SCALED_TILE - 24));
                break;
            case 1:
                setImageIndex(3);
                break;
            case 2:
                setImageIndex(0);
                break;
            case 3:
                setImageIndex(1);
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeOccupied'");
    }

    @Override
    public void interact(Sim sim) {
        System.out.println("Going into another room...");
        sim.setCurrentRoom(leadsIntoRoom);
    }
}
