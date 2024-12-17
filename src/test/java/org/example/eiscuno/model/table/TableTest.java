package org.example.eiscuno.model.table;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.table.bridge.ITableImplementation;
import org.example.eiscuno.model.table.bridge.TableImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Table} class.
 *
 * This test class contains tests to ensure that the table's operations behave
 * correctly, specifically verifying the retrieval of the current card on the table
 * when the table contains no cards.
 *
 * @author Samuel Arenas Valencia, Maria Juliana Saavedra, Juan Esteban Rodriguez
 * @version 1.0
 */
class TableTest {
    private Table table;
    private ITableImplementation tableImpl;

    /**
     * Sets up the environment before each test method.
     *
     * Initializes a new instance of the {@link Table} class before each test to ensure
     * that each test starts with a clean state.
     */
    @BeforeEach
    public void setUp() {
        tableImpl = new TableImplementation();
        table = new Table(tableImpl);
    }

    /**
     * Tests the {@code getCurrentCardOnTheTable()} method in the {@link Table} class.
     *
     * Verifies that attempting to retrieve the current card from an empty table results
     * in an {@link IndexOutOfBoundsException} with the message "There are no cards on the table."
     * This ensures proper error handling when the table has no cards present.
     */
    @Test
    public void testGetCurrentCardWhenTableIsEmpty() {
        // Verifica que se lanza una excepción si no hay cartas
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            table.getCurrentCardOnTheTable();
        });
        assertEquals("There are no cards on the table.", exception.getMessage());
    }

}