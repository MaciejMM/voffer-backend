package com.example.freight.auth;

import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Order(1)
@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;


    public RoleSeeder(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        List<ERole> roleList = Arrays.asList(ERole.USER, ERole.USER_MANAGER, ERole.ADMIN, ERole.SUPER_ADMIN);
        Map<ERole, String> roleDescriptionMap = Map.of(
                ERole.USER, "Default user role",
                ERole.USER_MANAGER, "User Manager role",
                ERole.ADMIN, "Administrator role",
                ERole.SUPER_ADMIN, "Super Administrator role"
        );

        roleList.forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = Role.builder()
                        .name(roleName)
                        .description(roleDescriptionMap.get(roleName))
                        .build();

                roleRepository.save(roleToCreate);
            });
        });
    }

}
