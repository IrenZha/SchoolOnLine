package com.example.blusers.repository;

import com.example.blusers.domain.Role;
import com.example.blusers.domain.User;
import com.example.blusers.dto.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String email);
    List<User> findAllByRolesContains(Role role);
}
