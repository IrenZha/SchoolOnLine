package com.example.blusers.service;

import com.example.blusers.domain.Role;
import com.example.blusers.domain.User;
import com.example.blusers.dto.RoleDto;
import com.example.blusers.dto.RoleEnum;
import com.example.blusers.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto save(UserDto user);

    UserDto getById(UUID id);

    void delete(UUID id);

    UserDto update(UUID id, UserDto user);

    UserDto findByUsername(String username);

    List<UserDto> getAll();

    UserDto addRole(UUID id, String roleName);

    boolean pay(UUID studentId, UUID teacherID, Double price);

    Role getRole(String name);

    List<UserDto> findAllByRolesContains(String role);
}
