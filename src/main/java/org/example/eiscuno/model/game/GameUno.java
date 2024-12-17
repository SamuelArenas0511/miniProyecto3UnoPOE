package org.example.eiscuno.model.game;

import javafx.application.Platform;
import org.example.eiscuno.controller.GameUnoController;
import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.command.InvokerCommand;
import org.example.eiscuno.model.command.specific_commads.ShowResultDeck;
import org.example.eiscuno.model.deck.Deck;
import org.example.eiscuno.model.player.Player;
import org.example.eiscuno.model.table.Table;

import java.util.Objects;

/**
 * Represents a game of Uno.
 * This class manages the game logic and interactions between players, deck, and the table.
 *  * @author Samuel Arenas Valencia
 *  * @author Maria Juliana Saavedra
 *  * @author Juan Esteban Rodriguez
 *  * @version 1.0
 */
public class GameUno extends GameAdapter {

    private final Player humanPlayer;
    private final Player machinePlayer;
    public  Deck deck;
    private final Table table;
    private String colorChoose;
    private boolean playerSingUno;
    private boolean machineSingUno;
    private GameUnoController gameUnoController;

    /**
     * Constructs a new GameUno instance.
     *
     * @param humanPlayer   The human player participating in the game.
     * @param machinePlayer The machine player participating in the game.
     * @param deck          The deck of cards used in the game.
     * @param table         The table where cards are placed during the game.
     */
    public GameUno(Player humanPlayer, Player machinePlayer, Deck deck, Table table, GameUnoController gameUnoController) {
        this.humanPlayer = humanPlayer;
        this.machinePlayer = machinePlayer;
        this.deck = deck;
        this.table = table;
        this.playerSingUno = false;
        this.machineSingUno = false;
        this.gameUnoController = gameUnoController;
    }

    public GameUno(Player humanPlayer, Player machinePlayer, Table table) {
        this.humanPlayer = humanPlayer;
        this.machinePlayer = machinePlayer;
        this.table = table;
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
        Card firstCard;
        do{
            firstCard = deck.takeCard();
        }while(isEspecialCard(firstCard));
        table.addCardOnTheTable(firstCard);
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
            if (deck.deckOfCards.size()==0){
                break;
            } else {
                player.addCard(this.deck.takeCard());
                System.out.println(deck.deckOfCards.size());
            }
        }
        if (player==humanPlayer) {
            if(isPlayerSingUno()){
                setPlayerSingUno(false);
                System.out.println("sing uno de player vuelve a false");
            }
        }else{
            if(isMachineSingUno()){
                setMachineSingUno(false);
                System.out.println("sing uno de player vuelve a false");
            }
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
    public void isGameOver(Player player) {
        if(player.getCardsPlayer().isEmpty()){
            System.out.println("Gamefinish playing");
        } else if (deck.isEmpty()) {
            System.out.println("Gamefinish deck");
            if (getHumanPlayer().getCardsPlayer().size() <getMachinePlayer().getCardsPlayer().size()) {
                Platform.runLater(() -> new InvokerCommand(new ShowResultDeck(gameUnoController, Boolean.TRUE)).invoke());
            } else if (getHumanPlayer().getCardsPlayer().size() == getMachinePlayer().getCardsPlayer().size()) {
                Platform.runLater(() -> new InvokerCommand(new ShowResultDeck(gameUnoController, null)).invoke());
            } else {
                Platform.runLater(() -> new InvokerCommand(new ShowResultDeck(gameUnoController, Boolean.FALSE)).invoke());
            }
        }
    }

    /**
     * Determines if a given card can be played on the current card on the table according to the game rules.
     * <p>
     * This method checks if a card can be placed on the table by comparing its properties with the
     * current card on the table. It handles special cases, such as WILD, FOUR_WILD, SKIP, TWO_WILD,
     * and RESERVE cards, ensuring correct comparisons to prevent bugs.
     *
     * @param card The card to be checked for playability.
     * @return A boolean value: true if the card can be played, false otherwise.
     */

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
                    Objects.equals(currentCard.getType(), card.getType()) ||
                    Objects.equals(card.getType(), "WILD") || Objects.equals(card.getType(), "FOUR_WILD");
        }

        //si no es una carta especial
        return Objects.equals(card.getColor(), currentCard.getColor()) ||
                Objects.equals(card.getValue(), currentCard.getValue()) ||
                Objects.equals(card.getType(), "WILD") || Objects.equals(card.getType(), "FOUR_WILD");
    }

    /**
     * Checks if the human player has declared "UNO".
     *
     * @return A boolean indicating whether the human player has declared "UNO".
     */
    public boolean isPlayerSingUno() {
        return playerSingUno;
    }

    /**
     * Sets the flag indicating whether the human player has declared "UNO".
     *
     * @param playerSingUno A boolean value representing if the human player declared "UNO".
     */
    public void setPlayerSingUno(boolean playerSingUno) {
        this.playerSingUno = playerSingUno;
    }

    /**
     * Checks if the machine player has declared "UNO".
     *
     * @return A boolean indicating whether the machine player has declared "UNO".
     */
    public boolean isMachineSingUno() {
        return machineSingUno;
    }

    /**
     * Sets the flag indicating whether the machine player has declared "UNO".
     *
     * @param machineSingUno A boolean value representing if the machine player declared "UNO".
     */
    public void setMachineSingUno(boolean machineSingUno) {
        this.machineSingUno = machineSingUno;
    }

    /**
     * Retrieves the human player object.
     *
     * @return The `Player` object representing the human player.
     */
    @Override
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    /**
     * Retrieves the machine player object.
     *
     * @return The `Player` object representing the machine player.
     */
    public Player getMachinePlayer(){
        return machinePlayer;
    }

    public Boolean isDeckEmpty(){
        return deck.isEmpty();
    }

    public Boolean isPlayerEmpty(Player player){
        return player.getCardsPlayer().isEmpty();
    }
}
