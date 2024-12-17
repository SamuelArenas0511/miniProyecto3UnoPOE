package org.example.eiscuno.model.table;

import org.example.eiscuno.model.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;

    @BeforeEach
    public void setUp() {
        table = new Table();
    }

    @Test
    public void testGetCurrentCardWhenTableIsEmpty() {
        // Verifica que se lanza una excepción si no hay cartas
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            table.getCurrentCardOnTheTable();
        });
        assertEquals("There are no cards on the table.", exception.getMessage());
    }

}