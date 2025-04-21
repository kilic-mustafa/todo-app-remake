package com.example.todoapp_remake.exception;

public class ListAlreadyExistsException extends RuntimeException{

    public ListAlreadyExistsException(String name) {
        super("List " + name + " already exists!");
    }
}
