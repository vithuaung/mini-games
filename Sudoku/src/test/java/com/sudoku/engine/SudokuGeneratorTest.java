package com.sudoku.engine;

import com.sudoku.model.Grid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGeneratorTest {

    private final SudokuGenerator generator = new SudokuGenerator();
    private final SudokuSolver solver = new SudokuSolver();

    @Test
    void generateReturnsGrid() {
        assertNotNull(generator.generate());
    }

    @Test
    void generatedGridIsPartiallyFilled() {
        Grid grid = generator.generate();
        int filled = 0;
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (!grid.getCell(r, c).isEmpty()) filled++;
        assertTrue(filled > 17, "Puzzle should have at least 17 clues");
        assertTrue(filled < 81, "Puzzle should have some empty cells");
    }

    @Test
    void preFilledCellsHaveNonZeroValues() {
        Grid grid = generator.generate();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (grid.getCell(r, c).isPreFilled())
                    assertNotEquals(0, grid.getCell(r, c).getValue(),
                        "Pre-filled cell at (" + r + "," + c + ") must not be zero");
    }

    @Test
    void emptyCellsAreNotPreFilled() {
        Grid grid = generator.generate();
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (grid.getCell(r, c).isEmpty())
                    assertFalse(grid.getCell(r, c).isPreFilled(),
                        "Empty cell at (" + r + "," + c + ") should not be flagged as pre-filled");
    }

    @Test
    void generatedPuzzleHasUniqueSolution() {
        Grid grid = generator.generate();
        assertTrue(solver.hasUniqueSolution(grid.toIntArray()),
            "Generated puzzle must have exactly one solution");
    }
}
