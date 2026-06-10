package com.sudoku.game;

import com.sudoku.engine.SudokuGenerator;
import com.sudoku.engine.SudokuSolver;
import com.sudoku.engine.SudokuValidator;
import com.sudoku.model.Grid;
import com.sudoku.ui.GameDisplay;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    // Minimal recording display — captures messages without touching stdout
    static class RecordingDisplay implements GameDisplay {
        final List<String> messages = new ArrayList<>();
        @Override public void printWelcome() {}
        @Override public void printInitialGrid(Grid grid) {}
        @Override public void printGrid(Grid grid) {}
        @Override public void printMessage(String msg) { messages.add(msg); }
        @Override public void printPrompt() {}
    }

    // Injects a fixed grid so tests are not dependent on the (buggy) generator
    static class FixedGenerator extends SudokuGenerator {
        private final Grid grid;
        FixedGenerator(Grid grid) {
            this.grid = grid;
        }
        @Override
        public Grid generate() {
            return grid;
        }
    }

    // All cells filled except (1,1) which is empty and not pre-filled
    private static Grid gridWithOneEmptyCell() {
        int[][] vals = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 0, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        boolean[][] preFilled = new boolean[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                preFilled[r][c] = vals[r][c] != 0;
        return new Grid(vals, preFilled);
    }

    @Test
    void gameQuits() {
        RecordingDisplay display = new RecordingDisplay();
        GameEngine engine = new GameEngine(
            new FixedGenerator(gridWithOneEmptyCell()),
            new SudokuSolver(), new SudokuValidator(),
            display, new CommandParser()
        );
        assertDoesNotThrow(() -> engine.start(new Scanner("quit\n")));
        assertTrue(display.messages.stream().anyMatch(m -> m.toLowerCase().contains("thanks")),
            "Quit should print a farewell message");
    }

    @Test
    void placeOnPreFilledCellIsRejected() {
        // With the buggy CommandParser (row +1, col no -1),
        // "A1 9" is parsed as PlaceCommand(1, 1, 9).
        // Cell (1,1) in gridWithOneEmptyCell() is the empty cell (value 0, not pre-filled),
        // so use "A2 9" which with bugs maps to PlaceCommand(1, 2, 9) — cell (1,2) is pre-filled.
        RecordingDisplay display = new RecordingDisplay();
        GameEngine engine = new GameEngine(
            new FixedGenerator(gridWithOneEmptyCell()),
            new SudokuSolver(), new SudokuValidator(),
            display, new CommandParser()
        );
        engine.start(new Scanner("A2 9\nquit\n"));
        assertTrue(display.messages.stream()
            .anyMatch(m -> m.toLowerCase().contains("pre-filled") || m.toLowerCase().contains("filled")),
            "Placing on a pre-filled cell must be rejected");
    }

    @Test
    void checkCommandReportsNoViolationsOnValidGrid() {
        RecordingDisplay display = new RecordingDisplay();
        GameEngine engine = new GameEngine(
            new FixedGenerator(gridWithOneEmptyCell()),
            new SudokuSolver(), new SudokuValidator(),
            display, new CommandParser()
        );
        engine.start(new Scanner("check\nquit\n"));
        assertTrue(display.messages.stream()
            .anyMatch(m -> m.toLowerCase().contains("no violation")),
            "check on a valid grid should report no violations");
    }

    @Test
    void unknownCommandShowsHelp() {
        RecordingDisplay display = new RecordingDisplay();
        GameEngine engine = new GameEngine(
            new FixedGenerator(gridWithOneEmptyCell()),
            new SudokuSolver(), new SudokuValidator(),
            display, new CommandParser()
        );
        engine.start(new Scanner("foobar\nquit\n"));
        assertTrue(display.messages.stream()
            .anyMatch(m -> m.toLowerCase().contains("unknown")),
            "Unrecognised input should show an error hint");
    }
}
