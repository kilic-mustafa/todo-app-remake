package com.example.todoapp_remake.exception;

public class CannotCreateDependencyBetweenUncommonItemsException extends RuntimeException {
    public CannotCreateDependencyBetweenUncommonItemsException() {
        super("These items are not in the same list.");
    }
}
