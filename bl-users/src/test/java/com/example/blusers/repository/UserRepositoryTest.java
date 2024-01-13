package com.example.blusers.repository;

import com.example.blusers.domain.Role;
import com.example.blusers.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2
)
class UserRepositoryTest {
    @Autowired
   private UserRepository userRepository;
    @Test
    void findByUsername() {
        User user = new User();
        String username = "username";
        user.setUsername(username);
       userRepository.save(user);

        Optional<User> byUsername = userRepository.findByUsername(username);

        Assertions.assertEquals(username, byUsername.get().getUsername());
    }

    @Test
    void findAllByRolesContains() {
        User user = new User();
       Role role = new Role("roleName");
       List<Role> roles = List.of(role);
       user.setRoles(roles);
        userRepository.save(user);

        List<User> rolesContains = userRepository.findAllByRolesContains(role);

        Assertions.assertEquals(1, rolesContains.size());

    }
    @Test
    public void getById() {
        User user = new User();
        String username = "username";
        user.setUsername(username);
        userRepository.save(user);

        Optional<User> byId = userRepository.findById(user.getId());

        org.assertj.core.api.Assertions.assertThat(byId).isNotEmpty();
        org.assertj.core.api.Assertions.assertThat(byId.get().getUsername()).isEqualTo("username");
    }

}