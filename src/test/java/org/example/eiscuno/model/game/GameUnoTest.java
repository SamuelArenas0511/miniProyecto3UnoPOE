package org.example.eiscuno.model.game;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.machine.ThreadPlayMachine;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link GameUno} class.
 *
 * This class contains tests to verify the functionality of the UNO game,
 * specifically for methods related to determining if a player can play a card
 * and ensuring that cards are properly placed on the table.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
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

    /**
     * Tests the {@code isCardPlayable()} method in the {@link GameUno} class.
     *
     * Verifies whether a specific card can be played according to the game's rules.
     * Adds a card to the table and checks if the human player can play the testCard2 card.
     * Asserts that the method correctly identifies if the card is playable.
     */
    @Test
    void testIsCardPlayable() {
        testTable.addCardOnTheTable(testCard);
        testHumanPlayer.addCard(testCard2);

        assertTrue(gameUno.isCardPlayable(testCard2));
    }

    /**
     * Tests the {@code playCard()} method in the {@link GameUno} class.
     *
     * Checks if a card is successfully played and placed on the table.
     * After playing the card, the test verifies that:
     * - The card is no longer in the human player's hand.
     * - The current card on the table matches the card that was played.
     */
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