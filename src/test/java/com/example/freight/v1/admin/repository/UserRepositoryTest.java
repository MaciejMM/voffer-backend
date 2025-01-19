package com.example.freight.v1.admin.repository;

import com.example.freight.auth.UserRepository;
import com.example.freight.auth.models.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldSaveAndFindUser() {

        // given
        User user = User.builder().email("test@example.com").firstName("John").lastName("Doe").title("Mr.").build();

        // when
        userRepository.save(user);

        // then
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
        assertThat(foundUser.get().getFirstName()).isEqualTo("John");
        assertThat(foundUser.get().getLastName()).isEqualTo("Doe");
        assertThat(foundUser.get().getTitle()).isEqualTo("Mr.");
        assertThat(foundUser.get().getCreatedAt()).isNotNull();
    }

    @Test
    public void shouldSaveAndReturnTwoUsers() {

        // given
        List<User> users = List.of(
                User.builder().firstName("John").lastName("Doe").build(),
                User.builder().firstName("Jane").lastName("Zoe").build());

        // when
        userRepository.saveAll(users);

        // then
        List<User> foundUsers = userRepository.findAll();
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(2);
        assertThat(foundUsers).extracting("firstName").contains("John", "Jane");
        assertThat(foundUsers).extracting("lastName").contains("Doe","Zoe");
    }

    @Test
    public void shouldDeleteUser() {

        // given
        List<User> users = List.of(
                User.builder().firstName("John").lastName("Doe").build(),
                User.builder().firstName("Jane").lastName("Doe").build());

        // when
        userRepository.saveAll(users);
        userRepository.deleteById(1L);

        // when
        List<User> foundUsers = userRepository.findAll();
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers).hasSize(1);

    }
}