package org.example.eiscuno.model.machine;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;
import org.example.eiscuno.model.command.UpdateCardsHumanPlayer;
import org.example.eiscuno.model.game.GameUno;

public class ThreadShowResultGame extends Thread{
    private final GridPane gridPaneCardsMachine;
    private final GridPane gridPaneCardsPlayer;
    private final Command updateCardsHumanPlayer;
    public volatile boolean running = true;
    private final ThreadPlayMachine threadPlayMachine;
    private GameUno gameUno;

    public ThreadShowResultGame(GridPane gridPaneCardsMachine, GridPane gridPaneCardsPlayers, GameUnoController gameUnoController, ThreadPlayMachine threadPlayMachine, GameUno gameUno) {
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        this.gridPaneCardsPlayer = gridPaneCardsPlayers;
        updateCardsHumanPlayer = new UpdateCardsHumanPlayer(gameUnoController);
        this.threadPlayMachine = threadPlayMachine;
        this.gameUno =gameUno;
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
                showResultGame();
                if (!running) return;
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    private void showResultGame() {
        Platform.runLater(() -> {
            int cardsHumanPlayer = gridPaneCardsPlayer.getChildren().size();
            int cardsMachinePlayer = gridPaneCardsMachine.getChildren().size();
            if (gameUno.deck.deckOfCards.isEmpty()) {
                if (cardsHumanPlayer < cardsMachinePlayer) {
                    threadPlayMachine.stopThread();
                    Platform.runLater(() -> updateCardsHumanPlayer.deckEmpty(true));
                    stopThread();
                } else if (cardsHumanPlayer == cardsMachinePlayer) {
                    threadPlayMachine.stopThread();
                    Platform.runLater(() -> updateCardsHumanPlayer.deckEmpty(null));
                    stopThread();
                } else {
                    threadPlayMachine.stopThread();
                    Platform.runLater(() -> updateCardsHumanPlayer.deckEmpty(false));
                    stopThread();
                }
            } else {
                if (cardsHumanPlayer == 0) {
                    threadPlayMachine.stopThread();
                    Platform.runLater(() -> updateCardsHumanPlayer.showResult(true));
                    stopThread();
                } else if (cardsMachinePlayer == 0) {
                    threadPlayMachine.stopThread();
                    Platform.runLater(() -> updateCardsHumanPlayer.showResult(false));
                    stopThread();
                }
            }
        });
    }

    public void stopThread() {
        running = false;
    }

}
