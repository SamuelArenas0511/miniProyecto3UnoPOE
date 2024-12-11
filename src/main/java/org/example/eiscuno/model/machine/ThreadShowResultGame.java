package org.example.eiscuno.model.machine;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;
import org.example.eiscuno.model.command.UpdateCardsHumanPlayer;

public class ThreadShowResultGame extends Thread{
    private final GridPane gridPaneCardsMachine;
    private final GridPane gridPaneCardsPlayer;
    private final Command updateCardsHumanPlayer;
    public volatile boolean running = true;
    private final ThreadPlayMachine threadPlayMachine;

    public ThreadShowResultGame(GridPane gridPaneCardsMachine, GridPane gridPaneCardsPlayers, GameUnoController gameUnoController, ThreadPlayMachine threadPlayMachine) {
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        this.gridPaneCardsPlayer = gridPaneCardsPlayers;
        updateCardsHumanPlayer = new UpdateCardsHumanPlayer(gameUnoController);
        this.threadPlayMachine = threadPlayMachine;
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(100);
                showResultGame();
                if (!running) break;
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
            if (cardsHumanPlayer == 0) {
                System.out.println("pase");
                threadPlayMachine.stopThread();
                Platform.runLater(() -> updateCardsHumanPlayer.showResult(true));
                stopThread();
            } else if (cardsMachinePlayer == 0) {
                System.out.println("pase 2");
                threadPlayMachine.stopThread();
                Platform.runLater(() -> updateCardsHumanPlayer.showResult(false));
                stopThread();
            }
        });
    }

    public void stopThread() {
        running = false;
    }

}
