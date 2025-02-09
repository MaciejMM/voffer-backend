package com.example.freight.v1.admin.repository;

import com.example.freight.auth.RoleRepository;
import com.example.freight.auth.UserRepository;
import com.example.freight.auth.models.entity.ERole;
import com.example.freight.auth.models.entity.Role;
import com.example.freight.auth.models.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    Role role = Role.builder().name(ERole.USER).createdAt(LocalDateTime.now()).description("test description").build();
    Role role2 = Role.builder().name(ERole.SUPER_ADMIN).createdAt(LocalDateTime.now()).description("test description2").build();

    List<User> users = List.of(
            User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("johndoe@email.com")
                    .title("Mr.")
                    .role(role)
                    .active(true)
                    .admin(true)
                    .build(),
            User.builder().firstName("jane")
                    .lastName("Zoe")
                    .email("janedoe@email.com")
                    .title("Mrs.")
                    .role(role2)
                    .active(true)
                    .admin(true)
                    .build());

    @Test
    public void shouldSaveAndFindUser() {

        // given
        Role role = Role.builder().name(ERole.USER).createdAt(LocalDateTime.now()).description("test description").build();
        roleRepository.save(role);

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@email.com")
                .title("Mr.")
                .role(role)
                .active(true)
                .admin(true)
                .build();

        // when
        userRepository.save(user);

        // then
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("johndoe@email.com");
        assertThat(foundUser.get().getFirstName()).isEqualTo("John");
        assertThat(foundUser.get().getLastName()).isEqualTo("Doe");
        assertThat(foundUser.get().getTitle()).isEqualTo("Mr.");
        assertThat(foundUser.get().getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldSaveAndReturnTwoUsers() {


        // given when
        roleRepository.save(role);
        roleRepository.save(role2);
        userRepository.saveAll(users);

        // then
        List<User> foundUsers = userRepository.findAll();
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(2);
        assertThat(foundUsers).extracting("firstName").contains("John", "jane");
        assertThat(foundUsers).extracting("lastName").contains("Doe","Zoe");
    }

    @Test
    public void shouldDeleteUser() {

        // given when
        roleRepository.save(role);
        roleRepository.save(role2);
        userRepository.saveAll(users);
        userRepository.deleteById(1L);

        // when
        List<User> foundUsers = userRepository.findAll();
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(1);

    }
}