package com.example.todoapp_remake.controller;

import com.example.todoapp_remake.dto.response.RegisterResponse;
import com.example.todoapp_remake.dto.request.UserRegisterRequest;
import com.example.todoapp_remake.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        authService.registerUser(userRegisterRequest);
        return new RegisterResponse();
    }

}
