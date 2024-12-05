package org.example.eiscuno.controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;
import java.io.IOException;



public class WelcomeGameUnoController {

    public Label lbContinue;

    public void initialize() {
        textContinueAnimation();
        onKeyPressedContinue();
    }

    private void onKeyPressedContinue() {
        lbContinue.setFocusTraversable(true);
        lbContinue.requestFocus();
        lbContinue.setOnKeyPressed(event -> {
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
        WelcomeGameUnoStage.deleteInstance();
        GameUnoStage.getInstance();
    }

}
