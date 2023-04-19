package src.userInterface;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    public static void main(String[] args) {
        // membuat frame
        JFrame frame = new JFrame("Contoh Menampilkan Gambar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        String imagePath =  "./src/assets/mockup/main menu/menu_bg.png";

        // membuat label
        JLabel label = new JLabel();

        // membuat ImageIcon dari gambar
        ImageIcon icon = new ImageIcon(imagePath);

        // mengatur gambar pada label
        label.setIcon(icon);

        // menambahkan label ke frame
        frame.add(label);

        // menampilkan frame
        frame.setVisible(true);
    }
}
