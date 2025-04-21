package com.example.todoapp_remake.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {

    private String message;

    public RegisterResponse() {
        this.message = "User successfully registered.";
    }
}
