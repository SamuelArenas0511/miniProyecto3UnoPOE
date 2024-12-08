package org.example.eiscuno.model.machine;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.player.Player;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;

public class ThreadSingUNOMachine implements Runnable{
    private Player player;
    private Player machine;
    private GameUno gameUno;
    private GridPane gridPaneCardsPlayer;

    public ThreadSingUNOMachine(Player player, Player machine, GameUno gameUno, GridPane gridPaneCardsPlayer){
        this.player = player;
        this.machine = machine;
        this.gameUno = gameUno;
        this.gridPaneCardsPlayer = gridPaneCardsPlayer;
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep((long) (Math.random() * 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hasOneCardTheHumanPlayer();
            hasOneCardTheMachinePlayer();
        }
    }

    private void hasOneCardTheHumanPlayer(){
        if((player.getCardsPlayer().size() == 1)&&(!gameUno.isPlayerSingUno())){
            System.out.println("maquina le canta uno al jugador");
            System.out.println("UNO");
            gameUno.setMachineSingUno(true);
            gameUno.eatCard(player,1);
            Platform.runLater(this::printCardsHumanPlayer);
            System.out.println("jugador come una carta");
            gameUno.setMachineSingUno(false);
        }
    }

    private void hasOneCardTheMachinePlayer(){
        if((machine.getCardsPlayer().size() == 1)&&(!gameUno.isPlayerSingUno())){
            System.out.println("UNO");
            gameUno.setMachineSingUno(true);
        }
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
}