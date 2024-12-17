package org.example.eiscuno.model.sound.music;

import org.example.eiscuno.model.sound.Sound;

/**
 * The {@code MusicGame} class implements the {@code IMusic} interface
 * to provide audio playback functionalities for various game events in UNO.
 * <p>
 * This class uses the {@code Sound} class to load and play specific sound effects
 * associated with game actions, such as playing a card, drawing a card, or hovering
 * over buttons. Each method corresponds to a unique game event sound.
 *
 * @author Samuel Arenas Valencia
 * @author Maria Juliana Saavedra
 * @author Juan Esteban Rodriguez
 * @version 1.0
 */
public class MusicGame implements IMusic{

    private static Sound sound;

    public MusicGame(){
        sound = new Sound();
    }

    /**
     * Constructs a new {@code MusicGame} instance.
     * <p>
     * Initializes the {@code Sound} object to manage audio clips.
     */

    /**
     * Plays the sound effect for the blue card.
     */
    @Override
    public void playBlueSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/blue_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the red card.
     */
    @Override
    public void playRedSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/red_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the yellow card.
     */

    @Override
    public void playYellowSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/yellow_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the green card.
     */
    @Override
    public void playGreenSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/green_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the draw two card.
     */

    @Override
    public void playDrawTwoSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/draw_two_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the draw four card.
     */

    @Override
    public void playDrawFourSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/draw_four_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the reverse card.
     */

    @Override
    public void playReverseSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/reverse_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the skip card.
     */

    @Override
    public void playSkipSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/skip_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the skip card.
     */

    @Override
    public void playWildCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/wild_card_sound.WAV");
        sound.playSound();

    }

    /**
     * Plays the sound effect for the sing "UNO".
     */

    @Override
    public void playUnoSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/win_sound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

    /**
     * Plays the sound effect for the dale card.
     */

    @Override
    public void playDaleCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/dale_card_sound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

    /**
     * Plays the sound effect for the button hover.
     */

    @Override
    public void playBtnHoverSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/buttonSound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

    /**
     * Plays the sound effect for the draw card.
     */

    @Override
    public void playDrawCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/draw_card_sound.WAV");
        sound.playSound();
    }

    /**
     * Plays the sound effect for the put card.
     */

    @Override
    public void playPutCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/put_card_sound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }


}
