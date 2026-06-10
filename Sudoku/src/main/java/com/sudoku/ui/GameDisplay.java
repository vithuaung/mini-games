package com.sudoku.ui;

import com.sudoku.model.Grid;

public interface GameDisplay {
    void printWelcome();
    void printInitialGrid(Grid grid);
    void printGrid(Grid grid);
    void printMessage(String message);
    void printPrompt();
}
