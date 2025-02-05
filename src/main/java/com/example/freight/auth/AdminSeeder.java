package com.example.freight.auth;

import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
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

        Optional<Role> optionalRole = roleRepository.findByName(ERole.SUPER_ADMIN);
        Optional<Role> adminRole = roleRepository.findByName(ERole.ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail("super.admin@email.com");

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
        User pawelAccount = User.builder()
                .firstName("Super")
                .lastName("Admin2")
                .email("super.admin2@email.com")
                .password(passwordEncoder.encode("123456"))
                .title("Mr")
                .role(adminRole.get())
                .admin(true)
                .active(true)
                .build();

        userRepository.saveAll(List.of(user, pawelAccount));
    }
}
