package com.tp24.OrderMngmtAPI.serviceTests;
import com.tp24.OrderMngmtAPI.exception.UserNotFoundException;
import com.tp24.OrderMngmtAPI.model.User;
import com.tp24.OrderMngmtAPI.repository.UserRepository;
import com.tp24.OrderMngmtAPI.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
    }

    @Test
    void getUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void hasUserWithUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean result = userService.hasUserWithUsername("testuser");

        assertTrue(result);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    @Test
    void hasUserWithEmail() {
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

        boolean result = userService.hasUserWithEmail("testuser@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail("testuser@example.com");
    }

    @Test
    void validateAndGetUserByUsername_found() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User result = userService.validateAndGetUserByUsername("testuser");

        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void validateAndGetUserByUsername_notFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.validateAndGetUserByUsername("testuser"));

        assertEquals("User with username testuser not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void saveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(user);

        verify(userRepository, times(1)).delete(user);
    }
}
//added tests for services