package com.example.freight.v1.admin.service;

import com.example.freight.auth.RoleRepository;
import com.example.freight.auth.UserRepository;
import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.models.request.UserRequest;
import com.example.freight.exception.ApiRequestException;
import com.example.freight.v1.admin.UserService;
import com.example.freight.v1.admin.UserUpdateRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateUser() {
        // given
        Role role = Role.builder().name(ERole.USER).createdAt(LocalDateTime.now()).description("test description").build();

        User user = User.builder()
                .email("simple@email.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();
        UserRequest userRequest = new UserRequest("simple@email.com", "password", "John", "Doe", "Mr.", ERole.USER);

        // when
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName(ERole.USER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("password");
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
        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("test@user.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .createdAt(LocalDateTime.now())
                .build();
        Role role = Role.builder().name(ERole.USER).updatedAt(LocalDateTime.now()).description("Role desciption").id(1).build();
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest("Jane", "Smith", "Ms.", role, true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        userService.updateUser(userId, userUpdateRequest);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User updatedUser = userArgumentCaptor.getValue();

        assertEquals("Jane", updatedUser.getFirstName());
        assertEquals("Smith", updatedUser.getLastName());
        assertEquals("Ms.", updatedUser.getTitle());
        assertNotNull(updatedUser.getUpdatedAt());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        Long userId = 1L;
        Role role = Role.builder().name(ERole.USER).updatedAt(LocalDateTime.now()).description("Role desciption").id(1).build();
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest("Jane", "Smith", "Ms.", role, true);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ApiRequestException.class, () -> userService.updateUser(userId, userUpdateRequest));
    }

    @Test
    void shouldThrowExceptionWhenDataIntegrityViolationOccurs() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .email("test@user.com")
                .firstName("John")
                .lastName("Doe")
                .title("Mr.")
                .createdAt(LocalDateTime.now())
                .build();
        Role role = Role.builder().name(ERole.USER).updatedAt(LocalDateTime.now()).description("Role desciption").id(1).build();
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest("Jane", "Smith", "Ms.", role, true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doThrow(DataIntegrityViolationException.class).when(userRepository).save(any(User.class));

        // when & then
        assertThrows(ApiRequestException.class, () -> userService.updateUser(userId, userUpdateRequest));
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