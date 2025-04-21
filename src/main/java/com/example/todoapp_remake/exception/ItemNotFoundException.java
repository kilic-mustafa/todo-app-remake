package com.example.todoapp_remake.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String itemId) {
        super("Item not found for id : " + itemId);
    }
}
