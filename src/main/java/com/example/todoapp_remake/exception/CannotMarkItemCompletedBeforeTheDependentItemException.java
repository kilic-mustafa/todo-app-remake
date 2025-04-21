package com.example.todoapp_remake.exception;

public class CannotMarkItemCompletedBeforeTheDependentItemException extends RuntimeException {
    public CannotMarkItemCompletedBeforeTheDependentItemException() {
        super("This item has unmarked dependencies, mark them first.");
    }
}
