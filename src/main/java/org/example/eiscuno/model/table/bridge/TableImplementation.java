package org.example.eiscuno.model.table.bridge;

import org.example.eiscuno.model.card.Card;

import java.util.ArrayList;

public class TableImplementation implements ITableImplementation{
    /**
     * Adds a card to the table.
     *
     * @param card The card to be added to the table.
     */
    @Override
    public void addCardOnTheTable(Card card, ArrayList<Card> cardsTable){
        cardsTable.add(card);
    }

    /**
     * Retrieves the current card on the table.
     *
     * @return The card currently on the table.
     * @throws IndexOutOfBoundsException if there are no cards on the table.
     */
    @Override
    public Card getCurrentCardOnTheTable(ArrayList<Card> cardsTable) throws IndexOutOfBoundsException {
        if (cardsTable.isEmpty()) {
            throw new IndexOutOfBoundsException("There are no cards on the table.");
        }
        return cardsTable.get(cardsTable.size()-1);
    }
}
