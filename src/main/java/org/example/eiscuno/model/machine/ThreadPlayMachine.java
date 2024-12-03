package org.example.eiscuno.model.machine;

import javafx.scene.image.ImageView;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;

import java.util.ArrayList;
import java.util.Objects;

public class ThreadPlayMachine extends Thread {
    private Table table;
    private Player machinePlayer;
    private ImageView tableImageView;
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
        Card card;
        int counter = 0;
        ArrayList<Card> machineCards = machinePlayer.getCardsPlayer();

        for (Card a : machineCards) {
            if (isCardPlayable(a)){
                card = a;
                table.addCardOnTheTable(card);
                tableImageView.setImage(card.getImage());
                machinePlayer.getCardsPlayer().remove(card);
                break;
            } else  {
                counter++;
            }

            if (counter == machineCards.size()-1){

            }
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