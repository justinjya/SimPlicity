package src.assets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import src.entities.sim.Sim;
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
    
    public static BufferedImage[] loadSim() {
        BufferedImage[] images = new BufferedImage[12];

        images[0] = readImage("sim", "sim_up", 1, 1, true);
        images[1] = readImage("sim", "sim_right", 1, 1, true);
        images[2] = readImage("sim", "sim_down", 1, 1, true);
        images[3] = readImage("sim", "sim_left", 1, 1, true);
        images[4] = readImage("sim", "sim_walk_up_1", 1, 1, true);
        images[5] = readImage("sim", "sim_walk_up_2", 1, 1, true);
        images[6] = readImage("sim", "sim_walk_right_1", 1, 1, true);
        images[7] = readImage("sim", "sim_walk_right_2", 1, 1, true);
        images[8] = readImage("sim", "sim_walk_down_1", 1, 1, true);
        images[9] = readImage("sim", "sim_walk_down_2", 1, 1, true);
        images[10] = readImage("sim", "sim_walk_left_1", 1, 1, true);
        images[11] = readImage("sim", "sim_walk_left_2", 1, 1, true);

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

    public static BufferedImage[] loadBeds() {
        BufferedImage[] images = new BufferedImage[6];

        images[0] = readImage("beds", "bed_single_idle", 4, 1, true);
        images[1] = readImage("beds", "bed_queen_idle", 4, 2, true);
        images[2] = readImage("beds", "bed_king_idle", 5, 2, true);
        images[3] = readImage("beds", "bed_single_occupied", 4, 1, true);
        images[4] = readImage("beds", "bed_queen_occupied", 4, 2, true);
        images[5] = readImage("beds", "bed_king_occupied", 5, 2, true);

        return images;
    }

    public static BufferedImage[] loadBedsIcons() {
        BufferedImage[] icons = new BufferedImage[3];

        icons[0] = readImage("inventory", "bed_single", 1, 1, false);
        icons[1] = readImage("inventory", "bed_queen", 1, 1, false);
        icons[2] = readImage("inventory", "bed_king", 1, 1, false);

        return icons;
    }

    public static BufferedImage[] loadBin() {
        BufferedImage[] images = new BufferedImage[4];

        images[0] = readImage("bin", "trash_bin_empty_on_floor", 1, 1, true);
        images[1] = readImage("bin", "trash_bin_empty", 1, 1, true);
        images[2] = readImage("bin", "trash_bin_filled", 1, 1, true);
        images[3] = readImage("bin", "trash_bin_filled_on_floor", 1, 1, true);
    
        return images;
    }

    public static BufferedImage loadBinIcon() {
        BufferedImage icon = readImage("inventory", "bin", 1, 1, false);
    
        return icon;
    }

    public static BufferedImage[] loadShower() {
        BufferedImage[] images = new BufferedImage[2];
        
        images[0] = readImage("tiles", "shower", 1, 2, true);
        images[1] = readImage("tiles", "shower_occupied", 1, 2, true);
    
        return images;
    }

    public static BufferedImage loadShowerIcon() {
        BufferedImage icon = readImage("inventory", "shower", 1, 1, false);
    
        return icon;
    }

    public static BufferedImage[] loadStoves() {
        BufferedImage[] images = new BufferedImage[4];
        
        images[0] = readImage("tiles", "gas_stove", 1, 1, true);
        images[1] = readImage("tiles", "gas_stove", 1, 1, true);
        images[2] = readImage("tiles", "electric_stove", 1, 1, true);
        images[3] = readImage("tiles", "electric_stove", 1, 1, true);
    
        return images;
    }

    public static BufferedImage[] loadStovesIcons() {
        BufferedImage[] images = new BufferedImage[2];
        
        images[0] = readImage("inventory", "gas_stove", 1, 1, false);
        images[1] = readImage("inventory", "electric_stove", 1, 1, false);
    
        return images;
    }

    public static BufferedImage[] loadTableAndChair() {
        BufferedImage[] images = new BufferedImage[2];
        
        images[0] = readImage("tiles", "table_and_chair", 3, 3, true);
        images[1] = readImage("tiles", "table_and_chair_occupied", 4, 3, true);

        return images;
    }

    public static BufferedImage loadTableAndChairIcon() {
        BufferedImage icon = readImage("inventory", "table_and_chair", 3, 3, false);
    
        return icon;
    }

    public static BufferedImage[] loadTelevision() {
        BufferedImage[] images = new BufferedImage[2];
        
        images[0] = readImage("tiles", "television", 1, 1, true);
        images[1] = readImage("tiles", "television_occupied", 2, 2, true);
    
        return images;
    }

    public static BufferedImage loadTelevisionIcon() {
        BufferedImage icon = readImage("inventory", "television", 2, 1, false);
    
        return icon;
    }

    public static BufferedImage[] loadToilet() {
        BufferedImage[] images = new BufferedImage[2];
        
        images[0] = readImage("toilet", "toilet_idle", 1, 1, true);
        images[1] = readImage("toilet", "toilet_occupied", 1, 1, true);

        return images;
    }

    public static BufferedImage loadToiletIcon() {
        BufferedImage icon = readImage("inventory", "toilet", 1, 1, false);
    
        return icon;
    }

    public static BufferedImage[] loadAquarium() {
        BufferedImage[] images = new BufferedImage[4];
        
        images[0] = readImage("tiles", "aquarium_1", 1, 1, true);
        images[1] = readImage("tiles", "aquarium_2", 1, 1, true);
        images[2] = readImage("tiles", "aquarium_1_occupied", 2, 1, true);
        images[3] = readImage("tiles", "aquarium_2_occupied", 2, 1, true);
    
        return images;
    }

    public static BufferedImage loadAquariumIcon() {
        BufferedImage icon = readImage("inventory", "aquarium", 1, 1, false);
    
        return icon;
    }

    public static BufferedImage[] loadRawFood() {
        BufferedImage[] images = new BufferedImage[8];
        
        images[0] = readImage("inventory", "gas_stove", 1, 1, false);
        images[1] = readImage("inventory", "gas_stove", 1, 1, false);
        images[2] = readImage("inventory", "gas_stove", 1, 1, false);
        images[3] = readImage("inventory", "gas_stove", 1, 1, false);
        images[4] = readImage("inventory", "gas_stove", 1, 1, false);
        images[5] = readImage("inventory", "gas_stove", 1, 1, false);
        images[6] = readImage("inventory", "gas_stove", 1, 1, false);
        images[7] = readImage("inventory", "gas_stove", 1, 1, false);
    
        return images;
    }

    public static BufferedImage[] loadBakedFood() {
        BufferedImage[] images = new BufferedImage[5];
        
        images[0] = readImage("inventory", "gas_stove", 1, 1, false);
        images[1] = readImage("inventory", "gas_stove", 1, 1, false);
        images[2] = readImage("inventory", "gas_stove", 1, 1, false);
        images[3] = readImage("inventory", "gas_stove", 1, 1, false);
        images[4] = readImage("inventory", "gas_stove", 1, 1, false);
    
        return images;
    }

    public static Color setColor(int selectedColor) {
        Color color = null;
        if (selectedColor == 0) color = Color.YELLOW;
        if (selectedColor == 1) color = Color.ORANGE;
        if (selectedColor == 2) color = new Color(215, 0, 20); // red color
        if (selectedColor == 3) color = Color.MAGENTA;
        if (selectedColor == 4) color = Color.PINK;
        if (selectedColor == 5) color = Color.BLUE;
        if (selectedColor == 6) color = Color.CYAN;
        if (selectedColor == 7) color = new Color(0, 254, 10); // green color

        return color;
    }

    public static BufferedImage simColorSelector(int selectedColor) {
        BufferedImage newImage = readImage("sim", "sim_down", 1, 1, false);
        Color oldShirtColor = new Color(215, 0, 20); // red color
        Color newShirtColor = setColor(selectedColor);

        float[] oldShirtColorHsb = new float[3];
        float[] newShirtColorHsb = new float[3];
        float hueDiff;

        Color.RGBtoHSB(oldShirtColor.getRed(), oldShirtColor.getGreen(), oldShirtColor.getBlue(), oldShirtColorHsb);
        Color.RGBtoHSB(newShirtColor.getRed(), newShirtColor.getGreen(), newShirtColor.getBlue(), newShirtColorHsb);
    
        // to change the shirt color
        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y = 0; y < newImage.getHeight(); y++) {
                int rgb = newImage.getRGB(x, y);
                if ((rgb >> 24) == 0x00) continue; // if pixel is transparent, skip color transformation

                Color pixelColor = new Color(rgb);
    
                // Check if the pixel color is within the range of hues
                float[] pixelHsb = new float[3];
                Color.RGBtoHSB(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelHsb);
                hueDiff = Math.abs(pixelHsb[0] - oldShirtColorHsb[0]);

                if (hueDiff <= 0.1 || hueDiff >= 0.9) {
                    // Keep the saturation and brightness values of the pixel, but change its hue to the new hue
                    newShirtColorHsb[1] = pixelHsb[1]; // keep saturation value
                    newShirtColorHsb[2] = pixelHsb[2]; // keep brightness value
                    Color newPixelColor = new Color(Color.HSBtoRGB(newShirtColorHsb[0], newShirtColorHsb[1], newShirtColorHsb[2]));
    
                    newImage.setRGB(x, y, newPixelColor.getRGB());
                }
            }
        }
        return newImage;
    }

    public static BufferedImage changeSimColor(BufferedImage simImage, Sim sim) {
        Color oldShirtColor = new Color(215, 0, 20); // red color
        Color newShirtColor = sim.getShirtColor();

        float[] oldShirtColorHsb = new float[3];
        float[] newShirtColorHsb = new float[3];
        float hueDiff;

        Color.RGBtoHSB(oldShirtColor.getRed(), oldShirtColor.getGreen(), oldShirtColor.getBlue(), oldShirtColorHsb);
        Color.RGBtoHSB(newShirtColor.getRed(), newShirtColor.getGreen(), newShirtColor.getBlue(), newShirtColorHsb);
    
        // to change the shirt color
        for (int x = 0; x < simImage.getWidth(); x++) {
            for (int y = 0; y < simImage.getHeight(); y++) {
                int rgb = simImage.getRGB(x, y);
                if ((rgb >> 24) == 0x00) continue; // if pixel is transparent, skip color transformation

                Color pixelColor = new Color(rgb);
    
                // Check if the pixel color is within the range of hues
                float[] pixelHsb = new float[3];
                Color.RGBtoHSB(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelHsb);
                hueDiff = Math.abs(pixelHsb[0] - oldShirtColorHsb[0]);

                if (hueDiff <= 0.05 || hueDiff >= 0.95) {
                    // Keep the saturation and brightness values of the pixel, but change its hue to the new hue
                    newShirtColorHsb[1] = pixelHsb[1]; // keep saturation value
                    newShirtColorHsb[2] = pixelHsb[2]; // keep brightness value
                    Color newPixelColor = new Color(Color.HSBtoRGB(newShirtColorHsb[0], newShirtColorHsb[1], newShirtColorHsb[2]));
    
                    simImage.setRGB(x, y, newPixelColor.getRGB());
                }
            }
        }
        return simImage;
    }
}
