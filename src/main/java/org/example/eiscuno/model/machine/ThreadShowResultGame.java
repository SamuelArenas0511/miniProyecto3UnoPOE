package org.example.eiscuno.model.machine;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;
import org.example.eiscuno.model.command.InvokerCommand;
import org.example.eiscuno.model.command.specific_commads.ShowResult;
import org.example.eiscuno.model.command.specific_commads.UpdateCardsHumanPlayer;

public class ThreadShowResultGame extends Thread{
    private final GridPane gridPaneCardsMachine;
    private final GridPane gridPaneCardsPlayer;
    private final GameUnoController gameUnoController;
    public volatile boolean running = true;
    private final ThreadPlayMachine threadPlayMachine;

    public ThreadShowResultGame(GridPane gridPaneCardsMachine, GridPane gridPaneCardsPlayers, GameUnoController gameUnoController, ThreadPlayMachine threadPlayMachine) {
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        this.gridPaneCardsPlayer = gridPaneCardsPlayers;
        this.threadPlayMachine = threadPlayMachine;
        this.gameUnoController = gameUnoController;
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
            if (cardsHumanPlayer == 0) {
                threadPlayMachine.stopThread();
                Platform.runLater(() -> new InvokerCommand(new ShowResult(gameUnoController,true)).invoke());
                stopThread();
            } else if (cardsMachinePlayer == 0) {
                threadPlayMachine.stopThread();
                Platform.runLater(() -> new InvokerCommand(new ShowResult(gameUnoController,true)).invoke());
                stopThread();
            }
        });
    }

    public void stopThread() {
        running = false;
    }

}
