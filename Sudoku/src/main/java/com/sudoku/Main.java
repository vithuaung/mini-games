package com.sudoku;

import com.sudoku.engine.SudokuGenerator;
import com.sudoku.engine.SudokuSolver;
import com.sudoku.engine.SudokuValidator;
import com.sudoku.game.CommandParser;
import com.sudoku.game.GameEngine;
import com.sudoku.ui.ConsoleRenderer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new GameEngine(
                new SudokuGenerator(),
                new SudokuSolver(),
                new SudokuValidator(),
                new ConsoleRenderer(),
                new CommandParser()
        ).start(new Scanner(System.in));
    }
}
