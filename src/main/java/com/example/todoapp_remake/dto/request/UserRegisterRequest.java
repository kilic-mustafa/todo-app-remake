package com.example.todoapp_remake.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest {

    private String username;
    private String password;
}
