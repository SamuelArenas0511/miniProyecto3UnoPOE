package org.example.eiscuno.model.game;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.machine.ThreadPlayMachine;
import org.example.eiscuno.model.machine.ThreadShowResultGame;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;


import static org.junit.jupiter.api.Assertions.*;
class GameUnoTest {
    private Player testHumanPlayer;
    private Player testMachinePlayer;
    private Table testTable;
    private Card testCard;
    private Card testCard2;
    private GameUno gameUno;


    @BeforeEach
    void setUp() {
        this.testHumanPlayer = new Player("HUMAN_PLAYER");
        this.testMachinePlayer = new Player("MACHINE_PLAYER");
        this.testTable = new Table();
        this.testCard = new Card("5", "RED", null); // carta puesta en la mesa
        this.testCard2 = new Card("2", "RED", null); // carta que si puede jugar el jugador
        this.gameUno = new GameUno(testHumanPlayer,testMachinePlayer,testTable);
    }

    @Test
    void testIsCardPlayable() {
        testTable.addCardOnTheTable(testCard);
        testHumanPlayer.addCard(testCard2);

        assertTrue(gameUno.isCardPlayable(testCard2));
    }

    @Test
    void testCardHasBeenPlayed() {
        testTable.addCardOnTheTable(testCard);
        testHumanPlayer.addCard(testCard2);

        if(gameUno.isCardPlayable(testCard2)){
            gameUno.playCard(testCard2);
            testHumanPlayer.removeCard(0);
        }

        assertFalse(testHumanPlayer.getCardsPlayer().contains(testCard2));
        assertEquals(testCard2,testTable.getCurrentCardOnTheTable());
    }

}