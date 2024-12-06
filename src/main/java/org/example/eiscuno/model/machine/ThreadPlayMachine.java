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
    private final int posInitCardToShow;
    private volatile boolean hasPlayerPlayed;

    public ThreadPlayMachine(Table table, Player machinePlayer, Player humanPlayer, ImageView tableImageView, GameUno gameUno, GridPane gridPaneCardsMachine, GridPane gridPaneCardsPlayer, int posInitCardToShow) {
        this.table = table;
        this.machinePlayer = machinePlayer;
        this.humanPlayer = humanPlayer;
        this.tableImageView = tableImageView;
        this.gameUno = gameUno;
        this.hasPlayerPlayed = false;
        this.gridPaneCardsMachine = gridPaneCardsMachine;
        this.gridPaneCardsPlayer = gridPaneCardsPlayer;
        this.posInitCardToShow = posInitCardToShow;
    }

    public void run() {
        while (true){
            if(hasPlayerPlayed){
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Aqui iria la logica de colocar la carta
                putCardOnTheTable();

            }
        }
    }

    private void putCardOnTheTable() {
        Card card = null;
        int counter = 0;
        for (Card iter : machinePlayer.getCardsPlayer()) {
            if (isCardPlayable(iter)) {
                card = iter;
                break;
            }
            counter++;
        }
        if (card == null) {
            System.out.println("La maquina comio carta");
            // No hay cartas jugables: el jugador máquina come una carta
            Platform.runLater(this::printCardsMachinePlayer);
            gameUno.eatCard(machinePlayer, 1);
            hasPlayerPlayed = false;
        } else {
            // Jugar la carta encontrada
            System.out.println("La maquina Jugo Carta");
            machinePlayer.removeCard(counter); // Asegúrate de eliminar la carta jugada
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

    private void playEspecialCard(Player player, Card card) {
        if(card.getType().equals("FOUR_WILD")) {;
            gameUno.eatCard(player, 4);
            card.setColor(getRandomColor());
            System.out.println("La máquina ha elegido el color: " + getRandomColor());
            System.out.println("color de la carta: " + card.getColor());

        }else if(card.getType().equals("TWO_WILD")) {
            gameUno.eatCard(player, 2);

        }else if (card.getType().equals("WILD")) {
            card.setColor(getRandomColor());
            System.out.println("La máquina ha elegido el color: " + getRandomColor());
            System.out.println("color de la carta: " + card.getColor());
        }
    }

    private String getRandomColor() {
        String[] colors = {"BLUE", "RED", "YELLOW", "GREEN"};
        Random random = new Random();
        return colors[random.nextInt(colors.length)];
    }

    private void printCardsHumanPlayer() {
        this.gridPaneCardsPlayer.getChildren().clear();
        Card[] currentVisibleCardsHumanPlayer = this.gameUno.getCurrentVisibleCardsHumanPlayer(this.posInitCardToShow);
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

    private boolean isCardPlayable(Card card) {
        Card currentCard = table.getCurrentCardOnTheTable();

        return Objects.equals(card.getColor(), currentCard.getColor()) ||
                Objects.equals(card.getValue(), currentCard.getValue()) ||
                Objects.equals(card.getType(), "WILD") || Objects.equals(card.getType(), "FOUR_WILD");
    }

    public void setHasPlayerPlayed(boolean hasPlayerPlayed) {
        this.hasPlayerPlayed = hasPlayerPlayed;
    }
}