package com.example.freight.auth;

import com.example.freight.exception.ApiRequestException;
import com.example.freight.exception.UserNotFoundException;
import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.models.request.LoginUserDto;
import com.example.freight.auth.models.request.RegisterUserDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(final RegisterUserDto input) {
        final Optional<Role> optionalRole = roleRepository.findByName(ERole.USER);

        User user = User.builder()
                .email(input.getEmail())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .title(input.getTitle())
                .role(optionalRole.orElseThrow(() -> new UserNotFoundException("Role not found")))
                .admin(false)
                .active(true)
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ApiRequestException("User creation failed", e);
        }
    }

    public User authenticate(final LoginUserDto input) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("Invalid credentials");
        }
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));
    }
}
