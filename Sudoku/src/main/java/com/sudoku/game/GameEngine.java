package com.sudoku.game;

import com.sudoku.engine.SudokuGenerator;
import com.sudoku.engine.Solver;
import com.sudoku.engine.Validator;
import com.sudoku.ui.GameDisplay;

import java.util.Scanner;

public class GameEngine {

    private final SudokuGenerator generator;
    private final Solver solver;
    private final Validator validator;
    private final GameDisplay display;
    private final CommandParser parser;

    public GameEngine(SudokuGenerator generator, Solver solver, Validator validator,
                      GameDisplay display, CommandParser parser) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void start(Scanner scanner) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
