package asteroids.util;

import asteroids.Info;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * The Sound class is used to play the different sounds of this game. It is very basic and the
 * sound quality is not the best, but it works. There are many .wav files included in this
 * project, and the sounds are accessed through audio input streams.
 */
public class Sound implements Info {
    private final String fileName;
    private Clip clip;

    public Sound(String fileName) {
        this.fileName = fileName;
        resetAudioStream();
    }

    /**
     * Play the sound (it can be played once or in a loop)
     * @param loop true if this sound should be played in a loop, false otherwise.
     */
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

    /**
     * Stop this sound from playing. This method will end the sound if it is playing
     * in a loop.
     */
    public void endSound() {
        clip.stop();
        resetAudioStream();
    }

    /**
     * Resets the audio stream to the beginning so that the sound can be replayed.
     */
    private void resetAudioStream() {
        try {
            BufferedInputStream myStream = new BufferedInputStream(getClass().getResourceAsStream(fileName));
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(myStream));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by initialize(), plays the last frame of this sound.
     */
    private void playLast() {
        clip.setFramePosition(clip.getFrameLength() - 1);
        clip.start();
    }

    /**
     * The Sound functionality in java takes a while to start up, and it starts up when the first
     * sound is played. This method is called by the GUI to initialize the sound functionality
     * when the program starts, not when the game starts. Without doing this, there would be a delay
     * of a couple seconds whenever the user started a new game. Now that delay is moved to the
     * opening of the entire program.
     */
    public static void initialize() {
        Sound init = new Sound(BEAT1_SOUND_FILE);
        init.playLast();
    }
}
