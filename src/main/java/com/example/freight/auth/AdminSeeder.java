package com.example.freight.auth;

import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.entity.User;
import com.example.freight.auth.models.request.RegisterUserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Order(2)
@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = RegisterUserDto.builder()
                .firstName("Super")
                .lastName("Admin")
                .email("super.admin@email.com")
                .password("123456")
                .title("Mr")
                .admin(true)
                .active(true)
                .build();

        Optional<Role> optionalRole = roleRepository.findByName(ERole.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User user = User.builder()
                .firstName("Super")
                .lastName("Admin")
                .email("super.admin@email.com")
                .password(passwordEncoder.encode("123456"))
                .title("Mr")
                .role(optionalRole.get())
                .admin(true)
                .active(true)
                .build();

        userRepository.save(user);
    }
}
