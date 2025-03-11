package org.example;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.InputStream;

public class Sound {
    private Clip clip;

    public void playBackgroundMusic() {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/Sound/nhacnen.wav");
            if (audioSrc != null) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioSrc);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void setVolume(int volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum();
            float max = gainControl.getMaximum();
            float range = max - min;
            float gain = min + (range * ((volume) / 100.0f));
            gainControl.setValue(gain);
        }
    }
}