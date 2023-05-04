package src.main.panels;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soudnURL[] = new URL[5];

    public Sound(){
        soudnURL[0] = getClass().getResource("./assets/sound/Backsound.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soudnURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch(Exception e){

        }
    }
    
    public void play(){
        clip.start();
    }

    public void stop(){
        clip.stop();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
