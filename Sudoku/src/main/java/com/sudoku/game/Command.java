package com.sudoku.game;

public sealed interface Command permits PlaceCommand, ClearCommand, HintCommand, CheckCommand, QuitCommand {
}

record PlaceCommand(int row, int col, int value) implements Command {}

record ClearCommand(int row, int col) implements Command {}

record HintCommand() implements Command {}

record CheckCommand() implements Command {}

record QuitCommand() implements Command {}
