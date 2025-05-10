package com.example.freight.v1.admin;

import com.example.freight.auth.RoleRepository;
import com.example.freight.auth.UserRepository;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.models.request.UserRequest;
import com.example.freight.exception.ApiRequestException;
import com.example.freight.exception.UserNotFoundException;
import com.example.freight.v1.admin.kinde.KindeUserService;
import com.example.freight.v1.admin.kinde.UserRequestMapper;
import com.example.freight.v1.admin.kinde.model.KindeCreateUserResponse;
import com.example.freight.v1.admin.kinde.model.request.KindeCreateUserRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final KindeUserService kindeUserService;

    public UserService(RoleRepository roleRepository, final UserRepository userRepository, final KindeUserService kindeUserService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.kindeUserService = kindeUserService;
    }

    public User createUser(final UserRequest userRequest) {
        final Optional<Role> optionalRole = roleRepository.findByName(userRequest.role());
        final Optional<User> optionalUser = userRepository.findByEmail(userRequest.email());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            throw new UserNotFoundException("User already exists");
        }
        final KindeCreateUserRequest map = UserRequestMapper.map(userRequest);
        final KindeCreateUserResponse kindeUser = kindeUserService.createUser(map);
        final User user = User.builder()
                .email(userRequest.email())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .title(userRequest.title())
                .role(optionalRole.get())
                .createdAt(LocalDateTime.now())
                .kindeId(kindeUser.id())
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
    public User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
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

    private void updateUserFields(final User user, final UserUpdateRequest userUpdateRequest) {
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

        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        try {
            kindeUserService.deleteUser(user.getKindeId());
            userRepository.deleteById(userId);
        } catch (ConstraintViolationException e) {
            throw new UserNotFoundException("User deletion failed", e);
        }
    }

}
