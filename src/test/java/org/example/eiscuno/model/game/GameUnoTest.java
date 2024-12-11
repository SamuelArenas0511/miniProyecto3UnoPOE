package org.example.eiscuno.model.game;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import org.example.eiscuno.controller.GameUnoController;
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
//    private GridPane gridPaneCardsMachine;
//    private GridPane gridPaneCardsPlayer;
//    private GameUnoController mockController;
//
//    @BeforeEach
//    void setUp() {
//        gridPaneCardsMachine = new GridPane();
//        gridPaneCardsPlayer = new GridPane();
//        mockController = new GameUnoController();
//    }
//
//    @Test
//    void testGameResultsHumanPlayerWins() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        // Simula el escenario en el que el jugador humano no tiene cartas
//        gridPaneCardsPlayer.getChildren().clear();
//        gridPaneCardsMachine.getChildren().add(new javafx.scene.control.Label("Machine Card"));
//
//        ThreadShowResultGame gameThread = new ThreadShowResultGame(gridPaneCardsMachine, gridPaneCardsPlayer, mockController);
//        gameThread.start();
//
//        // Simula tiempo de espera necesario para que el hilo procese el juego
//        latch.await();
//
//        assertFalse(gameThread.running, "El hilo debe detenerse cuando el jugador humano gane.");
//    }
}