package org.example.eiscuno.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.example.eiscuno.model.sound.Sound;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;
import java.io.IOException;

/**
 * This class serves as the controller for the welcome screen of the UNO game.
 * It manages the initialization of background sounds, animations, and interactions for UI elements.
 * The controller handles the transition from the welcome stage to the main game stage,
 * incorporating animations and sound effects to enhance user engagement.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */

public class WelcomeGameUnoController {

    public Label lbContinue;
    private Sound buttonSound;
    private Sound mainSound;

    /**
     * Initializes the welcome game controller.
     * Starts background music, sets up the animation effect on the "Continue" button,
     * and binds the key press event to transition to the main game stage.
     */
    public void initialize() {
        startMainMusic();
        textContinueAnimation();
        onKeyPressedContinue();
    }

    /**
     * Starts and configures background and button sound effects.
     * - Loads and loops the main background music.
     * - Sets the button sound with lower volume adjustment.
     */

    private void startMainMusic() {
        mainSound = new Sound();
        buttonSound = new Sound();
        buttonSound.loadSound("src/main/resources/org/example/eiscuno/sounds/buttonSound.WAV");
        mainSound.loadSound("src/main/resources/org/example/eiscuno/sounds/mainMusic.wav");
        mainSound.loopSound();
        buttonSound.lowerVolume(2.1f);
    }

    /**
     * Sets up the key press event on the "Continue" button.
     * When the user presses a key, it stops the main music and transitions to the main game stage.
     */
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

    /**
     * Creates a fade transition animation effect on the "Continue" button.
     * The button fades in and out in a continuous loop to draw attention.
     */
    private void textContinueAnimation() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), lbContinue);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.6);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    /**
     * Handles clicking the "Play Game" button.
     * Stops the background music and transitions to the main game stage.
     *
     * @param mouseEvent The mouse click event associated with the button interaction.
     */
    public void onHandlePlayGame(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        mainSound.stopSound();
        WelcomeGameUnoStage.deleteInstance();
        GameUnoStage.getInstance();
    }

    /**
     * Plays the button click sound effect when the mouse cursor enters the "Play" button.
     *
     * @param mouseEvent The mouse event triggered when the cursor enters the button.
     */
    public void onHandleEnteredBtnPlay(MouseEvent mouseEvent) {
        buttonSound.playSound();
    }
}
