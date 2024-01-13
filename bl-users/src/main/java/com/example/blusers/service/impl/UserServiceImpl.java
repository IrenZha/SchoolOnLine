package com.example.blusers.service.impl;

import com.example.blusers.domain.Role;
import com.example.blusers.dto.RoleEnum;
import com.example.blusers.domain.User;
import com.example.blusers.dto.UserDto;
import com.example.blusers.exc.EntityNotFoundException;
import com.example.blusers.exc.NotPaidException;
import com.example.blusers.mapper.UserMapper;
import com.example.blusers.repository.RoleRepository;
import com.example.blusers.repository.UserRepository;
import com.example.blusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleRepository roleRepository;


    @Override
    public List<UserDto> getAll() {
        List<User> all = repository.findAll();
        return mapper.toDtos(all);
    }

    @Override
    public Role getRole(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        return roleOptional.orElseGet(() -> new Role(name));
    }

    @Override
    public List<UserDto> findAllByRolesContains(String role) {
        return mapper.toDtos(repository.findAllByRolesContains(getRole(role)));
    }

    @Override
    public UserDto addRole(UUID id, String roleName) {
        Optional<User> userOptional = repository.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException(id.toString());
        }
        User user = userOptional.get();
        user.getRoles().add(getRole(roleName));
        return mapper.toDto(user);
    }

    @Override
    public UserDto save(UserDto user) {
        RoleEnum roleUser = RoleEnum.ROLE_USER;
        User entity = mapper.toEntity(user);
        entity.getRoles().add(getRole(roleUser.name()));
        User saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public UserDto getById(UUID id) {
        Optional<User> byId = repository.findById(id);
        if (byId.isPresent()) {
            return mapper.toDto(byId.get());
        } else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public void delete(UUID id) {
        Optional<User> byId = repository.findById(id);
        if (byId.isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public UserDto update(UUID id, UserDto user) {
        Optional<User> byId = repository.findById(id);
        if (byId.isPresent()) {
            User toUpdate = byId.get();
            mapper.update(toUpdate, user);
            return mapper.toDto(toUpdate);
        } else throw new EntityNotFoundException(user.getUsername());
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> byUsername = repository.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return mapper.toDto(user);
        } else return null;
    }

    @Override
    public boolean pay(UUID studentId, UUID teacherId, Double price) {
        Optional<User> studentById = repository.findById(studentId);
        if (studentById.isEmpty()) {
            throw new EntityNotFoundException("Student, id: " + studentId);
        }
        User student = studentById.get();
        Optional<User> teacherById = repository.findById(teacherId);
        if (teacherById.isEmpty()) {
            throw new EntityNotFoundException("Teacher, id: " + teacherId);
        }
        User teacher = teacherById.get();

        if ((student.getWallet() - price) < 0) {
            throw new NotPaidException();
        }
        student.setWallet(student.getWallet() - price);
        teacher.setWallet((teacher.getWallet() + (price * 0.8)));
        return true;
    }

   }
