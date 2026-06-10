package com.sudoku.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private static final boolean[][] NO_PREFILLED = new boolean[9][9];

    @Test
    void incompleteGridIsNotComplete() {
        int[][] values = new int[9][9];
        values[0][0] = 5;
        Grid grid = new Grid(values, NO_PREFILLED);
        assertFalse(grid.isComplete());
    }

    @Test
    void fullyFilledGridIsComplete() {
        int[][] values = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                values[r][c] = (r * 9 + c) % 9 + 1;
            }
        }
        Grid grid = new Grid(values, NO_PREFILLED);
        assertTrue(grid.isComplete());
    }

    @Test
    void getCellReturnsCorrectValue() {
        int[][] values = new int[9][9];
        values[2][4] = 7;
        Grid grid = new Grid(values, NO_PREFILLED);
        assertEquals(7, grid.getCell(2, 4).getValue());
    }

    @Test
    void preFilledCellIsMarkedCorrectly() {
        int[][] values = new int[9][9];
        values[0][0] = 5;
        boolean[][] preFilled = new boolean[9][9];
        preFilled[0][0] = true;
        Grid grid = new Grid(values, preFilled);
        assertTrue(grid.getCell(0, 0).isPreFilled());
        assertFalse(grid.getCell(0, 1).isPreFilled());
    }

    @Test
    void placeValueUpdatesCell() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        grid.placeValue(0, 0, 7);
        assertEquals(7, grid.getCell(0, 0).getValue());
    }

    @Test
    void clearCellResetsToEmpty() {
        int[][] values = new int[9][9];
        values[1][2] = 5;
        Grid grid = new Grid(values, NO_PREFILLED);
        grid.clearCell(1, 2);
        assertTrue(grid.getCell(1, 2).isEmpty());
    }

    @Test
    void toIntArrayReflectsCurrentState() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        grid.placeValue(3, 3, 4);
        assertEquals(4, grid.toIntArray()[3][3]);
    }

    @Test
    void sizeIsNine() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        assertEquals(9, grid.getSize());
    }
}
