package com.example.todoapp_remake.exception;

public class DependencyNotFoundException extends RuntimeException{
    public DependencyNotFoundException(String dependencyId) {
        super("Dependency not found by id : " + dependencyId);
    }
}
