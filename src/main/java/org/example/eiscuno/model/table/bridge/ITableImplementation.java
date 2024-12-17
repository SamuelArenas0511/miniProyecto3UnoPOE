package org.example.eiscuno.model.table.bridge;

import org.example.eiscuno.model.card.Card;

import java.util.ArrayList;

public interface ITableImplementation {
    /**
     * Adds a card to the table.
     *
     * @param card The card to be added to the table.
     */
    void addCardOnTheTable(Card card, ArrayList<Card> cardsTable);

    /**
     * Retrieves the current card on the table.
     *
     * @return The card currently on the table.
     * @throws IndexOutOfBoundsException if there are no cards on the table.
     */
    Card getCurrentCardOnTheTable(ArrayList<Card> cardsTable) throws IndexOutOfBoundsException;
}
