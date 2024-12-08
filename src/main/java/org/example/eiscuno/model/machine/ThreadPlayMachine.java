package org.example.eiscuno.model.machine;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ThreadPlayMachine extends Thread {
    private final Table table;
    private final Player machinePlayer;
    private final Player humanPlayer;
    private final ImageView tableImageView;
    private final GameUno gameUno;
    private final GridPane gridPaneCardsMachine;
    private final GridPane gridPaneCardsPlayer;
    private volatile boolean hasPlayerPlayed;

    public ThreadPlayMachine(Table table, Player machinePlayer, Player humanPlayer, ImageView tableImageView, GameUno gameUno, GridPane gridPaneCardsMachine, GridPane gridPaneCardsPlayer) {
        this.table = table;
        this.machinePlayer = machinePlayer;
        this.humanPlayer = humanPlayer;
        this.tableImageView = tableImageView;
        this.gameUno = gameUno;
        this.hasPlayerPlayed = false;
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        this.gridPaneCardsPlayer = gridPaneCardsPlayer;
    }

    public void run() {
        while (true){
            if(hasPlayerPlayed){
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Aqui iria la logica de colocar la carta
                putCardOnTheTable();

            }
        }
    }

    public void putCardOnTheTable() {
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
            if(gameUno.isMachineSingUno()){
                gameUno.setMachineSingUno(false);
            }
            // No hay cartas jugables: el jugador máquina come una carta
            Platform.runLater(this::printCardsMachinePlayer);
            gameUno.eatCard(machinePlayer, 1);
            hasPlayerPlayed = false;
        } else {
            // Jugar la carta encontrada
            machinePlayer.removeCard(counter);
            gameUno.playCard(card);
            tableImageView.setImage(card.getImage());
            Platform.runLater(this::printCardsMachinePlayer);

            if(gameUno.isEspecialCard(card)){
                playEspecialCard(humanPlayer, card);
                Platform.runLater(this::printCardsHumanPlayer);
                hasPlayerPlayed = true;
            }else{
                hasPlayerPlayed = false;
            }
        }
    }

    public void playEspecialCard(Player player, Card card) {
        if(card.getType().equals("FOUR_WILD")) {;
            gameUno.eatCard(player, 4);
            getRandomColor();

        }else if(card.getType().equals("TWO_WILD")) {
            gameUno.eatCard(player, 2);

        }else if (card.getType().equals("WILD")) {
            getRandomColor();

        }
    }

    private void getRandomColor() {
        String[] colors = {"BLUE", "RED", "YELLOW", "GREEN"};
        Random random = new Random();
        String color = colors[random.nextInt(colors.length)];
        System.out.println("la maquina acaba de escoger el color: "+ color);
        gameUno.setColorChoose(color);
    }

    private void printCardsHumanPlayer() {
        this.gridPaneCardsPlayer.getChildren().clear();
        Card[] currentVisibleCardsHumanPlayer = this.gameUno.getCurrentVisibleCardsHumanPlayer(0);
        for (int i = 0; i < currentVisibleCardsHumanPlayer.length; i++) {
            Card card = currentVisibleCardsHumanPlayer[i];
            ImageView cardImageView = card.getCard();
            cardImageView.getStyleClass().add("card-image");
            this.gridPaneCardsPlayer.add(cardImageView, i, 0);
        }
    }
    /**
     * Prints the machine player's cards on the grid pane.
     */
    public void printCardsMachinePlayer() {
        this.gridPaneCardsMachine.getChildren().clear();
        Card[] currentVisibleCardsMachinePlayer = this.gameUno.getCurrentVisibleCardsMachinePlayer(0);
        for (int i = 0; i < currentVisibleCardsMachinePlayer.length; i++) {
            ImageView cardImageView = new ImageView(String.valueOf(getClass().getResource("/org/example/eiscuno/cards-uno/card_uno.png")));
            this.gridPaneCardsMachine.add(cardImageView, i, 0);
        }
    }


    public void setHasPlayerPlayed(boolean hasPlayerPlayed) {
        this.hasPlayerPlayed = hasPlayerPlayed;
    }

}