package com.example.freight.v1.admin.service;

import com.example.freight.v1.admin.model.entity.User;
import com.example.freight.v1.admin.model.request.UserRequest;
import com.example.freight.v1.admin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
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

        when(userRepository.save(user)).thenReturn(user);
        doReturn(user).when(userService).createUser(userRequest);

        // when
        User createdUser = userService.createUser(userRequest);

        // then
        assertNotNull(createdUser);
        assertEquals("simple@email.com", createdUser.getEmail());
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("Mr.", createdUser.getTitle());
        assertNotNull(createdUser.getCreatedAt());
        assertNull(createdUser.getUpdatedAt());
    }

    @Test
    void shouldGetUsers() {
        // given
        User user = User.builder()
                .email("simple@email.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .createdAt(LocalDateTime.now())
                .build();
        User user2 =  User.builder()
                .email("simple@email.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .createdAt(LocalDateTime.now())
                .build();
        when(userRepository.findAll()).thenReturn(List.of(user, user2));
        doReturn(List.of(user, user2)).when(userService).getUsers();

        // when
        List<User> users = userService.getUsers();

        // then

        assertNotNull(users);
        assertEquals(2, users.size());

    }

    @Test
    void shouldUpdateUser() {
    }

    @Test
    void shouldDeleteUser() {
    }

}