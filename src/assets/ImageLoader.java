package src.assets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.main.Consts;

public class ImageLoader {
    private static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(Consts.SCALED_TILE * width, Consts.SCALED_TILE * height, image.getType());
        Graphics2D g = scaledImage.createGraphics();

        g.drawImage(image, 0, 0, Consts.SCALED_TILE * width, Consts.SCALED_TILE * height, null);
        g.dispose();
        return scaledImage;
    }

    public static BufferedImage readImage(String folder, String fileName, int width, int height, boolean scaled) {
        BufferedImage image;

        try {
            image = ImageIO.read(new File("./src/assets/" + folder + "/" + fileName + ".png"));
            if (scaled) {
                image = scaleImage(image, width, height);
            }
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage readImage(String folder, String subfolder, String fileName, int width, int height, boolean scaled) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("./src/assets/" + folder + "/" + subfolder + "/" + fileName + ".png"));
            if (scaled) {
                image = scaleImage(image, width, height);
            }
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage rotate90Clockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotated = new BufferedImage(height, width, image.getType());
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotated.setRGB(height - 1 - y, x, image.getRGB(x, y));
            }
        }
        return rotated;
    }

    public static BufferedImage[] loadMainMenu() {
        BufferedImage[] images = new BufferedImage[7];

        images[0] = ImageLoader.readImage("mockup", "main menu", "menu_bg", 0, 0, false);
        images[1] = ImageLoader.readImage("mockup", "main menu", "title", 0, 0, false);
        images[2] = ImageLoader.readImage("mockup", "main menu", "menu_button_bg", 0, 0, false);
        images[3] = ImageLoader.readImage("mockup", "main menu", "start_button", 0, 0, false);
        images[4] = ImageLoader.readImage("mockup", "main menu", "load_button", 0, 0, false);
        images[5] = ImageLoader.readImage("mockup", "main menu", "help_button", 0, 0, false);
        images[6] = ImageLoader.readImage("mockup", "main menu", "exit_button", 0, 0, false);

        return images;
    }
    
    public static BufferedImage[] loadSim() {
        BufferedImage[] images = new BufferedImage[12];

        images[0] = readImage("sim", "idle_up", 1, 1, true);
        images[1] = readImage("sim", "idle_right", 1, 1, true);
        images[2] = readImage("sim", "idle_down", 1, 1, true);
        images[3] = readImage("sim", "idle_left", 1, 1, true);
        images[4] = readImage("sim", "walk_up_1", 1, 1, true);
        images[5] = readImage("sim", "walk_up_2", 1, 1, true);
        images[6] = readImage("sim", "walk_right_1", 1, 1, true);
        images[7] = readImage("sim", "walk_right_2", 1, 1, true);
        images[8] = readImage("sim", "walk_down_1", 1, 1, true);
        images[9] = readImage("sim", "walk_down_2", 1, 1, true);
        images[10] = readImage("sim", "walk_left_1", 1, 1, true);
        images[11] = readImage("sim", "walk_left_2", 1, 1, true);
        return images;
    }

    public static BufferedImage[] loadBeds() {
        BufferedImage[] images = new BufferedImage[6];

        images[0] = readImage("beds", "bed_idle", 4, 1, true);
        images[1] = readImage("beds", "bed_occupied", 4, 1, true);
        return images;
    }

    public static BufferedImage loadWood() {
        BufferedImage image = readImage("tiles", "wood", 1, 1, true);
        return image;
    }

    public static BufferedImage loadMockup() {
        try {
            BufferedImage image = ImageIO.read(new File("./src/assets/mockup/gameLayoutMockup.png"));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static BufferedImage loadMenuMockup(){
        try {
            BufferedImage image = ImageIO.read(new File("./src/assets/mockup/menu_display.png"));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage loadStartButton(){
        try {
            BufferedImage image = ImageIO.read(new File("./src/assets/mockup/start_button.png"));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage loadHelpButton(){
        try {
            BufferedImage image = ImageIO.read(new File("./src/assets/mockup/help_button.png"));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage loadExitButton(){
        try {
            BufferedImage image = ImageIO.read(new File("./src/assets/mockup/exit_button.png"));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage loadLoadButton(){
        try {
            BufferedImage image = ImageIO.read(new File("./src/assets/mockup/load_button.png"));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage[] loadDoor() {
        BufferedImage[] images = new BufferedImage[4];
        
        images[0] = readImage("tiles", "door", 1, 1, true);
        images[1] = rotate90Clockwise(images[0]);
        images[2] = rotate90Clockwise(images[1]);
        images[3] = rotate90Clockwise(images[2]);

        return images;
    }

    public static BufferedImage[] loadWorld() {
        BufferedImage[] images = new BufferedImage[10];

        images[0] = readImage("tiles", "grass", 1, 1, false);
        images[1] = readImage("tiles", "house", "house", 1, 1, false);
        images[2] = readImage("tiles", "cursor", 1, 1, false);
        images[3] = readImage("tiles", "house", "unadded_house", 1, 1, false);
        images[4] = readImage("tiles", "house", "selected_house", 1, 1, false);
        images[5] = readImage("tiles", "house", "selected_house_occupied", 1, 1, false);
        images[6] = readImage("tiles", "quarter_arrow", "up", 1, 1, false);
        images[7] = readImage("tiles", "quarter_arrow", "left", 1, 1, false);
        images[8] = readImage("tiles", "quarter_arrow", "down", 1, 1, false);
        images[9] = readImage("tiles", "quarter_arrow", "right", 1, 1, false);
        
        return images;
    }

    public static BufferedImage[] loadArrows() {
        BufferedImage[] images = new BufferedImage[4];

        images[0] = readImage("tiles", "arrow", 1, 1, false);
        images[1] = rotate90Clockwise(images[0]);
        images[2] = rotate90Clockwise(images[1]);
        images[3] = rotate90Clockwise(images[2]);
        return images;
    }

    public static BufferedImage testSimColor(float hue) {
        Color redColor = new Color(215, 0, 20); // red color
        Color greenColor = new Color(0, 254, 10); // green color
        Color newShirtColor = new Color(0, 255, 0); // purple color
        Color newHairColor = new Color(87, 52, 37); // brown color
        
        BufferedImage image;
        float[] redHsb = new float[3];
        float[] greenHsb = new float[3];
        float[] newShirtHsb = new float[3];
        float[] newHairHsb = new float[3];
        float hueDiff;

        Color.RGBtoHSB(redColor.getRed(), redColor.getGreen(), redColor.getBlue(), redHsb);
        Color.RGBtoHSB(greenColor.getRed(), greenColor.getGreen(), greenColor.getBlue(), greenHsb);
        Color.RGBtoHSB(newShirtColor.getRed(), newShirtColor.getGreen(), newShirtColor.getBlue(), newShirtHsb);
        Color.RGBtoHSB(newHairColor.getRed(), newHairColor.getGreen(), newHairColor.getBlue(), newHairHsb);
    
        image = readImage("sim", "sim_down", 1, 1, true);
    
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                if ((rgb >> 24) == 0x00) continue; // if pixel is transparent, skip color transformation

                Color pixelColor = new Color(rgb);
    
                // Check if the pixel color is within the range of red hues
                float[] pixelHsb = new float[3];
                Color.RGBtoHSB(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelHsb);
                hueDiff = Math.abs(pixelHsb[0] - redHsb[0]);

                if (hueDiff <= 0.1 || hueDiff >= 0.9) {
                    // Keep the saturation and brightness values of the pixel, but change its hue to the new hue
                    newShirtHsb[0] = hue;
                    newShirtHsb[1] = pixelHsb[1]; // keep saturation value
                    newShirtHsb[2] = pixelHsb[2]; // keep brightness value
                    Color newPixelColor = new Color(Color.HSBtoRGB(newShirtHsb[0], newShirtHsb[1], newShirtHsb[2]));
    
                    image.setRGB(x, y, newPixelColor.getRGB());
                }

                hueDiff = Math.abs(pixelHsb[0] - greenHsb[0]);
                if (hueDiff <= 0.1 || hueDiff >= 0.9) {
                    // Keep the saturation and brightness values of the pixel, but change its hue to the new hue
                    newHairHsb[1] = pixelHsb[1]; // keep saturation value
                    newHairHsb[2] = pixelHsb[2]; // keep brightness value
                    Color newPixelColor = new Color(Color.HSBtoRGB(newHairHsb[0], newHairHsb[1], newHairHsb[2]));
    
                    image.setRGB(x, y, newPixelColor.getRGB());
                }
            }
        }
        return image;
    }
}
