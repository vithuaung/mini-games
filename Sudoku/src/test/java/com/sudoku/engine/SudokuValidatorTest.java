package com.sudoku.engine;

import com.sudoku.model.Grid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuValidatorTest {

    private final SudokuValidator validator = new SudokuValidator();
    private static final boolean[][] NO_PREFILLED = new boolean[9][9];

    @Test
    void emptyGridHasNoViolations() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        assertTrue(validator.findViolation(grid).isEmpty());
    }

    @Test
    void isValidReturnsTrueForCleanBoard() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        assertTrue(validator.isValid(grid));
    }

    @Test
    void detectsRowViolation() {
        int[][] puzzle = new int[9][9];
        puzzle[0][0] = 3;
        puzzle[0][1] = 3;
        Grid grid = new Grid(puzzle, NO_PREFILLED);
        var violation = validator.findViolation(grid);
        assertTrue(violation.isPresent());
        assertTrue(violation.get().contains("Row A"));
    }

    @Test
    void detectsColumnViolation() {
        int[][] puzzle = new int[9][9];
        puzzle[0][0] = 5;
        puzzle[1][0] = 5;
        Grid grid = new Grid(puzzle, NO_PREFILLED);
        var violation = validator.findViolation(grid);
        assertTrue(violation.isPresent());
        assertTrue(violation.get().contains("Column 1"));
    }

    @Test
    void detectsSubgridViolation() {
        int[][] puzzle = new int[9][9];
        puzzle[0][3] = 7;
        puzzle[1][4] = 7;
        Grid grid = new Grid(puzzle, NO_PREFILLED);
        var violation = validator.findViolation(grid);
        assertTrue(violation.isPresent());
        assertTrue(violation.get().contains("subgrid"));
    }

    @Test
    void validPartialGridHasNoViolations() {
        int[][] puzzle = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        Grid grid = new Grid(puzzle, NO_PREFILLED);
        assertTrue(validator.findViolation(grid).isEmpty());
    }
}
