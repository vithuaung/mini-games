package com.sudoku.model;

public record MoveResult(Status status, String message) {

    public enum Status {
        SUCCESS,
        PRE_FILLED
    }

    public static MoveResult success() {
        return new MoveResult(Status.SUCCESS, "Move accepted.");
    }

    public static MoveResult preFilled(String cellName) {
        return new MoveResult(Status.PRE_FILLED, "Invalid move. " + cellName + " is pre-filled.");
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}
