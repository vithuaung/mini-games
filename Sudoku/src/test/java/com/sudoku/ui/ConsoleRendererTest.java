package com.sudoku.ui;

import com.sudoku.model.Grid;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleRendererTest {

    private final ConsoleRenderer renderer = new ConsoleRenderer();
    private static final boolean[][] NO_PREFILLED = new boolean[9][9];

    private String captureOutput(Runnable action) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return baos.toString();
    }

    @Test
    void printGridDoesNotThrow() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        assertDoesNotThrow(() -> renderer.printGrid(grid));
    }

    @Test
    void printGridShowsUnderscoresForEmptyCells() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        String output = captureOutput(() -> renderer.printGrid(grid));
        assertTrue(output.contains("_"), "Empty cells should be displayed as underscores");
        assertFalse(output.lines().anyMatch(l -> l.matches(".*\\s0\\s.*")),
            "Empty cells should not display as 0");
    }

    @Test
    void printGridHasIndentedRowLabels() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        String output = captureOutput(() -> renderer.printGrid(grid));
        assertTrue(output.contains("  A "), "Row A should be indented as '  A '");
        assertTrue(output.contains("  I "), "Row I should be indented as '  I '");
        assertFalse(output.contains("|"), "Grid should not contain | column separators");
    }
}
