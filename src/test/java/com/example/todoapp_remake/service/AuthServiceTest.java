package com.example.todoapp_remake.service;

import com.example.todoapp_remake.dto.request.UserRegisterRequest;
import com.example.todoapp_remake.entity.UserEntity;
import com.example.todoapp_remake.exception.UsernameAlreadyExistsException;
import com.example.todoapp_remake.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    ArgumentCaptor<UserEntity> userEntityCaptor;

    @InjectMocks
    private AuthService authService;

    @Test
    public void whenRegisterUserCalledWithValidRequest_shouldCreateValidUserEntity() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("testUsername");
        userRegisterRequest.setPassword("testPassword");

        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        authService.registerUser(userRegisterRequest);

        verify(userRepository).findByUsername("testUsername");
        verify(passwordEncoder).encode("testPassword");
        verify(userRepository).save(userEntityCaptor.capture());

        UserEntity capturedUserEntity = userEntityCaptor.getValue();
        assertThat(capturedUserEntity.getUsername()).isEqualTo("testUsername");
        assertThat(capturedUserEntity.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    public void whenRegisterUserCalledWithInvalidRequest_UsernameAlreadyExists() {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUsername("existingUsername");
        userRegisterRequest.setPassword("testPassword");

        UserEntity userEntity = UserEntity
                .builder()
                .id("1")
                .username("existingUsername")
                .password("password")
                .build();

        when(userRepository.findByUsername(userRegisterRequest.getUsername())).thenReturn(Optional.of(userEntity));

        assertThatThrownBy(() -> authService.registerUser(userRegisterRequest))
                .isInstanceOf(UsernameAlreadyExistsException.class)
                .hasMessageContaining("Username " + userRegisterRequest.getUsername() + " already exists!");


        verify(userRepository).findByUsername(userRegisterRequest.getUsername());
        verifyNoInteractions(passwordEncoder);
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}