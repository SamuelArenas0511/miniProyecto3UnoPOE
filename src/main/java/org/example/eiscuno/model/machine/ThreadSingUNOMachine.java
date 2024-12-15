package org.example.eiscuno.model.machine;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.command.Command;
import org.example.eiscuno.model.command.UpdateCardsHumanPlayer;
import org.example.eiscuno.model.game.GameUno;
import org.example.eiscuno.model.player.Player;


public class ThreadSingUNOMachine implements Runnable{
    private final Player player;
    private final Player machine;
    private final GameUno gameUno;
    private final Command updateCardsHumanPlayer;

    public ThreadSingUNOMachine(Player player, Player machine, GameUno gameUno, GridPane gridPaneCardsPlayer, GameUnoController gameUnoController) {
        this.player = player;
        this.machine = machine;
        this.gameUno = gameUno;
        updateCardsHumanPlayer = new UpdateCardsHumanPlayer(gameUnoController);
    }

    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hasOneCardTheHumanPlayer();
            hasOneCardTheMachinePlayer();
        }
    }

    private void hasOneCardTheHumanPlayer(){
        if((player.getCardsPlayer().size() == 1)&&(!gameUno.isPlayerSingUno())){
            System.out.println("UNO (maquina le canta uno al jugador)");
            gameUno.eatCard(player,1);
            Platform.runLater(updateCardsHumanPlayer::execute);
            System.out.println("jugador come una carta");
        }
    }

    private void hasOneCardTheMachinePlayer(){
        if((machine.getCardsPlayer().size() == 1)&&(!gameUno.isPlayerSingUno())&&(!gameUno.isMachineSingUno())){
            System.out.println("UNO (canta maquina)");
            gameUno.setMachineSingUno(true);
        }
    }

}