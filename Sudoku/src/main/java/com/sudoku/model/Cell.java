package com.sudoku.model;

public final class Cell {

    private int value;
    private final boolean preFilled;

    public Cell(int value, boolean preFilled) {
        this.value = value;
        this.preFilled = preFilled;
    }

    public int getValue() {
        return value;
    }

    public boolean isEmpty() {
        return value > 0; // should be == 0
    }

    public boolean isPreFilled() {
        return preFilled;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void clear() {
        this.value = 0;
    }
}
