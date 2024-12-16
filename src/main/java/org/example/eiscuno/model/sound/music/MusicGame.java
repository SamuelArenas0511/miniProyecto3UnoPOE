package org.example.eiscuno.model.sound.music;

import org.example.eiscuno.model.sound.Sound;

public class MusicGame implements IMusic{

    private Sound sound;

    public MusicGame(){
        sound = new Sound();
    }

    @Override
    public void playBlueSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/blue_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playRedSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/red_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playYellowSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/yellow_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playGreenSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/green_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playDrawTwoSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/draw_two_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playDrawFourSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/draw_four_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playReverseSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/reverse_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playSkipSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/skip_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playSelectCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/select_card_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playWildCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/wild_card_sound.WAV");
        sound.playSound();

    }

    @Override
    public void playUnoSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/win_sound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

    @Override
    public void playDaleCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/dale_card_sound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

    @Override
    public void playBtnHoverSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/buttonSound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

    @Override
    public void playDrawCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/draw_card_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playPutCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/put_card_sound.WAV");
        sound.playSound();
        sound.lowerVolume(10.0f);
    }

}
