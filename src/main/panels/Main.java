package src.main.panels;

import src.main.panels.Sound;

public class Main {
    public static void main(String[] args) {
        Sound sound = new Sound();
        sound.setFile(0);
        sound.play();
        sound.loop();
    }
}

