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
    void printGridShowsDotsForEmptyCells() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        String output = captureOutput(() -> renderer.printGrid(grid));
        assertTrue(output.contains("."), "Empty cells should be displayed as dots");
        assertFalse(output.lines().anyMatch(l -> l.matches(".*\\s0\\s.*")),
            "Empty cells should not display as 0");
    }

    @Test
    void printGridHasRowSeparatorsAfterRowsCAndF() {
        Grid grid = new Grid(new int[9][9], NO_PREFILLED);
        String output = captureOutput(() -> renderer.printGrid(grid));
        String[] lines = output.split("\\r?\\n");

        // Layout: header(0), A(1), B(2), C(3), sep(4), D(5), E(6), F(7), sep(8), G(9), H(10), I(11)
        assertTrue(lines[4].contains("+"),
            "Separator should appear between rows C and D (line index 4)");
        assertTrue(lines[8].contains("+"),
            "Separator should appear between rows F and G (line index 8)");
        assertFalse(lines[3].contains("+"),
            "Row C itself should not be a separator line");
    }
}
