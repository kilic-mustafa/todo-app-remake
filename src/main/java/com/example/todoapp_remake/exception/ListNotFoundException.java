package com.example.todoapp_remake.exception;

public class ListNotFoundException extends RuntimeException{

    public ListNotFoundException(String listId) {
        super("List not found for id : " + listId);
    }
}
