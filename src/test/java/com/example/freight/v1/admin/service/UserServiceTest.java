package com.example.freight.v1.admin.service;

import com.example.freight.v1.admin.model.entity.User;
import com.example.freight.v1.admin.model.request.UserRequest;
import com.example.freight.v1.admin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        // given
        User user = User.builder()
                .email("simple@email.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .createdAt(LocalDateTime.now())
                .build();
        UserRequest userRequest =  UserRequest.builder()
                .email("simple@email.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .createdAt(LocalDateTime.now())
                .build();

        // when
        userService.createUser(userRequest);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(user.getEmail(), capturedUser.getEmail());
        assertEquals(user.getFirstName(), capturedUser.getFirstName());
        assertEquals(user.getLastName(), capturedUser.getLastName());
        assertEquals(user.getTitle(), capturedUser.getTitle());
        assertNotNull(capturedUser.getCreatedAt());
        assertNull(capturedUser.getUpdatedAt());
    }

    @Test
    void shouldGetUsers() {
        // given
        userService.getUsers();

        // then
        verify(userRepository).findAll();
    }

    @Test
    void shouldUpdateUser() {
    }

    @Test
    void shouldDeleteUser() {
        // given
        Long userId = 1L;

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository).deleteById(userId);

    }

}