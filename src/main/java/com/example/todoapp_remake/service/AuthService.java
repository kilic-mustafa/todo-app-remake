package com.example.todoapp_remake.service;

import com.example.todoapp_remake.dto.request.UserRegisterRequest;
import com.example.todoapp_remake.entity.UserEntity;
import com.example.todoapp_remake.exception.UsernameAlreadyExistsException;
import com.example.todoapp_remake.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRegisterRequest userRegisterRequest) {

        Optional<UserEntity> user = userRepository.findByUsername(userRegisterRequest.getUsername());

        if(user.isPresent()) {
            throw new UsernameAlreadyExistsException(userRegisterRequest.getUsername());
        }

        UserEntity userEntity = UserEntity
                .builder()
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .build();

        userRepository.save(userEntity);
    }

}
