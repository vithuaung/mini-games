# Sudoku – Design Notes

## Overview

A command-line Sudoku game written in Java 21. The player interacts by typing
commands to place numbers, clear cells, request hints, check for rule violations,
or quit at any time. The game generates a new puzzle each round and ends when the
grid is fully and correctly filled.

## Package Structure

```
com.sudoku
├── Main.java                   entry point – wires all dependencies together
├── model/                      plain data – holds game state, no rules
│   ├── Cell.java
│   ├── Grid.java
│   └── MoveResult.java
├── engine/                     core logic – generation, solving, validation
│   ├── Solver.java             interface
│   ├── Validator.java          interface
│   ├── SudokuGenerator.java
│   ├── SudokuSolver.java       implements Solver
│   └── SudokuValidator.java    implements Validator
├── game/                       user interaction – command parsing and game loop
│   ├── Command.java            sealed interface + record subtypes
│   ├── CommandParser.java
│   ├── GameEngine.java
│   └── ParseException.java
└── ui/
    ├── GameDisplay.java        interface
    └── ConsoleRenderer.java    implements GameDisplay
```

## Key Design Decisions

**Model vs engine separation**
Grid and Cell only store state. They have no knowledge of Sudoku rules.
This keeps the model classes simple and makes the engine easy to test on its own.

**Grid owns its mutations**
Rather than exposing Cell objects directly and letting callers call
`cell.setValue()` freely, Grid exposes `placeValue(row, col, value)` and
`clearCell(row, col)`. This keeps mutation paths controlled and makes the
intent at call sites obvious.

**Dependency Inversion – interfaces for engine and display**
GameEngine depends on the `Solver`, `Validator`, and `GameDisplay` interfaces,
not on the concrete classes. All dependencies are injected through the
constructor. This makes GameEngine testable in isolation and keeps the
high-level game logic decoupled from implementation details like how the board
is solved or how output is rendered.

`Main` is the only place that knows about concrete types and wires everything
together.

**Solver – backtracking**
A recursive backtracking algorithm fills empty cells one at a time and backs up
when no number fits. It is used in two places: completing a board during puzzle
generation, and finding the correct value for a hint cell. The solver also exposes
a uniqueness check used during puzzle generation.

**Puzzle generation**
The three diagonal 3×3 boxes are filled first because they are completely
independent of each other. The solver then fills the rest. Numbers are removed one
at a time in random order; each removal is only kept if the puzzle still has a
unique solution. This stops when 30 clues remain.

**Commands – sealed interface**
User commands are modelled as a sealed interface with a small record for each
command type (PlaceCommand, ClearCommand, HintCommand, CheckCommand, QuitCommand).
This lets GameEngine use a switch expression that the compiler checks for
exhaustiveness, which removes the need for a default branch and makes future
additions obvious.

**Validation**
The validator scans rows, columns, and 3×3 subgrids and returns the first
violation it finds as a plain string message, or an empty Optional when the board
is clean.

## How to Build and Run

Requirements: Java 21, Maven 3.8+

```bash
# Build and run all tests
mvn clean verify

# Run the game (after building)
java -jar target/sudoku.jar
```

## Assumptions

- The puzzle always starts with exactly 30 pre-filled cells.
- Pre-filled cells cannot be changed or cleared by the player.
- A hint reveals the correct value for the first empty cell the solver encounters.
  If the current board state has no solution (the player has entered conflicting
  numbers), the hint command says so instead of guessing.
- The `check` command reports the first violation found, not all of them.
