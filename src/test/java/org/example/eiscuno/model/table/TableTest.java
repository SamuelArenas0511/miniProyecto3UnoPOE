package org.example.eiscuno.model.table;

import org.example.eiscuno.model.card.Card;
import org.example.eiscuno.model.table.bridge.ITableImplementation;
import org.example.eiscuno.model.table.bridge.TableImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Table table;
    private ITableImplementation tableImpl;


    @BeforeEach
    public void setUp() {
        tableImpl = new TableImplementation();
        table = new Table(tableImpl);
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