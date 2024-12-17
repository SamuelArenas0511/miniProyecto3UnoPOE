package org.example.eiscuno.model.table;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.table.bridge.ITableImplementation;

import java.util.ArrayList;

/**
 * Represents the table in the Uno game where cards are played.
 */
public class Table {
    private ArrayList<Card> cardsTable;
    private ITableImplementation tableImplementation;

    /**
     * Constructs a new Table object with no cards on it.
     */
    public Table(ITableImplementation tableImplementation) {
        this.tableImplementation = tableImplementation;
        this.cardsTable = new ArrayList<Card>();
    }

    /**
     * Adds a card to the table.
     *
     * @param card The card to be added to the table.
     */
    public void addCardOnTheTable(Card card){
        tableImplementation.addCardOnTheTable(card,cardsTable);
    }

    /**
     * Retrieves the current card on the table.
     *
     * @throws IndexOutOfBoundsException if there are no cards on the table.
     */
    public Card getCurrentCardOnTheTable() throws IndexOutOfBoundsException {
        return tableImplementation.getCurrentCardOnTheTable(cardsTable);
    }
}
