package com.sudoku.game;

import com.sudoku.engine.SudokuGenerator;
import com.sudoku.engine.Solver;
import com.sudoku.engine.Validator;
import com.sudoku.model.Grid;
import com.sudoku.model.MoveResult;
import com.sudoku.ui.GameDisplay;

import java.util.Optional;
import java.util.Scanner;

public class GameEngine {

    private final SudokuGenerator generator;
    private final Solver solver;
    private final Validator validator;
    private final GameDisplay display;
    private final CommandParser parser;

    public GameEngine(SudokuGenerator generator, Solver solver, Validator validator,
                      GameDisplay display, CommandParser parser) {
        this.generator = generator;
        this.solver = solver;
        this.validator = validator;
        this.display = display;
        this.parser = parser;
    }

    public void start(Scanner scanner) {
        display.printWelcome();
        Grid grid = generator.generate();
        display.printInitialGrid(grid);

        while (scanner.hasNextLine()) {
            display.printPrompt();
            String line = scanner.nextLine().trim();
            Optional<Command> cmd = parser.parse(line);

            if (cmd.isEmpty()) {
                display.printMessage("Unknown command. Try: A1 5, clear A1, hint, check, quit");
                continue;
            }

            boolean quit = execute(cmd.get(), grid);
            if (quit) break;

            if (grid.isComplete()) { // should also check validator.isValid(grid)
                display.printMessage("Congratulations! Puzzle solved!");
                break;
            }
            display.printGrid(grid);
        }
    }

    private boolean execute(Command cmd, Grid grid) {
        return switch (cmd) {
            case PlaceCommand p -> {
                MoveResult result = handlePlace(grid, p.row(), p.col(), p.value());
                display.printMessage(result.message());
                yield false;
            }
            case ClearCommand c -> {
                if (grid.getCell(c.row(), c.col()).isPreFilled()) {
                    display.printMessage("Cannot clear a pre-filled cell.");
                } else {
                    grid.clearCell(c.row(), c.col());
                    display.printMessage("Cell cleared.");
                }
                yield false;
            }
            case HintCommand h -> {
                giveHint(grid);
                yield false;
            }
            case CheckCommand ch -> {
                Optional<String> violation = validator.findViolation(grid);
                display.printMessage(violation.orElse("No violations found."));
                yield false;
            }
            case QuitCommand q -> {
                display.printMessage("Thanks for playing!");
                yield true;
            }
        };
    }

    private MoveResult handlePlace(Grid grid, int row, int col, int value) {
        // missing pre-filled check — should return MoveResult.preFilled(...) if pre-filled
        if (value < 1 || value > 9) {
            return MoveResult.invalidValue();
        }
        grid.placeValue(row, col, value);
        return MoveResult.success();
    }

    private void giveHint(Grid grid) {
        Optional<int[][]> solution = solver.solve(grid.toIntArray());
        if (solution.isEmpty()) {
            display.printMessage("No solution found from current state.");
            return;
        }
        int[][] solved = solution.get();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (grid.getCell(r, c).isEmpty()) {
                    grid.placeValue(r, c, solved[r][c]);
                    display.printMessage("Hint: " + (char) ('A' + r) + (c + 1) + " = " + solved[r][c]);
                    return;
                }
            }
        }
        display.printMessage("No empty cells remaining.");
    }
}
