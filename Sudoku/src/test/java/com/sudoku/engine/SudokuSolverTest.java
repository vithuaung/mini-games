package com.sudoku.engine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSolverTest {

    private final SudokuSolver solver = new SudokuSolver();

    private int[][] knownPuzzle() {
        return new int[][]{
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
    }

    @Test
    void solvesKnownPuzzle() {
        var result = solver.solve(knownPuzzle());
        assertTrue(result.isPresent());
        int[][] solution = result.get();
        assertEquals(4, solution[0][2]);
        assertEquals(6, solution[0][3]);
        assertEquals(5, solution[4][4]);
        assertEquals(2, solution[8][3]);
    }

    @Test
    void solveDoesNotModifyOriginalPuzzle() {
        int[][] puzzle = knownPuzzle();
        int before = puzzle[0][2];
        solver.solve(puzzle);
        assertEquals(before, puzzle[0][2]);
    }

    @Test
    void returnsEmptyForUnsolvablePuzzle() {
        // row 0 has 1-8 so only 9 can go in (0,0), but col 0 already has 9 - no solution
        int[][] puzzle = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {9, 0, 0, 0, 0, 0, 0, 0, 0},
            new int[9], new int[9], new int[9],
            new int[9], new int[9], new int[9], new int[9]
        };
        assertTrue(solver.solve(puzzle).isEmpty());
    }

    @Test
    void hasUniqueSolutionReturnsTrueForSingleSolution() {
        // one empty cell - only one valid number fits (9 at position (8,8))
        int[][] almostSolved = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 0}
        };
        assertTrue(solver.hasUniqueSolution(almostSolved));
    }

    @Test
    void hasUniqueSolutionReturnsFalseWhenMultipleSolutionsExist() {
        // one clue means countless valid completions - algorithm finds two and stops early
        int[][] sparse = new int[9][9];
        sparse[0][0] = 1;
        assertFalse(solver.hasUniqueSolution(sparse));
    }
}
