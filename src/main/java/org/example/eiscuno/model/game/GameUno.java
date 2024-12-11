package org.example.eiscuno.model.game;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;

import java.util.Objects;

/**
 * Represents a game of Uno.
 * This class manages the game logic and interactions between players, deck, and the table.
 */
public class GameUno implements IGameUno {

    private final Player humanPlayer;
    private final Player machinePlayer;
    private final Deck deck;
    private final Table table;
    private String colorChoose;
    private boolean playerSingUno;
    private boolean machineSingUno;

    /**
     * Constructs a new GameUno instance.
     *
     * @param humanPlayer   The human player participating in the game.
     * @param machinePlayer The machine player participating in the game.
     * @param deck          The deck of cards used in the game.
     * @param table         The table where cards are placed during the game.
     */
    public GameUno(Player humanPlayer, Player machinePlayer, Deck deck, Table table) {
        this.humanPlayer = humanPlayer;
        this.machinePlayer = machinePlayer;
        this.deck = deck;
        this.table = table;
        this.playerSingUno = false;
        this.machineSingUno = false;
    }

    public String getColorChoose() {
        return colorChoose;
    }

    public void setColorChoose(String colorChoose) {
        this.colorChoose = colorChoose;
    }

    /**
     * Starts the Uno game by distributing cards to players.
     * The human player and the machine player each receive 10 cards from the deck.
     */
    @Override
    public void startGame() {
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                humanPlayer.addCard(this.deck.takeCard());
            } else {
                machinePlayer.addCard(this.deck.takeCard());
            }
        }
        table.addCardOnTheTable(this.deck.takeCard());
    }

    /**
     * Allows a player to draw a specified number of cards from the deck.
     *
     * @param player        The player who will draw cards.
     * @param numberOfCards The number of cards to draw.
     */
    @Override
    public void eatCard(Player player, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            player.addCard(this.deck.takeCard());
        }
    }

    @Override
    public boolean isEspecialCard(Card card) {
        return Objects.equals(card.getType(), "WILD") ||
                Objects.equals(card.getType(), "FOUR_WILD") ||
                Objects.equals(card.getType(), "SKIP") ||
                Objects.equals(card.getType(), "TWO_WILD") ||
                Objects.equals(card.getType(), "RESERVE");
    }

    /**
     * Places a card on the table during the game.
     *
     * @param card The card to be placed on the table.
     */
    @Override
    public void playCard(Card card) {
        this.table.addCardOnTheTable(card);
    }

    /**
     * Handles the scenario when a player shouts "Uno", forcing the other player to draw a card.
     *
     * @param playerWhoSang The player who shouted "Uno".
     */
    @Override
    public void haveSungOne(String playerWhoSang) {
        if (playerWhoSang.equals("HUMAN_PLAYER")) {
            machinePlayer.addCard(this.deck.takeCard());
        } else {
            humanPlayer.addCard(this.deck.takeCard());
        }
    }

    /**
     * Retrieves the current visible cards of the human player starting from a specific position.
     *
     * @param posInitCardToShow The initial position of the cards to show.
     * @return An array of cards visible to the human player.
     */
    @Override
    public Card[] getCurrentVisibleCardsHumanPlayer(int posInitCardToShow) {
        int totalCards = this.humanPlayer.getCardsPlayer().size();
        int numVisibleCards = Math.min(4, totalCards - posInitCardToShow);
        Card[] cards = new Card[numVisibleCards];

        for (int i = 0; i < numVisibleCards; i++) {
            cards[i] = this.humanPlayer.getCard(posInitCardToShow + i);
        }

        return cards;
    }

    public Card[] getCurrentVisibleCardsMachinePlayer(int posInitCardToShow) {
        int totalCards = this.machinePlayer.getCardsPlayer().size();
        int numVisibleCards = Math.min(4, totalCards - posInitCardToShow);
        Card[] cards = new Card[numVisibleCards];

        for (int i = 0; i < numVisibleCards; i++) {
            cards[i] = this.machinePlayer.getCard(posInitCardToShow + i);
        }
        return cards;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the deck is empty, indicating the game is over; otherwise, false.
     */
    @Override
    public Boolean isGameOver() {
        return null;
    }

    public boolean isCardPlayable(Card card) {
        Card currentCard = table.getCurrentCardOnTheTable();
        /*
        hacer que si la carta en la mesa es una carta especial wild o four wild (las que cambian el color en el juego)
        compare el color con el atributo colorChoose y además solo compare color y ese mismo tipo para evitar bugs
         */
        if(Objects.equals(currentCard.getType(), "WILD") ||
                Objects.equals(currentCard.getType(), "FOUR_WILD")){
            return Objects.equals(card.getColor(), colorChoose) ||
                    Objects.equals(card.getType(), "WILD") ||
                    Objects.equals(card.getType(), "FOUR_WILD");
        }

        /*
        si es una carta especial skip, two wild o reserve solo compare el color y el tipo
        ya que value es null en todas las cartas especiales y estaba causando bugs
        */
        if (Objects.equals(currentCard.getType(), "SKIP") ||
                Objects.equals(currentCard.getType(), "TWO_WILD")||
                Objects.equals(currentCard.getType(), "RESERVE")) {
            return Objects.equals(card.getColor(), currentCard.getColor()) ||
                    Objects.equals(currentCard.getType(), card.getType());
        }

        //si no es una carta especial
        return Objects.equals(card.getColor(), currentCard.getColor()) ||
                Objects.equals(card.getValue(), currentCard.getValue()) ||
                Objects.equals(card.getType(), "WILD") || Objects.equals(card.getType(), "FOUR_WILD");
    }

    public boolean isPlayerSingUno() {
        return playerSingUno;
    }

    public void setPlayerSingUno(boolean playerSingUno) {
        this.playerSingUno = playerSingUno;
    }

    public boolean isMachineSingUno() {
        return machineSingUno;
    }

    public void setMachineSingUno(boolean machineSingUno) {
        this.machineSingUno = machineSingUno;
    }
}
