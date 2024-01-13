package com.example.il_platform.service.impl;

import com.example.il_platform.client.AvatarClient;
import com.example.il_platform.client.FeedbackClient;
import com.example.il_platform.client.UserClient;
import com.example.il_platform.dto.avatar.AvatarDto;
import com.example.il_platform.dto.feedback.FeedbackDto;
import com.example.il_platform.dto.users.RoleDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.exc.PasswordMatchException;
import com.example.il_platform.exc.UsernameExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.example.il_platform.dto.users.RoleEnum.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserClient userClient;
    @Mock
    private AvatarClient avatarClient;
    @Mock
    private FeedbackClient feedbackClient;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void saveConfirmPassExc() {
        String pass = "qwerty";
        String confirmPass = "qwer";
        UserDto userDto = new UserDto();
        userDto.setPassword(pass);
        userDto.setConfirmPassword(confirmPass);

        Assertions.assertThrows(PasswordMatchException.class, () -> service.save(userDto));
    }

    @Test
    public void saveUserNameExistExc() {
        String pass = "qwerty";
        String confirmPass = "qwerty";
        String username = "username";
        UserDto userDto = new UserDto();
        userDto.setPassword(pass);
        userDto.setConfirmPassword(confirmPass);
        userDto.setUsername(username);
        Mockito.when(userClient.findByUsername(username)).thenReturn(userDto);

        Assertions.assertThrows(UsernameExistsException.class, () -> service.save(userDto));
    }

    @Test
    public void save() {
        String pass = "qwerty";
        String confirmPass = "qwerty";
        String username = "username";
        UserDto userDto = new UserDto();
        userDto.setPassword(pass);
        userDto.setConfirmPassword(confirmPass);
        userDto.setUsername(username);
        Mockito.when(userClient.findByUsername(username)).thenReturn(null);
        Mockito.when(passwordEncoder.encode(pass)).thenReturn(pass);
        Mockito.when(userClient.save(userDto)).thenReturn(userDto);

        UserDto result = service.save(userDto);

        Assertions.assertEquals(pass, result.getPassword());
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setId(id);
        Mockito.when(userClient.getById(id)).thenReturn(userDto);

        UserDto result = service.getById(id);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void getAllUsers() {
        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();
        List<UserDto> users = List.of(user1, user2);
        Mockito.when(userClient.getAll()).thenReturn(users);

        List<UserDto> result = service.getAllUsers();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(user1, result.get(0));
        Assertions.assertEquals(user2, result.get(1));
    }

    @Test
    public void setAvatar() {
        String fileName = "fileName";
        String contentType = "fileType";
        String content = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, content.getBytes());
        String fileId = UUID.randomUUID().toString();
        AvatarDto avatar = new AvatarDto(fileId, fileName, contentType, content.getBytes(), null);
        UUID id = UUID.randomUUID();
        UserDto user = new UserDto();
        user.setId(id);
        Mockito.when(avatarClient.uploadFile(file)).thenReturn(avatar);
        Mockito.when(userClient.update(id, user)).thenReturn(user);

        UserDto result = service.setAvatar(user, file);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void findByUsername() {
        String username = "Username";
        UserDto user = new UserDto();
        user.setUsername(username);
        Mockito.when(userClient.findByUsername(username)).thenReturn(user);

        UserDto result = service.findByUsername(username);

        Assertions.assertEquals(username, result.getUsername());
    }

    @Test
    public void becomeTeacherIsPresentRole() {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setRoles(List.of(new RoleDto(ROLE_TEACHER)));
        Mockito.when(userClient.getById(id)).thenReturn(userDto);

        UserDto result = service.becomeTeacher(id);

        assertThat(result.getRoles().contains(ROLE_TEACHER));
    }

    @Test
    public void becomeTeacher() {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setRoles(List.of(new RoleDto(ROLE_USER)));
        Mockito.when(userClient.getById(id)).thenReturn(userDto);
        Mockito.when(userClient.addRole(id, ROLE_TEACHER.name())).thenReturn(userDto);

        service.becomeTeacher(id);

        Mockito.verify(userClient, Mockito.times(1)).addRole(id, ROLE_TEACHER.name());
    }

    @Test
    public void getAllFeedbacks() {
        FeedbackDto feedback = new FeedbackDto();
        List<FeedbackDto> feedbacks = List.of(feedback);
        Mockito.when(feedbackClient.getAll()).thenReturn(feedbacks);

        List<FeedbackDto> result = service.getAllFeedbacks();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(feedback, result.get(0));
    }

    @Test
    public void getAllFeedbacksByUserId() {
        FeedbackDto feedback = new FeedbackDto();
        List<FeedbackDto> feedbacks = List.of(feedback);
        UUID userId = UUID.randomUUID();
        Mockito.when(feedbackClient.getAllByUserId(userId)).thenReturn(feedbacks);

        List<FeedbackDto> result = service.getAllFeedbacksByUserId(userId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(feedback, result.get(0));
    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setId(id);
        Mockito.when(userClient.update(id, userDto)).thenReturn(userDto);

        UserDto result = service.update(id, userDto);

        Assertions.assertEquals(id, result.getId());

    }

    @Test
    public void setWallet() {
        UUID id = UUID.randomUUID();
        UserDto userDto = new UserDto();
        userDto.setWallet(0.0);
        userDto.setId(id);
        Mockito.when(userClient.update(id, userDto)).thenReturn(userDto);

        service.setWallet(userDto, 100.0);

        Mockito.verify(userClient, Mockito.times(1)).update(id, userDto);
    }

    @Test
    public void feedbackUpdate() {
        UUID id = UUID.randomUUID();
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(id);
        Mockito.when(feedbackClient.update(id, feedbackDto)).thenReturn(feedbackDto);

        service.feedbackUpdate(id, feedbackDto);

        Mockito.verify(feedbackClient, Mockito.times(1)).update(id, feedbackDto);
    }

    @Test
    public void getTeachers() {
        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();
        user1.setRoles(List.of(new RoleDto(ROLE_TEACHER)));
        user2.setRoles(List.of(new RoleDto(ROLE_TEACHER)));
        List<UserDto> teachers = List.of(user1, user2);
        Mockito.when(userClient.getAllByRole(ROLE_TEACHER.name())).thenReturn(teachers);

        List<UserDto> result = service.teachers();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void isPresentRoleTrue() {
        UserDto user = new UserDto();
        user.setRoles(List.of(new RoleDto(ROLE_TEACHER)));

        boolean result = service.isPresentRole(user, ROLE_TEACHER);

        Assertions.assertTrue(result);
    }

    @Test
    public void isPresentRoleFalse() {
        UserDto user = new UserDto();
        user.setRoles(List.of(new RoleDto(ROLE_TEACHER)));

        boolean result = service.isPresentRole(user, ROLE_STUDENT);

        Assertions.assertFalse(result);
    }
}