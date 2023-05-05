package src.main.panels;

import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
    private Clip clip;
    private final URL soudnURL[] = new URL[5]; 
    private FloatControl floatControl;
    private int volumeScale = 3;
    private float volume;


    public Sound(){
        soudnURL[0] = getClass().getResource("/src/assets/sound/Backsound.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soudnURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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

    public int getVolumeScale() {
        return volumeScale;
    }

    public Sound setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
        return this;
    }
}
