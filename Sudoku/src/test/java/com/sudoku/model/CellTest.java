package com.sudoku.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void newCellWithZeroValueIsEmpty() {
        Cell cell = new Cell(0, false);
        assertTrue(cell.isEmpty());
    }

    @Test
    void newCellWithNonZeroValueIsNotEmpty() {
        Cell cell = new Cell(5, false);
        assertFalse(cell.isEmpty());
    }

    @Test
    void preFilledCellReturnsCorrectFlag() {
        Cell cell = new Cell(7, true);
        assertTrue(cell.isPreFilled());
    }

    @Test
    void nonPreFilledCellReturnsCorrectFlag() {
        Cell cell = new Cell(0, false);
        assertFalse(cell.isPreFilled());
    }

    @Test
    void setValueUpdatesValue() {
        Cell cell = new Cell(0, false);
        cell.setValue(9);
        assertEquals(9, cell.getValue());
        assertFalse(cell.isEmpty());
    }

    @Test
    void clearResetsValueToZero() {
        Cell cell = new Cell(4, false);
        cell.clear();
        assertEquals(0, cell.getValue());
        assertTrue(cell.isEmpty());
    }
}
