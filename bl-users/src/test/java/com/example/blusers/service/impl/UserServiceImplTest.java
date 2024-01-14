package com.example.blusers.service.impl;

import com.example.blusers.domain.Role;
import com.example.blusers.domain.User;
import com.example.blusers.dto.UserDto;
import com.example.blusers.exc.EntityNotFoundException;
import com.example.blusers.exc.NotPaidException;
import com.example.blusers.mapper.UserMapperImpl;
import com.example.blusers.repository.RoleRepository;
import com.example.blusers.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapperImpl userMapper;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void getAll() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userMapper.toDtos(users)).thenCallRealMethod();

        List<UserDto> result = service.getAll();

        Assertions.assertEquals(2, result.size());
    }



    @Test
    public void getRole() {
        String name = "ROLE_USER";

        Role result = service.getRole(name);

        Assertions.assertEquals(name, result.getName());
    }

    @Test
    public void saveUser() {
        UUID id = UUID.randomUUID();
        List<Role> roles = new ArrayList<>();
        User user = new User();
        user.setId(id);
        user.setRoles(roles);
        UserDto userDto = new UserDto();
        Mockito.when(userMapper.toEntity(userDto)).thenReturn(user);

        service.save(userDto);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        List<Role> roles = new ArrayList<>();
        User user = new User();
        user.setId(id);
        user.setRoles(roles);
        UserDto userDto = new UserDto();
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        service.update(id,userDto);

        Mockito.verify(userMapper, Mockito.times(1)).update(user,userDto);
    }


    @Test
    public void updateExc() {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(id, userDto));
    }


    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        List<Role> roles = List.of(new Role("ROLE_USER"));
        User user = new User();
        user.setId(id);
        user.setRoles(roles);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));


        service.getById(id);

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void getByIdExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(id));
    }


    @Test
    public void delete() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        service.delete(id);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void addRole() {
        UUID id = UUID.randomUUID();
        String roleName = "ROLE";
        User user = new User();
        List<Role> roles = new ArrayList<>();
        user.setId(id);
        user.setRoles(roles);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        service.addRole(id, roleName);

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void addRoleExc() {
        UUID id = UUID.randomUUID();
        String roleName = "ROLE";

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.addRole(id, roleName));
    }

    @Test
    public void findByUsername() {
        String username = "username";
        UUID id = UUID.randomUUID();
        User user = new User();
        List<Role> roles = new ArrayList<>();
        user.setId(id);
        user.setUsername(username);
        user.setRoles(roles);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        service.findByUsername(username);

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
    }

    @Test
    public void userByUsernameNotFound() {
        String username = "username";

        UserDto result = service.findByUsername(username);

        Assertions.assertNull(result);
    }
    @Test
    public void payStudentNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();


        Assertions.assertThrows(EntityNotFoundException.class, () -> service.pay(studentId, teacherId, 100.0));
    }
    @Test
    public void payTeacherNotFound() {
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        User student = new User();
        Mockito.when(userRepository.findById(studentId)).thenReturn(Optional.of(student));


        Assertions.assertThrows(EntityNotFoundException.class, () -> service.pay(studentId, teacherId, 100.0));
    }

    @Test
    public void notPaid() {
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        User student = new User();
        student.setWallet(0.0);
        User teacher = new User();
        Mockito.when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(userRepository.findById(teacherId)).thenReturn(Optional.of(teacher));


        Assertions.assertThrows(NotPaidException.class, () -> service.pay(studentId, teacherId, 100.0));
    }

    @Test
    public void pay() {
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
        User student = new User();
        student.setWallet(200.0);
        User teacher = new User();
        teacher.setWallet(100.0);
        Mockito.when(userRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(userRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        boolean pay = service.pay(studentId, teacherId, 100.0);

        Assertions.assertTrue(pay);
    }

    @Test
    public void findAllByRolesContains() {
        String roleName = "roleName";

        service.findAllByRolesContains(roleName);

        Mockito.verify(roleRepository, Mockito.times(1)).findByName(roleName);
    }
}