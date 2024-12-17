package org.example.eiscuno.model.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * The {@code Sound} class is responsible for managing audio playback,
 * including functionalities such as controlling volume and handling audio clips.
 * <p>
 * This class provides methods to manipulate audio clips, such as lowering
 * the volume or performing other audio-related operations. It makes use of
 * {@code FloatControl} for volume adjustment.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
public class Sound {
    /**
     * Clip object that holds the audio data for playing, stopping, and looping the sound.
     */
    private Clip clip;

    /**
     * Loads a sound file from the specified path.
     * The sound file is prepared for playback but not yet played.
     *
     * @param path The file path of the sound to be loaded.
     */
    public void loadSound(String path) {
        try {
            File soundFile = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the loaded sound from the beginning.
     * If no sound has been loaded, this method does nothing.
     */
    public void playSound() {
        if (clip != null) {
            clip.setFramePosition(0); // Reset to the beginning of the sound
            clip.start();
        }
    }

    /**
     * Stops the currently playing sound.
     * If no sound is playing, this method does nothing.
     */
    public void stopSound() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
        }
    }

    /**
     * Loops the loaded sound continuously.
     * If no sound has been loaded, this method does nothing.
     */
    public void loopSound() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }


    /**
     * Reduces the audio volume by a specified decrement value.
     * <p>
     * This method decreases the current volume of the audio clip by 15.0 decibels
     * using the {@code FloatControl} of the audio clip. The method checks if the
     * audio clip is not null before attempting to adjust the volume.
     *
     * @param v Unused parameter. Intended for a future implementation or flexibility.
     *          Currently, the decrement value is fixed at 15.0 decibels.
     */
    public void lowerVolume(float v){
        if (clip != null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float currentVolume = volumeControl.getValue();
            volumeControl.setValue(currentVolume - 15.0f);
        }
    }

}
