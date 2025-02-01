package com.example.freight.v1.admin;

import com.example.freight.auth.models.request.RegisterUserDto;
import com.example.freight.exception.ApiRequestException;
import com.example.freight.exception.UserNotFoundException;
import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.request.UserRequest;
import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.RoleRepository;
import com.example.freight.auth.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(RoleRepository roleRepository, final UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void createSuperAdministrator(RegisterUserDto userRequest) {

        Optional<Role> optionalRole = roleRepository.findByName(ERole.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userRequest.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }
        User user = User.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .title(userRequest.getTitle())
                .role(optionalRole.get())
                .active(true)
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(optionalRole.get())
                .build();

        userRepository.save(user);
    }


    public User createUser(final UserRequest userRequest) {
        final Optional<Role> optionalRole = roleRepository.findByName(userRequest.role());
        final Optional<User> optionalUser = userRepository.findByEmail(userRequest.email());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            throw new UserNotFoundException("User already exists");
        }

        final User user = User.builder()
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .title(userRequest.title())
                .role(optionalRole.get())
                .active(true)
                .password(passwordEncoder.encode(userRequest.password()))
                .createdAt(LocalDateTime.now())
                .build();

        try {
            return userRepository.save(user);
        } catch (ConstraintViolationException e) {
            throw new UserNotFoundException("User deletion failed", e);
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(final Long userId, final UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        updateUserFields(user, userUpdateRequest);
        user.setUpdatedAt(LocalDateTime.now());
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ApiRequestException("User update failed", e);
        }
    }
    private void updateUserFields(final User user,final UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest.firstName() != null) {
            user.setFirstName(userUpdateRequest.firstName());
        }
        if (userUpdateRequest.lastName() != null) {
            user.setLastName(userUpdateRequest.lastName());
        }
        if (userUpdateRequest.title() != null) {
            user.setTitle(userUpdateRequest.title());
        }
        if (userUpdateRequest.role() != null) {
            roleRepository.findByName(userUpdateRequest.role().getName())
                    .ifPresent(user::setRole);
        }
    }

    public void deleteUser(final Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (ConstraintViolationException e) {
            throw new UserNotFoundException("User deletion failed", e);
        }
    }

}
