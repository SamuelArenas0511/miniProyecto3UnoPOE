package org.example.eiscuno.model.deck;

import org.example.eiscuno.model.exception.emptyDeckException;
import org.example.eiscuno.model.factoryMethod.CardFactory;
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
    private CardFactory cardFactory;

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
                Card card = cardFactory.createCard(
                        cardEnum.getFilePath(),
                        getCardValue(cardEnum.name()),
                        getCardColor(cardEnum.name()),
                        getCardType(cardEnum.name())
                );
//                Card card = new Card(cardEnum.getFilePath(), getCardValue(cardEnum.name()), getCardColor(cardEnum.name()), getCardType(cardEnum.name()));
                deckOfCards.push(card);
            }
        }
        Collections.shuffle(deckOfCards);
    }

    private String getCardValue(String name) {
        if (name.endsWith("0")){
            return "0";
        } else if (name.endsWith("1")){
            return "1";
        } else if (name.endsWith("2")){
            return "2";
        } else if (name.endsWith("3")){
            return "3";
        } else if (name.endsWith("4")){
            return "4";
        } else if (name.endsWith("5")){
            return "5";
        } else if (name.endsWith("6")){
            return "6";
        } else if (name.endsWith("7")){
            return "7";
        } else if (name.endsWith("8")){
            return "8";
        } else if (name.endsWith("9")){
            return "9";
        } else {
            return null;
        }

    }

    private String getCardColor(String name){
        if(name.startsWith("GREEN") || name.endsWith("GREEN")){
            return "GREEN";
        } else if(name.startsWith("YELLOW") || name.endsWith("YELLOW")){
            return "YELLOW";
        } else if(name.startsWith("BLUE") || name.endsWith("BLUE")){
            return "BLUE";
        } else if(name.startsWith("RED") || name.endsWith("RED")){
            return "RED";
        } else {
            return null;
        }
    }

    private String getCardType(String name){
        if(name.startsWith("TWO_WILD") || name.endsWith("TWO_WILD")){
            return "TWO_WILD";
        } else if(name.startsWith("FOUR_WILD") || name.endsWith("FOUR_WILD")){
            return "FOUR_WILD";
        } else if(name.startsWith("SKIP") || name.endsWith("SKIP")){
            return "SKIP";
        } else if(name.startsWith("RESERVE") || name.endsWith("RESERVE")){
            return "RESERVE";
        } else if(name.startsWith("WILD") || name.endsWith("WILD")){
            return "WILD";
        }else {
            return null;
        }
    }

    /**
     * Takes a card from the top of the deck.
     *
     * @return the card from the top of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public Card takeCard() {
        return deckOfCards.pop();
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
