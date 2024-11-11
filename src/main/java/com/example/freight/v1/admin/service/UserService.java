package com.example.freight.v1.admin.service;

import com.example.freight.exception.ApiRequestException;
import com.example.freight.v1.admin.model.request.UserRequest;
import com.example.freight.v1.admin.model.entity.User;
import com.example.freight.v1.admin.model.request.UserUpdateRequest;
import com.example.freight.v1.admin.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final UserRequest userRequest) {
        final User user = User.builder()
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .title(userRequest.title())
                .createdAt(userRequest.createdAt())
                .updatedAt(null)
                .build();

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ApiRequestException("User creation failed", e);
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void updateUser(final Long userId, final UserUpdateRequest userUpdateRequest) {
        Optional
                .ofNullable(userRepository.findById(userId).orElseThrow(() -> new ApiRequestException("Unauthorized operation")))
                .ifPresent(user -> {
                    if (userUpdateRequest.email() != null) {
                        user.setEmail(userUpdateRequest.email());
                    }
                    if (userUpdateRequest.firstName() != null) {
                        user.setFirstName(userUpdateRequest.firstName());
                    }
                    if (userUpdateRequest.lastName() != null) {
                        user.setLastName(userUpdateRequest.lastName());
                    }
                    if (userUpdateRequest.title() != null) {
                        user.setTitle(userUpdateRequest.title());
                    }
                    user.setUpdatedAt(LocalDateTime.now());
                    try {
                        userRepository.save(user);
                    } catch (DataIntegrityViolationException e) {
                        throw new ApiRequestException("User update failed", e);
                    }
                });

    }

    public void deleteUser(final Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (ConstraintViolationException e) {
            throw new ApiRequestException("Unauthorized operation", e);
        }
    }

}
