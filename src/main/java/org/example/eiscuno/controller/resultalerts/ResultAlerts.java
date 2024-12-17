package org.example.eiscuno.controller.resultalerts;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.eiscuno.model.sound.Sound;
import org.example.eiscuno.model.sound.music.MusicGame;
import org.example.eiscuno.view.GameUnoStage;
import org.example.eiscuno.view.WelcomeGameUnoStage;

import java.io.IOException;
import java.util.Objects;

public class ResultAlerts {
    public static void winAlert(Sound sound, String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        ButtonType customButton = new ButtonType("Regresar al inicio", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(customButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = alert.getDialogPane().getScene();
        scene.setFill(Color.TRANSPARENT);


        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ResultAlerts.class.getResourceAsStream("/org/example/eiscuno/images/win_image.png")))));
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(ResultAlerts.class.getResource("/org/example/eiscuno/css/alertCss.css")).toExternalForm());

        Text congratulatoryText = new Text(title);
        Text messageText = new Text(message);

        congratulatoryText.setFill(Color.rgb(80, 171, 68));
        messageText.setFill(Color.rgb(52, 52, 52));
        congratulatoryText.setStyle("-fx-font-weight: bold");

        TextFlow textFlow = new TextFlow(congratulatoryText,messageText);
        textFlow.setStyle("-fx-padding: 100 20 20 0;");

        alert.getDialogPane().setContent(textFlow);

        alert.showAndWait();

        GameUnoStage.deleteInstance();
        sound.stopSound();
        try {
            WelcomeGameUnoStage.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LoseAlert(Sound loseSound,String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        ButtonType customButton = new ButtonType("Regresar al inicio", ButtonBar.ButtonData.OK_DONE);

        alert.getButtonTypes().setAll(customButton);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = alert.getDialogPane().getScene();
        scene.setFill(Color.TRANSPARENT);


        alert.setGraphic(new ImageView(new Image(Objects.requireNonNull(ResultAlerts.class.getResourceAsStream("/org/example/eiscuno/images/lose_image.png")))));
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(ResultAlerts.class.getResource("/org/example/eiscuno/css/alertCss.css")).toExternalForm());

        Text congratulatoryText = new Text(title);
        Text messageText = new Text(message);

        congratulatoryText.setFill(Color.rgb(246, 55, 55));
        messageText.setFill(Color.rgb(52, 52, 52));
        congratulatoryText.setStyle("-fx-font-weight: bold");

        TextFlow textFlow = new TextFlow(congratulatoryText,messageText);
        textFlow.setStyle("-fx-padding: 100 20 20 0;");

        alert.getDialogPane().setContent(textFlow);
        alert.getDialogPane().setStyle("-fx-border-color: #F63737;");

        alert.showAndWait();

        GameUnoStage.deleteInstance();
        loseSound.stopSound();
        try {
            WelcomeGameUnoStage.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
