package org.example.eiscuno.model.machine;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.command.Command;
import org.example.eiscuno.model.command.UpdateCardsHumanPlayer;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ThreadPlayMachine extends Thread {
    private final Player machinePlayer;
    private final Player humanPlayer;
    private final ImageView tableImageView;
    private final GameUno gameUno;
    private final GridPane gridPaneCardsMachine;
    private volatile boolean hasPlayerPlayed;
    private final Command updateCardsHumanPlayer;
    private volatile boolean running = true;
    private boolean startGame = false;

    public ThreadPlayMachine(Table table, Player machinePlayer, Player humanPlayer, ImageView tableImageView, GameUno gameUno, GridPane gridPaneCardsMachine, GameUnoController gameUnoController) {
        this.machinePlayer = machinePlayer;
        this.humanPlayer = humanPlayer;
        this.tableImageView = tableImageView;
        this.gameUno = gameUno;
        this.hasPlayerPlayed = false;
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        updateCardsHumanPlayer = new UpdateCardsHumanPlayer(gameUnoController);
    }

    public void run() {
        while (true){
            if(hasPlayerPlayed && running){
                try{
                    Thread.sleep(3000);
                    putCardOnTheTable();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void putCardOnTheTable() throws InterruptedException {
        Card card = null;
        int counter = 0;
        for (Card iter : machinePlayer.getCardsPlayer()) {
            if (gameUno.isCardPlayable(iter)) {
                card = iter;
                break;
            }
            counter++;
        }
        if (card == null) {
            System.out.println("La maquina comio carta");
            gameUno.eatCard(machinePlayer, 1);
            hasPlayerPlayed = false;
            Platform.runLater(() -> updateCardsHumanPlayer.setStyleTurnPlayer(true));
            Platform.runLater(() -> updateCardsHumanPlayer.setAnimationTakeCard(false,1));
        } else {
            machinePlayer.removeCard(counter);
            gameUno.playCard(card);
            tableImageView.setImage(card.getImage());
            Platform.runLater(this::printCardsMachinePlayer);

            if(gameUno.isEspecialCard(card)){
                Thread.sleep(1000);
                playEspecialCard(humanPlayer, card);
                Platform.runLater(updateCardsHumanPlayer::execute);
                hasPlayerPlayed = true;
            }else{
                Platform.runLater(() -> updateCardsHumanPlayer.setStyleTurnPlayer(true));
                hasPlayerPlayed = false;
            }
        }
    }

    public void playEspecialCard(Player player, Card card) throws InterruptedException {
        if(card.getType().equals("FOUR_WILD")) {;
            Platform.runLater(() -> updateCardsHumanPlayer.setAnimationTakeCard(true,4));
            Thread.sleep(1000);
            gameUno.eatCard(player, 4);
            getRandomColor();
        }else if(card.getType().equals("TWO_WILD")) {
            Platform.runLater(() -> updateCardsHumanPlayer.setAnimationTakeCard(true,2));
            Thread.sleep(1000);
            gameUno.eatCard(player, 2);
        }else if (card.getType().equals("WILD")) {
            getRandomColor();

        }
    }

    private void getRandomColor() {
        String[] colors = {"BLUE", "RED", "YELLOW", "GREEN"};
        Random random = new Random();
        String color = colors[random.nextInt(colors.length)];
        switch (color) {
            case "BLUE" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, blue, 20, 0, 0, 0)");
            case "RED" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0, 0, 0)");
            case "YELLOW" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 20, 0, 0, 0)");
            case "GREEN" -> tableImageView.setStyle("-fx-effect: dropshadow(gaussian, green, 20, 0, 0, 0)");
        }
        System.out.println("la maquina acaba de escoger el color: "+ color);
        gameUno.setColorChoose(color);
    }

    /**
     * Prints the machine player's cards on the grid pane.
     */
    public void printCardsMachinePlayer() {
        tableImageView.setStyle(null);
        this.gridPaneCardsMachine.getChildren().clear();
        Card[] currentVisibleCardsMachinePlayer = this.gameUno.getCurrentVisibleCardsMachinePlayer(0);
        for (int i = 0; i < currentVisibleCardsMachinePlayer.length; i++) {
            ImageView cardImageView = new ImageView(String.valueOf(getClass().getResource("/org/example/eiscuno/cards-uno/card_uno.png")));
            if(startGame) {
                setAnimation(i, cardImageView);
            }else{
                this.gridPaneCardsMachine.add(cardImageView, i, 0);
            }
        }
        startGame = false;
    }

    private void setAnimation(int finalI, ImageView cardImageView) {
        PauseTransition delay = new PauseTransition(Duration.seconds( finalI * 0.5));
        delay.setOnFinished(event -> {
            gridPaneCardsMachine.add(cardImageView, finalI, 0);
            animateDealCard(cardImageView);
        });
        delay.play();
    }

    private void animateDealCard(ImageView cardImageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.4), cardImageView);
        scaleTransition.setFromX(1.5);
        scaleTransition.setFromY(1.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }


    public void setHasPlayerPlayed(boolean hasPlayerPlayed) {
        this.hasPlayerPlayed = hasPlayerPlayed;
    }

    public boolean isHasPlayerPlayed(){
        return this.hasPlayerPlayed;
    }
    public void stopThread() {
        running = false;
    }

}