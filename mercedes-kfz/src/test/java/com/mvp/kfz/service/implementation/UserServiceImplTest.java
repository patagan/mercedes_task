package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.LoginInput;
import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenUserWhenUserNotExistsThenSave() {
        // Given
        Users newUser = Users.builder().id(1L).username("newUser").build();

        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        // When
        Long userId = userService.addUser(newUser);

        // Then
        assertNotNull(userId);
        verify(userRepository).findByUsername(newUser.getUsername());
        verify(userRepository).save(newUser);
    }

    @Test
    public void givenUserWhenUserExistsThenThrowException() {
        // Given
        Users existingUser = Users.builder().username("existingUser").build();

        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(existingUser));
        verify(userRepository).findByUsername(existingUser.getUsername());
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    public void givenLoginInputWhenUserExistsThenGetDetails() {
        // Given
        LoginInput loginInput = LoginInput.builder().username("username").password("password").build();
        Users existingUser = Users.builder().username("username").build();

        when(userRepository.findByUsername(loginInput.getUsername())).thenReturn(Optional.of(existingUser));

        // When
        Users result = userService.getUserDetails(loginInput);

        // Then
        assertNotNull(result);
        assertEquals(existingUser, result);
        verify(userRepository).findByUsername(loginInput.getUsername());
    }

    @Test
    public void givenLoginInputWhenUserDoesNotExistThenThrowError() {
        // Given
        LoginInput loginInput = LoginInput.builder().username("username").password("password").build();

        when(userRepository.findByUsername(loginInput.getUsername())).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserDetails(loginInput));
        verify(userRepository).findByUsername(loginInput.getUsername());
    }

    @Test
    public void givenUserIdWhenUserExistThenGetUser() {
        // Given
        Long userId = 1L;
        Users existingUser = Users.builder().id(userId).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // When
        Users result = userService.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(existingUser, result);
        verify(userRepository).findById(userId);
    }

    @Test
    public void givenUserIdWhenUserDoesNotExistThenThrowError() {
        // Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(Exception.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

}