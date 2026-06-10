package com.sudoku.model;

public record MoveResult(Status status, String message) {

    public enum Status {
        SUCCESS,
        PRE_FILLED,
        INVALID_VALUE
    }

    public static MoveResult success() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static MoveResult preFilled(String cellName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static MoveResult invalidValue() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isSuccess() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
