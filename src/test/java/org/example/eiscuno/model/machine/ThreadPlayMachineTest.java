//package org.example.eiscuno.model.machine;
//
//import javafx.fxml.FXML;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.GridPane;
//import org.example.eiscuno.model.card.Card;
//import org.example.eiscuno.model.deck.Deck;
//import org.example.eiscuno.model.game.GameUno;
//import org.example.eiscuno.model.player.Player;
//import org.example.eiscuno.model.table.Table;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static javafx.beans.binding.Bindings.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//class ThreadPlayMachineTest {
//    @Test
//    void testIsCardPlayable_WrongColor() {
//        var table = new Table();
//        var humanPlayer = new Player("HUMAN_PLAYER");
//        var machinePlayer = new Player("MACHINE_PLAYER");
//        var tableImageView = new ImageView();
//        var deck = new Deck();
//        var gameUno = new GameUno(humanPlayer, machinePlayer, deck, table);
//        var gridPaneCardsMachine = new GridPane();
//        var gridPaneCardsPlayer = new GridPane();
//        var machine = new ThreadPlayMachine(table, machinePlayer, humanPlayer, tableImageView, gameUno, gridPaneCardsMachine, gridPaneCardsPlayer);
//
//        var currentCardOnTable = new Card("WILD", null, null, "WILD");
//        Card card = null;
//        for (Card iter : machinePlayer.getCardsPlayer()) {
//            if (gameUno.isEspecialCard(iter)) {
//                card = iter;
//                break;
//            }
//        }
//        machine.playEspecialCard(machinePlayer, currentCardOnTable);
//        System.out.println("selected color: "+ gameUno.getColorChoose());
//
//        var nonPlayableCard = new Card("BLUE", "5", "NUMBER", null);// Carta a evaluar
//
//        assertFalse(machine.isCardPlayable(nonPlayableCard));
//
//    }
//
//}