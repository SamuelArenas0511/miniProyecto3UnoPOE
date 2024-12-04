package org.example.eiscuno.model.machine;

import javafx.scene.image.ImageView;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;

import java.util.ArrayList;
import java.util.Objects;

public class ThreadPlayMachine extends Thread {
    private Table table;
    private Player machinePlayer;
    private ImageView tableImageView;
    private GameUno gameUno;
    private volatile boolean hasPlayerPlayed;

    public ThreadPlayMachine(Table table, Player machinePlayer, ImageView tableImageView) {
        this.table = table;
        this.machinePlayer = machinePlayer;
        this.tableImageView = tableImageView;
        this.hasPlayerPlayed = false;
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
                hasPlayerPlayed = false;
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
            // No hay cartas jugables: el jugador máquina come una carta
            gameUno.eatCard(machinePlayer, 1);
        } else {
            // Jugar la carta encontrada
            machinePlayer.removeCard(counter); // Asegúrate de eliminar la carta jugada
            table.addCardOnTheTable(card);
            tableImageView.setImage(card.getImage());
        }
    }

    private boolean isCardPlayable(Card card) {
        Card currentCard = table.getCurrentCardOnTheTable();

        return Objects.equals(card.getColor(), currentCard.getColor()) ||
                Objects.equals(card.getValue(), currentCard.getValue()) ||
                card.getColor() == null;
    }

    public void setHasPlayerPlayed(boolean hasPlayerPlayed) {
        this.hasPlayerPlayed = hasPlayerPlayed;
    }
}