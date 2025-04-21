package com.example.todoapp_remake.exception;

public class ItemAlreadyExistException extends RuntimeException{

    public ItemAlreadyExistException(String name) {
        super("Item " + name + " already exists!");
    }
}
