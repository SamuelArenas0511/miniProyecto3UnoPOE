package org.example.eiscuno.controller;

import javafx.fxml.FXML;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class WelcomeGameUnoController {


    public void onHandlePlayGame(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        WelcomeGameUnoStage.deleteInstance();
        GameUnoStage.getInstance();
    }
}
