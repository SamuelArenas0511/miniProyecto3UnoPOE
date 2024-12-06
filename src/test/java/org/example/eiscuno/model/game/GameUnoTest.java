package org.example.eiscuno.model.game;

import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameUnoTest {

    @Test
    void eatCard() {
        var humanPlayer = new Player("Human");
        var machinePlayer = new Player("Machine");
        var deckPlayer = new Deck();
        var table = new Table();
        var gameUno = new GameUno(humanPlayer, machinePlayer, deckPlayer, table);

    }
}