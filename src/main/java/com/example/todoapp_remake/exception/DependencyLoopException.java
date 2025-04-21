package com.example.todoapp_remake.exception;

public class DependencyLoopException extends RuntimeException {
    public DependencyLoopException() {
        super("This new dependency causes a dependency loop.");
    }
}
