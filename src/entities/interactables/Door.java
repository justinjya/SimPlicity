package src.entities.interactables;

import java.awt.image.BufferedImage;

import src.assets.ImageLoader;
import src.entities.sim.Sim;
import src.main.GamePanel;
import src.main.Consts;
import src.main.GameTime;
import src.main.ui.ActiveActionsUserInterface;
import src.world.Room;

public class Door extends Interactables {
    // Atributes
    private Room leadsIntoRoom;

    // Images of the door
    private BufferedImage[] images;

    // CONSTRUCTOR
    public Door(Room room) {
        super (
            "Door",
            "visit another room",
            2,
            4,
            5,
            1,
            1
        );

        this.leadsIntoRoom = room;
        this.images = ImageLoader.loadDoor();

        updateBounds();
    }

    public Door(Door door, Room room) {
        super (
            "Door",
            "visit another room",
            0,
            0,
            0,
            1,
            1
        );

        this.leadsIntoRoom = room;
        this.images = ImageLoader.loadDoor();

        // Update the bounds based on the image index
        switch (door.getImageIndex()) {
            case 0:
                setImageIndex(2);
                setX(door.getX());
                setY(Consts.PLAY_ARENA_Y_DOWN);
                getBounds().setBounds(getX(), getY() + (Consts.SCALED_TILE - 24), Consts.SCALED_TILE, 24);
                break;
            case 1:
                setImageIndex(3);
                setX(Consts.PLAY_ARENA_X_LEFT);
                setY(door.getY());
                getBounds().setBounds(getX(), getY(), 24, Consts.SCALED_TILE);
                break;
            case 2:
                setImageIndex(0);
                setX(door.getX());
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX(), getY(), Consts.SCALED_TILE, 24);
                break;
            case 3:
                setImageIndex(1);
                setX(Consts.PLAY_ARENA_X_RIGHT);
                setY(door.getY());
                getBounds().setBounds(getX() + (Consts.SCALED_TILE - 24), getY(), 24, Consts.SCALED_TILE);
                break;
            default:
                break;
        }
    }

    // IMPLEMENTATION OF ABSTRACT METHODS
    @Override
    public void updateBounds() {
        switch (this.getImageIndex()) {
            case 0:
                setY(Consts.PLAY_ARENA_Y_UP);
                getBounds().setBounds(getX(), getY(), Consts.SCALED_TILE, 24);
                break;
            case 1:
                setX(Consts.PLAY_ARENA_X_RIGHT);
                getBounds().setBounds(getX() + (Consts.SCALED_TILE - 24), getY(), 24, Consts.SCALED_TILE);
                break;
            case 2:
                setY(Consts.PLAY_ARENA_Y_DOWN);
                getBounds().setBounds(getX(), getY() + (Consts.SCALED_TILE - 24), Consts.SCALED_TILE, 24);
                break;
            case 3:
                setX(Consts.PLAY_ARENA_X_LEFT);
                getBounds().setBounds(getX(), getY(), 24, Consts.SCALED_TILE);
                break;
            default:
                break;
        }
    }

    @Override
    public BufferedImage getIcon() {
        return images[getImageIndex()];
    }

    @Override
    public BufferedImage getImage() {
        return images[getImageIndex()];
    }

    @Override
    public void interact(Sim sim, GameTime time) {
        if (leadsIntoRoom == null) {
            // ActiveActionsUserInterface.showActiveActions(gp);
            return;
        }

        sim.setCurrentRoom(leadsIntoRoom);
        switch (getImageIndex()) {
            case 0:
                sim.setX(this.getX());
                sim.setY(Consts.PLAY_ARENA_Y_DOWN - Consts.TILE_SIZE * 2);
                break;
            case 1:
                sim.setY(this.getY());
                sim.setX(Consts.PLAY_ARENA_X_LEFT + Consts.TILE_SIZE * 2);
                break;
            case 2:
                sim.setX(this.getX());
                sim.setY(Consts.PLAY_ARENA_Y_UP + Consts.TILE_SIZE * 2);
                break;
            case 3:
                sim.setY(this.getY());
                sim.setX(Consts.PLAY_ARENA_X_RIGHT - Consts.TILE_SIZE * 2);
            }
        sim.getInteractionHandler().update();
    }

    // OTHERS
    public void changeDoorDirection(int newX, int newY) {
        if (newX == Consts.PLAY_ARENA_X_RIGHT) {
            ((Door) this).setImageIndex(1);
        }
        if (newX == Consts.PLAY_ARENA_X_LEFT) {
            ((Door) this).setImageIndex(3);
        }
        if (newY == Consts.PLAY_ARENA_Y_UP) {
            ((Door) this).setImageIndex(0);
        }
        if (newY == Consts.PLAY_ARENA_Y_DOWN) {
            ((Door) this).setImageIndex(2);
        }
    }

    public void rotate(int x, int y) {
        if (x == Consts.PLAY_ARENA_X_LEFT && y == Consts.PLAY_ARENA_Y_UP) {
            if (getImageIndex() == 0) {
                setImageIndex(3);
            }
            else {
                setImageIndex(0);
            }
        }
        if (x == Consts.PLAY_ARENA_X_LEFT && y == Consts.PLAY_ARENA_Y_DOWN) {
            if (getImageIndex() == 2) {
                setImageIndex(3);
            }
            else {
                setImageIndex(2);
            }
        }
        if (x == Consts.PLAY_ARENA_X_RIGHT && y == Consts.PLAY_ARENA_Y_UP) {
            if (getImageIndex() == 0) {
                setImageIndex(1);
            }
            else {
                setImageIndex(0);
            }
        }
        if (x == Consts.PLAY_ARENA_X_RIGHT && y == Consts.PLAY_ARENA_Y_DOWN) {
            if (getImageIndex() == 2) {
                setImageIndex(1);
            }
            else {
                setImageIndex(2);
            }
        }
    }
}
