package com.sudoku;

import com.sudoku.game.GameEngine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new GameEngine().start(new Scanner(System.in));
    }
}
