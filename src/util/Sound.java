package util;

import game_info.Info;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import java.io.IOException;

public class Sound implements Info {
    String fileName;
    Clip clip;

    public Sound(String fileName) {
        this.fileName = fileName;
        resetAudioStream();
    }

    public void playSound(boolean loop) {
        if (!(clip.isRunning() && loop)) {
            resetAudioStream();
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }
    }

    public void endSound() {
        clip.stop();
        resetAudioStream();
    }

    private void resetAudioStream() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(fileName)));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playLast() {
        clip.setFramePosition(clip.getFrameLength() - 1);
        clip.start();
    }

    public static void initialize() {
        Sound init = new Sound(BEAT1_SOUND_FILE);
        init.playLast();
    }
}
