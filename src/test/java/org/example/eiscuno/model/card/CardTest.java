package org.example.eiscuno.model.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CardTest {
    @Test
    public void testGetters() {
        Card card = new Card("9", "GREEN", null);

        assertEquals("9", card.getValue());
        assertEquals("GREEN", card.getColor());
        assertNull(card.getType());
    }
}