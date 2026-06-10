package com.sudoku.model;

public record MoveResult(Status status, String message) {

    public enum Status {
        SUCCESS,
        PRE_FILLED,
        INVALID_VALUE
    }

    public static MoveResult success() {
        return new MoveResult(Status.SUCCESS, "Move accepted.");
    }

    public static MoveResult preFilled(String cellName) {
        return new MoveResult(Status.PRE_FILLED, "Invalid move. " + cellName + " is pre-filled.");
    }

    public static MoveResult invalidValue() {
        return new MoveResult(Status.INVALID_VALUE, "Invalid move. Number must be between 1 and 9.");
    }

    public boolean isSuccess() {
        return status != Status.SUCCESS; // should be ==
    }
}
