package org.example.eiscuno.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.example.eiscuno.model.sound.Sound;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;
import java.io.IOException;



public class WelcomeGameUnoController {

    public Label lbContinue;
    private Sound buttonSound;
    private Sound mainSound;

    public void initialize() {
        startMainMusic();
        textContinueAnimation();
        onKeyPressedContinue();
    }

    private void startMainMusic() {
        mainSound = new Sound();
        buttonSound = new Sound();
        buttonSound.loadSound("src/main/resources/org/example/eiscuno/sounds/buttonSound.wav");
        mainSound.loadSound("src/main/resources/org/example/eiscuno/sounds/mainMusic.wav");
        mainSound.loopSound();
        buttonSound.lowerVolume(2.1f);
    }

    private void onKeyPressedContinue() {
        lbContinue.setFocusTraversable(true);
        lbContinue.requestFocus();
        lbContinue.setOnKeyPressed(event -> {
            mainSound.stopSound();
            WelcomeGameUnoStage.deleteInstance();
            try {
                GameUnoStage.getInstance();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void textContinueAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), lbContinue);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.6);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    public void onHandlePlayGame(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        mainSound.stopSound();
        WelcomeGameUnoStage.deleteInstance();
        GameUnoStage.getInstance();
    }

    public void onHandleEnteredBtnPlay(MouseEvent mouseEvent) {
        buttonSound.playSound();
    }
}
