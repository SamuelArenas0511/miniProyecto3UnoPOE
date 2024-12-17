package org.example.eiscuno.model.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The `CardTest` class contains unit tests for the `Card` class, verifying the correctness of getter methods
 * for attributes such as value, color, and type. It uses JUnit to ensure that the class methods return the expected results.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
class CardTest {
    /**
     * Tests the getter methods of the `Card` class to ensure proper retrieval of card properties.
     *
     * This test verifies that:
     * - The `getValue()` method correctly returns the card's value.
     * - The `getColor()` method correctly returns the card's color.
     * - The `getType()` method correctly returns `null` when the type is not set.
     */
    @Test
    public void testGetters() {
        Card card = new Card("9", "GREEN", null);

        assertEquals("9", card.getValue());
        assertEquals("GREEN", card.getColor());
        assertNull(card.getType());
    }
}