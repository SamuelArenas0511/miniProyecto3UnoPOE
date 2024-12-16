package org.example.eiscuno.model.sound.music;

import org.example.eiscuno.model.sound.Sound;

public class MusicGame implements IMusic{

    private Sound sound;

    public MusicGame(){
        sound = new Sound();
    }

    @Override
    public void playBlueSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/main_game_sound.WAV");
        sound.loopSound();
    }

    @Override
    public void playRedSound() {

    }

    @Override
    public void playYellowSound() {

    }

    @Override
    public void playGreenSound() {

    }

    @Override
    public void playDrawTwoSound() {

    }

    @Override
    public void playDrawFourSound() {

    }

    @Override
    public void playReverseSound() {

    }

    @Override
    public void playSkipSound() {

    }

    @Override
    public void playSelectCardSound() {
        sound.loadSound("src/main/resources/org/example/eiscuno/sounds/sounds-game/select_card_sound.WAV");
        sound.playSound();
    }

    @Override
    public void playWildCardSound() {

    }

    @Override
    public void playWinSound() {

    }

    @Override
    public void playDaleCardSound() {

    }

}
