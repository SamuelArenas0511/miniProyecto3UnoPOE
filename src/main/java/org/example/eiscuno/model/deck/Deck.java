package org.example.eiscuno.model.deck;

import org.example.eiscuno.model.exception.emptyDeckException;
import org.example.eiscuno.model.factoryMethod.ICardFactory;
import org.example.eiscuno.model.factoryMethod.UnoCardFactory;
import org.example.eiscuno.model.unoenum.EISCUnoEnum;
import org.example.eiscuno.model.card.Card;

import java.util.Collections;
import java.util.Stack;

/**
 * Represents a deck of Uno cards.
 */
public class Deck {
    public Stack<Card> deckOfCards;
    private ICardFactory cardFactory;

    /**
     * Constructs a new deck of Uno cards and initializes it.
     */
    public Deck() {
        this.cardFactory = new UnoCardFactory();
        deckOfCards = new Stack<>();
        initializeDeck();
    }

    /**
     * Initializes the deck with cards based on the EISCUnoEnum values.
     */
    private void initializeDeck() {
        for (EISCUnoEnum cardEnum : EISCUnoEnum.values()) {
            if (cardEnum.name().startsWith("GREEN_") ||
                    cardEnum.name().startsWith("YELLOW_") ||
                    cardEnum.name().startsWith("BLUE_") ||
                    cardEnum.name().startsWith("RED_") ||
                    cardEnum.name().startsWith("SKIP_") ||
                    cardEnum.name().startsWith("RESERVE_") ||
                    cardEnum.name().startsWith("TWO_WILD_DRAW_") ||
                    cardEnum.name().equals("FOUR_WILD_DRAW") ||
                    cardEnum.name().equals("WILD")) {
                Card card = cardFactory.createCard(cardEnum.getFilePath(), cardEnum.name());
                deckOfCards.push(card);
            }
        }
        Collections.shuffle(deckOfCards);
    }

    /**
     * Takes a card from the top of the deck.
     *
     * @return the card from the top of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public Card takeCard() {
        try{
            if (deckOfCards.isEmpty()) {
                throw new emptyDeckException("No hay más cartas en el mazo.");
            }
            return deckOfCards.pop();
        }catch (emptyDeckException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Checks if the deck is empty.
     *
     * @return true if the deck is empty, false otherwise
     */
    public boolean isEmpty() {
        return deckOfCards.isEmpty();
    }
}
